package com.example.meucats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meucats.R
import com.example.meucats.database.CatTable
import com.example.meucats.mDb

class AdapterFavorite(private val values: MutableList<CatTable>) :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView? = null

        init {
            image = itemView?.findViewById(R.id.catIconRecyclerPart)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_for_recyclerview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image?.let {
            Glide
                .with(holder.itemView.context)
                .load(values[position].link)
                .placeholder(R.drawable.spinercat)
                .into(it)
        }
        holder.image?.setOnLongClickListener {
            Thread {
                mDb.CatDao().delete(
                    values[position].link
                )
            }.start()
            Toast.makeText(holder.itemView.context, "Deleted️", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }
}