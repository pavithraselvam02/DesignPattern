package com.example.designpattern

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopularFoodAdapter(
    private val context: Context,
    private val popularFoodList: List<PopularFood>,
    private val fav: Favourites
) :
    RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder>() {

    inner class PopularFoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularFoodImage: ImageView = itemView.findViewById(R.id.popularfoodimage)
        val popularFoodDescription: TextView = itemView.findViewById(R.id.popularfooddescription)
        val mins: TextView = itemView.findViewById(R.id.mins)
        val popularRestaurant: TextView = itemView.findViewById(R.id.popularrestaurant)
        val favicon: ImageView = itemView.findViewById(R.id.favicon)

        init {
            // Set OnClickListener for the favicon
            favicon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val popularFood = popularFoodList[position]
                    // Toggle the isFavourite value
                    popularFood.isFavourite = !popularFood.isFavourite
                    // Update the favicon image based on the new isFavourite value
                    if (popularFood.isFavourite) {
                        favicon.setImageResource(R.drawable.baseline_favorite_24)
                    } else {
                        favicon.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                    // Notify the adapter of the data change
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFoodViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.popular_food, parent, false)
        return PopularFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularFoodViewHolder, position: Int) {
        val popularFood = popularFoodList[position]
        holder.popularFoodImage.setImageResource(popularFood.popularfoodimage)
        holder.popularFoodDescription.text = popularFood.popularfooddescription
        holder.mins.text = popularFood.mins
        holder.popularRestaurant.text = popularFood.popularrestaurant
        // Set the favicon image based on the isFavourite value
        if (popularFood.isFavourite) {
            holder.favicon.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            holder.favicon.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    override fun getItemCount(): Int {
        return popularFoodList.size
    }

    interface Favourites {
        // Method to handle adding and removing from favorites
        fun favouriteFood(food: PopularFood)
        fun unFavouriteFood(food: PopularFood)
    }
}