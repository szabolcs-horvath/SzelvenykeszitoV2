package hu.bme.aut.android.szelvenykeszito.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.szelvenykeszito.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {
    private lateinit var binding: FragmentResultsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentResultsBinding.inflate(layoutInflater)
        return binding.root
    }

}