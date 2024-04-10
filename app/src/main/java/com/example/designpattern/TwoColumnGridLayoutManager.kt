package com.example.designpattern

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TwoColumnGridLayoutManager(context: Context, spanCount: Int) :
    GridLayoutManager(context, spanCount, RecyclerView.HORIZONTAL, false) {

    override fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position < 10) {
                    // First 10 items should take up the entire width
                    1
                } else {
                    // Remaining items should take up half of the width
                    2
                }
            }
        }
    }
}