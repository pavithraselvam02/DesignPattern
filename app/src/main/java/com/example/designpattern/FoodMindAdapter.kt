package com.example.designpattern

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodMindAdapter(private val context: Context, private val dataList: List<FoodMind>) :
    RecyclerView.Adapter<FoodMindAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fooditems_mind, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.foodImageView.setImageResource(data.imageResource)
        holder.Foodmind.text = data.text
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val foodImageView: ImageView = itemView.findViewById(R.id.foodImageView)
         val Foodmind: TextView = itemView.findViewById(R.id.Foodmind)


    }
}
