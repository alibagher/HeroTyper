package com.phatphoophoo.pdtran.herotyper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.phatphoophoo.pdtran.herotyper.R
import kotlinx.android.synthetic.main.fragment_game_menu.*

class GameMenuFragment : Fragment() {
    private var isGameOver: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isGameOver = it.getBoolean(ARG_IS_GAME_OVER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_menu, container, false)
    }

    override fun onStart() {
        super.onStart()

        resume_button.visibility = if (isGameOver) View.GONE else View.VISIBLE
        menu_header_text.text = if (isGameOver) "Game Over" else "Paused"
    }

    companion object {
        const val ARG_IS_GAME_OVER = "ARG_IS_GAME_OVER"

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(isGameOver: Boolean) =
            GameMenuFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_GAME_OVER, isGameOver)
                }
            }
    }
}
