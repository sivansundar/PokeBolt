package com.sivan.pokebolt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sivan.pokebolt.data.TeamItem
import com.sivan.pokebolt.databinding.MyTeamItemBinding
import com.sivan.pokebolt.util.OnTeamItemClickInterface
import com.sivan.pokebolt.util.toDate
import timber.log.Timber

class TeamsAdapter(
    private val listener: OnTeamItemClickInterface
) : RecyclerView.Adapter<TeamsAdapter.TeamsAdapterVH>() {

    var teamsList : List<TeamItem> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamsAdapterVH {
        val binding = MyTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsAdapterVH(binding)
    }

    override fun onBindViewHolder(holder: TeamsAdapterVH, position: Int) {
        val currentItem = teamsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = teamsList.size

    fun updateItems(items: List<TeamItem>){
        teamsList = items
        notifyDataSetChanged()
    }

    inner class TeamsAdapterVH(
        private val binding: MyTeamItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = teamsList[position]
                    listener.onItemClick(item, binding.pokemonImage)
                }
            }
        }

        fun bind(item: TeamItem) {

            Timber.d("ITEMS : ${item.stats}")


            binding.apply {
                nameText.text = "Name : ${item.name}"
                hpText.text = "HP : ${item.stats[0].base_stat}"
                typeText.text = "Type : ${item.types[0].type.name}"
                capturedAtText.text = "Captured on ${item.captured_at.toDate()}"
                pokemonImage.load(item.sprites.other.official_artwork.front_default)
            }
        }


    }
}