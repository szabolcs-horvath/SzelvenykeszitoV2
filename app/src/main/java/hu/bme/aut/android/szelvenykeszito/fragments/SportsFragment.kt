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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.szelvenykeszito.adapters.SportAdapter
import hu.bme.aut.android.szelvenykeszito.application.SzelvenykeszitoApplication
import hu.bme.aut.android.szelvenykeszito.databinding.FragmentSportsBinding
import hu.bme.aut.android.szelvenykeszito.model.Sport
import hu.bme.aut.android.szelvenykeszito.network.OddsAPIInteractor
import kotlin.concurrent.thread


class SportsFragment : Fragment(), SportAdapter.SportItemClickListener {
    private lateinit var binding: FragmentSportsBinding
    private val adapter = SportAdapter(this)
    private val interactor = OddsAPIInteractor()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSportsBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.rvChooseLeague.adapter = adapter
        binding.rvChooseLeague.layoutManager = LinearLayoutManager(this.context)
        binding.srlSports.setOnRefreshListener { loadSports() }
        binding.btMyGames.setOnClickListener {
            view.findNavController().navigate(SportsFragmentDirections.actionSportsFragmentToOwnGamesFragment())
        }

        progressDialog.setCancelable(true)
        progressDialog.setMessage("Meccsek letöltése...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.isIndeterminate = true

        loadSports()
    }

    override fun navigateToOdds(sport: String) {
        findNavController().navigate(SportsFragmentDirections.actionSportsFragmentToGamesFragment(sport))
    }

    override fun navigateToResults(sport: String) {
        findNavController().navigate(SportsFragmentDirections.actionSportsFragmentToResultsFragment(sport))
    }

    private fun loadSports() {
        progressDialog.show();
        interactor.getSports(this::showSports, this::showError)
        binding.srlSports.isRefreshing = false
    }

    private fun showSports(sports: List<Sport>, remainingRequests: String) {
        adapter.update(sports)
        progressDialog.hide()
        //Toast.makeText(context, "Requests remaining for this API Key: $remainingRequests", Toast.LENGTH_SHORT).show()
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
        progressDialog.setMessage("Nem sikerült betölteni a sportokat. :(")
    }
}