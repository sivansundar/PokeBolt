package com.sivan.pokebolt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sivan.pokebolt.databinding.ActivityItemBinding
import com.sivan.pokebolt.retrofit.entity.FFObject
import com.sivan.pokebolt.util.OnFFItemClickInterface
import com.sivan.pokebolt.util.toDate
import com.sivan.pokebolt.util.toLocalTime12Hr

class FFAdapter(
    private val listener: OnFFItemClickInterface) : RecyclerView.Adapter<FFAdapter.ActivityAdapterVH>() {

    var ffList : ArrayList<FFObject> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):  ActivityAdapterVH {
        val binding = ActivityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityAdapterVH(binding)
    }

    override fun onBindViewHolder(holder: ActivityAdapterVH, position: Int) {
        val currentItem = ffList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = ffList.size

    fun updateItems(items : ArrayList<FFObject>){
        ffList = items
        notifyDataSetChanged()
    }


    inner class ActivityAdapterVH(private val binding: ActivityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val userImageList = emptyList<Int>()

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = ffList[position]
                    listener.onItemClick(item)
                }
            }
        }

        fun bind(item: FFObject) {
            binding.apply {
                nameText.text = "Trainer ${item.name}"
                pokemonName.text = "Captured ${item.pokemon.name}"
                capturedAtText.text = "${item.pokemon.captured_at.toDate()} at ${item.pokemon.captured_at.toLocalTime12Hr()}"
                pokeballIcon.load(item.pokemon.sprites.other.official_artwork.front_default)
            }
        }

    }
}