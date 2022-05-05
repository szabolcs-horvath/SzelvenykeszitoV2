package hu.bme.aut.android.szelvenykeszito.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.szelvenykeszito.adapters.SportAdapter
import hu.bme.aut.android.szelvenykeszito.databinding.FragmentSportsBinding
import hu.bme.aut.android.szelvenykeszito.model.Sport
import hu.bme.aut.android.szelvenykeszito.network.OddsAPIInteractor


class SportsFragment : Fragment(), SportAdapter.SportItemClickListener {
    private lateinit var binding: FragmentSportsBinding
    private val adapter = SportAdapter(this)
    private val interactor = OddsAPIInteractor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSportsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvChooseLeague.adapter = adapter
        binding.rvChooseLeague.layoutManager = LinearLayoutManager(this.context)
        binding.srlSports.setOnRefreshListener { loadSports() }

        loadSports()
    }

    override fun onItemChanged(item: Sport) {
        TODO("Not yet implemented")
    }

    override fun navigateToOdds(sport: String) {
        view?.findNavController()?.navigate(SportsFragmentDirections.actionSportsFragmentToGamesFragment(sport))
    }

    private fun loadSports() {
        interactor.getSports(this::showSports, this::showError)
        binding.srlSports.isRefreshing = false
    }

    private fun showSports(sports: List<Sport>, remainingRequests: String) {
        adapter.update(sports)
        Toast.makeText(context, "Requests remaining for this API Key: $remainingRequests", Toast.LENGTH_SHORT).show()
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
    }
}