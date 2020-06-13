package com.vspl.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_list.view.*

class MoviesAdpter (
    val context: Context,
    val itemList: ArrayList<MovieModel>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movies_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide
            .with(context)
            .load(itemList!!.get(position).poster)
            .fitCenter()
            .placeholder(R.drawable.default_poster)
            .into(holder.itemView.posterImage);


        holder.itemView.movietitle.text=itemList!!.get(position).title
        holder.itemView.movieyear.text=itemList!!.get(position).year

    }
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    }

}
