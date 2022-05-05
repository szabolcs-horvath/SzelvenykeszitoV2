package hu.bme.aut.android.szelvenykeszito.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.szelvenykeszito.R
import hu.bme.aut.android.szelvenykeszito.databinding.ItemGamesBinding
import hu.bme.aut.android.szelvenykeszito.model.Game
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import hu.bme.aut.android.szelvenykeszito.utility.format
import hu.bme.aut.android.szelvenykeszito.utility.setRadioGroup

class GameAdapter(private val listener: GameItemClickListener)
    : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private val items = mutableListOf<DisplayGame>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameViewHolder(ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val gamesItem = items[position]

        holder.binding.tvHomeTeam.text = gamesItem.home_team
        holder.binding.tvAwayTeam.text = gamesItem.away_team
        holder.binding.rgOutcome.setRadioGroup(gamesItem.selection)
        holder.binding.rgOutcome.setOnCheckedChangeListener { _, i ->
            holder.binding.btClearSelection.isVisible = true
            gamesItem.selection = when (i) {
                R.id.rbHomeTeam -> DisplayGame.Selection.HOME
                R.id.rbDraw -> DisplayGame.Selection.DRAW
                R.id.rbAwayTeam -> DisplayGame.Selection.AWAY
                else -> DisplayGame.Selection.NONE
            }
        }
        if (gamesItem.outcomes.size == 3) {
            holder.binding.rbHomeTeam.text = gamesItem.outcomes.find { o -> o.name == gamesItem.home_team} ?.price?.format(2)
            holder.binding.rbDraw.text = gamesItem.outcomes.find { o -> o.name == "Draw"} ?.price?.format(2)
            holder.binding.rbAwayTeam.text = gamesItem.outcomes.find { o -> o.name == gamesItem.away_team} ?.price?.format(2)
        } else if (gamesItem.outcomes.size == 2) {
            holder.binding.tvDraw.isVisible = false
            holder.binding.rbDraw.isVisible = false
            holder.binding.rbHomeTeam.text = gamesItem.outcomes.find { o -> o.name == gamesItem.home_team} ?.price?.format(2)
            holder.binding.rbAwayTeam.text = gamesItem.outcomes.find { o -> o.name == gamesItem.away_team} ?.price?.format(2)
        }
        holder.binding.tvCommenceTime.text = "Start time: ${gamesItem.commence_time.toLocalDate()} ${gamesItem.commence_time.toLocalTime()}"
        holder.binding.btClearSelection.isVisible = gamesItem.selection != DisplayGame.Selection.NONE
        holder.binding.btClearSelection.setOnClickListener {
            holder.binding.rgOutcome.clearCheck()
            holder.binding.btClearSelection.isVisible = false
        }
    }

    override fun getItemCount(): Int = items.size

    fun update(gameItems: List<DisplayGame>?) {
        items.clear()
        if (gameItems != null) {
            items.addAll(gameItems)
        }
        notifyDataSetChanged()
    }

    interface GameItemClickListener {
        fun onItemChanged(item: Game)
    }

    inner class GameViewHolder(val binding: ItemGamesBinding): RecyclerView.ViewHolder(binding.root)
}