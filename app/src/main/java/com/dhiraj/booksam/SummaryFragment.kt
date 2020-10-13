package com.example.booksam


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_summary.view.*

/**
 * A simple [Fragment] subclass.
 */
class SummaryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        view.iv_back.setOnClickListener {
            super.onDestroy()
        }
        view.tv_word.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_wordFragment)
        }
        view.tv_phrase.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_phraseFragment)
        }
        return view
    }


}
