package com.example.booksam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.booksam.bookdetail.SummaryViewModel
import kotlinx.android.synthetic.main.fragment_summary.view.*

/**
 * A simple [Fragment] subclass.
 */
class SummaryFragment : Fragment() {

    lateinit var summaryViewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        view.ll_words.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_wordFragment)
        }
        view.ll_phrases.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_phraseFragment)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        summaryViewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)
    }


}
