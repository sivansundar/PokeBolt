package com.sivan.pokebolt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sivan.pokebolt.data.CapturedItem
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.toListModel
import com.sivan.pokebolt.databinding.CapturedItemBinding
import com.sivan.pokebolt.retrofit.entity.FFObject
import com.sivan.pokebolt.util.OnCapturedItemClickInterface
import com.sivan.pokebolt.util.toDate
import com.sivan.pokebolt.util.toLocalTime12Hr
import timber.log.Timber


class CapturedAdapter(
    private val listener : OnCapturedItemClickInterface
) : RecyclerView.Adapter<CapturedAdapter.CapturedAdapterVH>() {

    var capturedList : List<CapturedItem> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CapturedAdapterVH {
        val binding = CapturedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CapturedAdapterVH(binding)
    }

    override fun onBindViewHolder(holder: CapturedAdapterVH, position: Int) {
        val currentItem = capturedList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = capturedList.size

    fun updateItems(items: List<CapturedItem>){
        capturedList = items
        notifyDataSetChanged()
    }

    inner class CapturedAdapterVH(private val binding: CapturedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = capturedList[position]
                    listener.onItemClick(item)
                }
            }
        }

        fun bind(item : CapturedItem) {
            Timber.d("ITEMS : ${item.sprites.other}")


            binding.apply {
                capturedItemImage.load(
                    item.sprites.other.official_artwork.front_default
                )
            }
        }


    }
}