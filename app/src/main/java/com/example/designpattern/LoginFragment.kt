package com.example.designpattern

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val first: ImageView = view.findViewById(R.id.first_img_view)
        val second: ImageView = view.findViewById(R.id.second_img_view)
        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 9000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = first.width.toFloat()
            val translationX = width * progress
            first.translationX = translationX
            second.translationX = translationX - width
        }
        animator.start()
        val getStartedButton: Button = view.findViewById(R.id.get_started_button)
        getStartedButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        }
    }
}
