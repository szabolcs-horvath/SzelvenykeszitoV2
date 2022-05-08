package hu.bme.aut.android.szelvenykeszito.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.szelvenykeszito.adapters.ResultAdapter
import hu.bme.aut.android.szelvenykeszito.databinding.FragmentResultsBinding
import hu.bme.aut.android.szelvenykeszito.model.Result
import hu.bme.aut.android.szelvenykeszito.network.OddsAPIInteractor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ResultsFragment : Fragment(), ResultAdapter.ResultItemClickListener {
    private val args: ResultsFragmentArgs by navArgs()
    private lateinit var binding: FragmentResultsBinding
    private val adapter = ResultAdapter(this)
    private val interactor = OddsAPIInteractor()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentResultsBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.rvGameResults.adapter = adapter
        binding.rvGameResults.layoutManager = LinearLayoutManager(this.context)
        binding.srlResults.setOnRefreshListener { loadResults(args.sport) }
        binding.btMyGames.setOnClickListener {
            findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToOwnGamesFragment())
        }

        progressDialog.setCancelable(true)
        progressDialog.setMessage("Meccsek letöltése...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.isIndeterminate = true

        val bundleString = savedInstanceState?.getString("RESULTS")
        if (!bundleString.isNullOrEmpty()) {
            adapter.update(Json.decodeFromString(bundleString))
        }
        else {
            loadResults(args.sport)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val jsonList = Json.encodeToString(adapter.getItems())
        outState.putString("RESULTS", jsonList)
    }

    private fun loadResults(sport: String) {
        progressDialog.show()
        interactor.getScores(sport, 3, this::showResults, this::showError)
        binding.srlResults.isRefreshing = false
    }

    private fun showResults(results: List<Result>, remainingRequests: String) {
        adapter.update(results.map { r -> r.toDisplayResult() })
        progressDialog.hide()
        Toast.makeText(context, "API kulcs hátralévő hívások: $remainingRequests", Toast.LENGTH_SHORT).show()
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
        progressDialog.setMessage("Ehhez a sporthoz nem sikerült letölteni az eredményeket. :(")
    }

}