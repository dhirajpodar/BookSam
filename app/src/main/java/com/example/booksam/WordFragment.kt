package com.example.booksam


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_word.view.*

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_word, container, false)
        view.tv_phrase.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_wordFragment_to_phraseFragment)
        }

        view.tv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_wordFragment_to_addFragment)
        }
        return view
    }


}
