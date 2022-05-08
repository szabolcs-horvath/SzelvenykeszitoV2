package hu.bme.aut.android.szelvenykeszito.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.szelvenykeszito.databinding.ItemSportsBinding
import hu.bme.aut.android.szelvenykeszito.model.Sport

class SportAdapter(private val listener: SportItemClickListener): RecyclerView.Adapter<SportAdapter.SportViewHolder>() {

    private val items = mutableListOf<Sport>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SportViewHolder(ItemSportsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        val sportsItem = items[position]
        holder.binding.tvSport.text = sportsItem.title
        holder.binding.btSport.setOnClickListener {
            listener.navigateToOdds(sportsItem.key)
        }
        holder.binding.btResults.setOnClickListener {
            listener.navigateToResults(sportsItem.key)
        }
    }

    override fun getItemCount(): Int = items.size

    fun getItems(): List<Sport> {
        return items
    }

    fun update(sportItems: List<Sport>?) {
        items.clear()
        if (sportItems != null) {
            items.addAll(sportItems)
        }
        notifyDataSetChanged()
    }

    interface SportItemClickListener {
        fun navigateToOdds(sport: String)
        fun navigateToResults(sport: String)
    }

    inner class SportViewHolder(val binding: ItemSportsBinding): RecyclerView.ViewHolder(binding.root)
}