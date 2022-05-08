package hu.bme.aut.android.szelvenykeszito.fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.szelvenykeszito.R
import hu.bme.aut.android.szelvenykeszito.adapters.GameAdapter
import hu.bme.aut.android.szelvenykeszito.application.SzelvenykeszitoApplication
import hu.bme.aut.android.szelvenykeszito.databinding.FragmentOwnGamesBinding
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class OwnGamesFragment : Fragment(), GameAdapter.GameItemClickListener {
    private lateinit var binding: FragmentOwnGamesBinding
    private val adapter = GameAdapter(this)
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOwnGamesBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOwnGames.adapter = adapter
        binding.rvOwnGames.layoutManager = LinearLayoutManager(this.context)
        binding.srlOwnGames.setOnRefreshListener { loadGamesFromDatabase() }
        binding.tvWinningsAmount.text = String.format(getString(R.string.winnings_amount), 0)
        binding.etBetSize.addTextChangedListener { updateWinnings() }
        binding.btClearAll.setOnClickListener {
            AlertDialog.Builder(context, R.style.MyDialogTheme)
                .setTitle("Minden meccs törlése")
                .setMessage("Biztos törölni szeretnéd az összes meccsedet?")
                .setPositiveButton("Igen") { _, _ ->
                    thread {
                        SzelvenykeszitoApplication.gameDatabase.gameDao().deleteAllGames()
                        activity?.runOnUiThread {
                            loadGamesFromDatabase()
                        }
                    }
                }
                .setNegativeButton("Mégse") { _, _ ->
                    Toast.makeText(context, "Akkor ne nyomogasd azt a gombot!", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        progressDialog.setCancelable(true)
        progressDialog.setMessage("Meccseim betöltése...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.isIndeterminate = true

        val bundleString = savedInstanceState?.getString("OWN_GAMES")
        if (!bundleString.isNullOrEmpty()) {
            val items = Json.decodeFromString<List<DisplayGame>>(bundleString)
            if (items.isEmpty()) {
                loadGamesFromDatabase()
            } else {
                adapter.update(items)
            }
        } else {
            loadGamesFromDatabase()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val jsonList = Json.encodeToString(adapter.getItems())
        outState.putString("OWN_GAMES", jsonList)
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
        updateWinnings()
    }

    private fun updateWinnings() {
        val bet = (if(binding.etBetSize.text.toString().isNotEmpty()) binding.etBetSize.text.toString().toInt() else 0)
        binding.tvWinningsAmount.text = String.format(getString(R.string.winnings_amount), (bet * adapter.getMultiplier()).roundToInt())
    }

    private fun loadGamesFromDatabase() {
        progressDialog.show()
        thread {
            val games = SzelvenykeszitoApplication.gameDatabase.gameDao().getAllGames().map { roomGame -> roomGame.toDisplayGame() }
            activity?.runOnUiThread {
                adapter.update(games)
                binding.srlOwnGames.isRefreshing = false
                progressDialog.hide()
            }
        }
    }

}