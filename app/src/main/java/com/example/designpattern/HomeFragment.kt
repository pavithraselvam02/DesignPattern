package com.example.designpattern

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import android.widget.EditText

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var secondViewPager: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val delay = 2000L
    private var isAutoScrollEnabled = false

    private val carouselItems = listOf(
        CarouselItem(R.drawable.burger, "Mc Donalds"),
        CarouselItem(R.drawable.kfcchicken, "KFC"),
        CarouselItem(R.drawable.icecream, "Kwality Walls")
    )

    private val secondCarouselItems = listOf(
        CarouselItem(R.drawable.potatochips, "Lays"),
        CarouselItem(R.drawable.milk, "Milk"),
        CarouselItem(R.drawable.onionn, "Onion")
    )

    private val runnable = object : Runnable {
        override fun run() {
            if (isAutoScrollEnabled) {
                viewPager.setCurrentItem(currentPage++, true)
                secondViewPager.setCurrentItem(currentPage++, true)
                if (currentPage == viewPager.adapter?.itemCount) {
                    currentPage = 0
                }
            }
            handler.postDelayed(this, delay)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        secondViewPager = view.findViewById(R.id.viewPager2)
        val searchEditText: EditText = view.findViewById(R.id.searchEditText)

// Set initial hint
        searchEditText.hint = "Search for"

// Example of dynamically updating the hint based on the selected item
        val selectedItem = "Milk" // Change this to your selected item
        searchEditText.hint = "Search for $selectedItem"

// List of items to suggest
        val suggestedItems = listOf("Bread", "Cheese", "Eggs", "Yogurt")

// Set up text change listener for the searchEditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable?) {
                // Get the current text entered by the user
                val searchText = s.toString().trim()

                // Check if the user has entered something
                if (searchText.isNotEmpty()) {
                    // Check if the entered text matches any suggested items
                    val matchedItems = suggestedItems.filter { it.contains(searchText, ignoreCase = true) }

                    // Prepare the hint text with selected item and suggested items
                    val hint = if (matchedItems.isNotEmpty()) {
                        val suggestedItemsText = matchedItems.joinToString(", ")
                        "Search for $selectedItem, e.g. $suggestedItemsText"
                    } else {
                        "Search for $selectedItem"
                    }

                    // Update the hint text
                    searchEditText.hint = hint
                } else {
                    // If the user clears the text, revert to default hint
                    searchEditText.hint = "Search for $selectedItem"
                }
            }
        })


        viewPager.clipToPadding = false
        val padding = resources.getDimensionPixelSize(R.dimen.viewpager_padding)
        viewPager.setPadding(padding, 0, padding, 0)
        viewPager.adapter = CarouselAdapter(carouselItems)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
            }
        })

        secondViewPager.clipToPadding = false
        secondViewPager.setPadding(padding, 0, padding, 0)
        secondViewPager.adapter = CarouselAdapter(secondCarouselItems)

        secondViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        viewPager.setOnClickListener {
            isAutoScrollEnabled = !isAutoScrollEnabled
        }
        secondViewPager.setOnClickListener {
            isAutoScrollEnabled = !isAutoScrollEnabled
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        startViewPagerAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        stopViewPagerAutoScroll()
    }

    private fun startViewPagerAutoScroll() {
        handler.postDelayed(runnable, delay)
    }

    private fun stopViewPagerAutoScroll() {
        handler.removeCallbacks(runnable)
    }
}
