package com.example.designpattern

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlin.random.Random

class HomeFragment : Fragment() {
    private var isUpdatingText = false
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

    private val suggestions = mutableListOf<String>()
    private val suggestionGenerator = Random(System.currentTimeMillis())

    private val suggestionRunnable = object : Runnable {
        override fun run() {
            if (isAutoScrollEnabled) {
                updateSuggestion()
            }
            handler.postDelayed(this, delay)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not needed
        }

        override fun afterTextChanged(s: Editable?) {
            if (!isUpdatingText) {
                isUpdatingText = true
                // Clear previous suggestions
                suggestions.clear()
                // Add random suggestions
                repeat(3) { // Adjust the number of suggestions here
                    val randomIndex = suggestionGenerator.nextInt(secondCarouselItems.size)
                    val randomItem = secondCarouselItems[randomIndex]
                    suggestions.add(randomItem.description)
                }
                // Display the first suggestion
                updateSuggestion()
                // Remove the TextWatcher temporarily
                val searchEditText = view?.findViewById<EditText>(R.id.searchEditText)
                searchEditText?.removeTextChangedListener(this)
                isUpdatingText = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        secondViewPager = view.findViewById(R.id.viewPager2)

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

        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(textWatcher)

        return view
    }

    override fun onResume() {
        super.onResume()
        startViewPagerAutoScroll()
        handler.postDelayed(suggestionRunnable, delay)
    }

    override fun onPause() {
        super.onPause()
        stopViewPagerAutoScroll()
        handler.removeCallbacks(suggestionRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val searchEditText = view?.findViewById<EditText>(R.id.searchEditText)
        searchEditText?.removeTextChangedListener(textWatcher)
    }

    private fun startViewPagerAutoScroll() {
        handler.postDelayed(suggestionRunnable, delay)
    }

    private fun stopViewPagerAutoScroll() {
        handler.removeCallbacks(suggestionRunnable)
    }

    private fun updateSuggestion() {
        if (suggestions.isNotEmpty()) {
            val currentSuggestion = suggestions.first()
            val searchEditText = view?.findViewById<EditText>(R.id.searchEditText)
            searchEditText?.apply {
                text = null
                append(currentSuggestion)
            }
        }
    }
}