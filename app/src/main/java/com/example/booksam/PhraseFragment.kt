package com.example.booksam


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_phrase.view.*

/**
 * A simple [Fragment] subclass.
 */
class PhraseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phrase, container, false)

        view.tv_word.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_phraseFragment_to_wordFragment)
        }

        view.tv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_phraseFragment_to_addFragment)
        }
        return view
    }


}
