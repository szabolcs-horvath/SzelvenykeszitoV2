package hu.bme.aut.android.szelvenykeszito.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.szelvenykeszito.R
import hu.bme.aut.android.szelvenykeszito.databinding.ItemResultsBinding
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayResult

class ResultAdapter(private val listener: ResultItemClickListener):
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private val items = mutableListOf<DisplayResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResultViewHolder(ItemResultsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val resultItem = items[position]

        holder.binding.tvHomeTeam.text = resultItem.home_team
        holder.binding.tvAwayTeam.text = resultItem.away_team
        holder.binding.tvResult.text = String.format(
            (listener as Fragment).getString(R.string.result_value),
            resultItem.scores?.find { score -> score.name == resultItem.home_team }?.score,
            resultItem.scores?.find { score -> score.name == resultItem.away_team }?.score
        )
        holder.binding.tvLastUpdate.text = String.format(
            (listener as Fragment).getString(R.string.last_update_time),
            resultItem.last_update?.toLocalDate(),
            resultItem.last_update?.toLocalTime()
        )
        if (!resultItem.completed) {
            holder.binding.tvResult.setTextColor(0xff0000)
        }
    }

    override fun getItemCount(): Int = items.size

    fun update(resultItems: List<DisplayResult>?) {
        items.clear()
        if (resultItems != null) {
            items.addAll(resultItems)
        }
        notifyDataSetChanged()
    }

    interface ResultItemClickListener {  }

    inner class ResultViewHolder(val binding: ItemResultsBinding): RecyclerView.ViewHolder(binding.root)
}