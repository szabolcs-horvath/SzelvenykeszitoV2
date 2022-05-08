package hu.bme.aut.android.szelvenykeszito.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.szelvenykeszito.adapters.GameAdapter
import hu.bme.aut.android.szelvenykeszito.application.SzelvenykeszitoApplication
import hu.bme.aut.android.szelvenykeszito.databinding.FragmentGamesBinding
import hu.bme.aut.android.szelvenykeszito.model.Game
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import hu.bme.aut.android.szelvenykeszito.network.OddsAPIInteractor
import hu.bme.aut.android.szelvenykeszito.network.OddsParameters
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.concurrent.thread

class GamesFragment : Fragment(), GameAdapter.GameItemClickListener {
    private val args: GamesFragmentArgs by navArgs()
    private lateinit var binding: FragmentGamesBinding
    private val adapter = GameAdapter(this)
    private val interactor = OddsAPIInteractor()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGamesBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.rvChooseGame.adapter = adapter
        binding.rvChooseGame.layoutManager = LinearLayoutManager(this.context)
        binding.srlGames.setOnRefreshListener { loadOdds(args.sport) }
        binding.btMyGames.setOnClickListener {
            view.findNavController().navigate(GamesFragmentDirections.actionGamesFragmentToOwnGamesFragment())
        }

        progressDialog.setCancelable(true)
        progressDialog.setMessage("Meccsek letöltése...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.isIndeterminate = true

        val bundleString = savedInstanceState?.getString("GAMES")
        if (!bundleString.isNullOrEmpty()) {
            val items = Json.decodeFromString<List<DisplayGame>>(bundleString)
            if (items.isEmpty()) {
                loadOdds(args.sport)
            } else {
                adapter.update(items)
            }
        } else {
            loadOdds(args.sport)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val jsonList = Json.encodeToString(adapter.getItems())
        outState.putSerializable("GAMES", jsonList)
    }

    override fun onItemChanged(item: DisplayGame) {
        if (item.selection == DisplayGame.Selection.NONE) {
            thread {
                SzelvenykeszitoApplication.gameDatabase.gameDao().deleteGame(item.toRoomGame())
            }
        } else {
            thread {
                SzelvenykeszitoApplication.gameDatabase.gameDao().insertGame(item.toRoomGame())
            }
        }
    }

    private fun loadOdds(sport: String) {
        progressDialog.show()
        interactor.getOdds(OddsParameters(sport), this::showOdds, this::showError)
        binding.srlGames.isRefreshing = false
    }

    private fun showOdds(games: List<Game>, remainingRequests: String) {
        adapter.update(games.map { g -> g.toDisplayGame() })
        progressDialog.hide()
        Toast.makeText(context, "API kulcs hátralévő hívások: $remainingRequests", Toast.LENGTH_SHORT).show()
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
        progressDialog.setMessage("Ehhez a sporthoz nem sikerült letölteni a meccseket. :(")
    }
}