package com.example.designpattern

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Food_navmenu_Fragment : Fragment(), PopularFoodAdapter.Favourites {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapterRecyclerview
    private lateinit var dataList: MutableList<FoodItem>
    private lateinit var recyclerViewBelowWhatson: RecyclerView
    private lateinit var foodMindAdapter: FoodMindAdapter
    private lateinit var datalistfood: MutableList<FoodMind>
    private lateinit var recyclerviewpopular: RecyclerView
    private lateinit var popularFoodAdapter: PopularFoodAdapter
    private lateinit var datalistpopular: MutableList<PopularFood>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_navmenu_, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewBelowWhatson = view.findViewById(R.id.recyclerViewBelowWhatson)
        recyclerViewBelowWhatson.layoutManager =
            TwoColumnGridLayoutManager(requireContext(), 2)

        recyclerviewpopular = view.findViewById(R.id.popularRecycler)
        recyclerviewpopular.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        dataList = ArrayList()
        dataList.add(FoodItem(R.drawable.amburstarbiryani, "Ambur Star Briyani"))
        dataList.add(FoodItem(R.drawable.nakhshatrafood, "Nakshatra Pure veg"))
        dataList.add(FoodItem(R.drawable.sizzlingchickencurry, "Sizzling Curries"))
        dataList.add(FoodItem(R.drawable.a2b, "A2B Adayar Ananda Bhavan"))
        dataList.add(FoodItem(R.drawable.burger, "Item 3 Description"))
        dataList.add(FoodItem(R.drawable.burger, "Item 3 Description"))

        datalistfood = ArrayList()
        datalistfood.add(FoodMind(R.drawable.burger, "Briyani"))
        datalistfood.add(FoodMind(R.drawable.kfcchicken, "North Indian"))
        datalistfood.add(FoodMind(R.drawable.icecream, "Pizza"))
        datalistfood.add(FoodMind(R.drawable.burger, "South Indian"))
        datalistfood.add(FoodMind(R.drawable.kfcchicken, "Chinese"))
        datalistfood.add(FoodMind(R.drawable.icecream, "Pure Veg"))

        datalistfood.add(FoodMind(R.drawable.burger, "Item 1"))
        datalistfood.add(FoodMind(R.drawable.kfcchicken, "Item 2"))
        datalistfood.add(FoodMind(R.drawable.icecream, "Item 3"))
        datalistfood.add(FoodMind(R.drawable.burger, "Item 3"))
        datalistfood.add(FoodMind(R.drawable.kfcchicken, "Item 3"))
        datalistfood.add(FoodMind(R.drawable.icecream, "Item 3"))

        datalistpopular = ArrayList()
        datalistpopular.add(
            PopularFood(
                R.drawable.seashelll,
                "Savoury Sea Shell",
                "30-35 mins",
                "Chinese,Inidian,Arabian",
                false
            )
        )
        datalistpopular.add(
            PopularFood(
                R.drawable.a2b,
                "A2B - Adyar Ananda Bhavan",
                "45-50 mins",
                "SouthIndian,Sweets,Chinese",
                false
            )
        )
        datalistpopular.add(
            PopularFood(
                R.drawable.amburstarbiryani,
                "ytyguhgf",
                "hgfdggh",
                "hgfdfg",
                false
            )
        )
        datalistpopular.add(
            PopularFood(
                R.drawable.amburstarbiryani,
                "ytyguhgf",
                "hgfdggh",
                "hgfdfg",
                false
            )
        )
        datalistpopular.add(
            PopularFood(
                R.drawable.amburstarbiryani,
                "ytyguhgf",
                "hgfdggh",
                "hgfdfg",
                false
            )
        )

        adapter = FoodAdapterRecyclerview(dataList)
        recyclerView.adapter = adapter

        foodMindAdapter = FoodMindAdapter(requireContext(), datalistfood)
        recyclerViewBelowWhatson.adapter = foodMindAdapter

        popularFoodAdapter = PopularFoodAdapter(requireContext(), datalistpopular, this)
        recyclerviewpopular.adapter = popularFoodAdapter

        return view
    }
    override fun favouriteFood(food: PopularFood) {
        datalistpopular.forEachIndexed { index, item ->
            if (item == food) {
                datalistpopular[index] = food.copy(isFavourite = true)
                popularFoodAdapter.notifyItemChanged(index)
                return@forEachIndexed
            }
        }
    }

    override fun unFavouriteFood(food: PopularFood) {
        datalistpopular.forEachIndexed { index, item ->
            if (item == food) {
                datalistpopular[index] = food.copy(isFavourite = false)
                popularFoodAdapter.notifyItemChanged(index)
                return@forEachIndexed
            }
        }
    }
}
