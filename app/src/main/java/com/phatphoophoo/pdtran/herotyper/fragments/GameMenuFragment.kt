package com.phatphoophoo.pdtran.herotyper.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.phatphoophoo.pdtran.herotyper.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameMenuFragment.GameMenuFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var canResume: Boolean? = false
    private var listener: GameMenuFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            canResume = it.getBoolean(ARG_IS_GAME_OVER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("Roland", "Inflated")
        return inflater.inflate(R.layout.fragment_game_menu, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameMenuFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement GameMenuFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface GameMenuFragmentInteractionListener {
        fun onRestartPressed()
        fun onResumePressed()
        fun onExitPressed()
    }

    companion object {
        const val ARG_IS_GAME_OVER = "ARG_IS_GAME_OVER"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameMenuFragment.
         */
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
