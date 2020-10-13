package com.example.booksam


import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.booksam.bookdetail.SummaryViewModel
import com.example.extension.setLog
import kotlinx.android.synthetic.main.custom_word_dialog.view.*
import kotlinx.android.synthetic.main.fragment_word.view.*

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {
    private var word: String? = null
    private lateinit var summaryViewModel: SummaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        summaryViewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_word, container, false)
        view.fb_add.setOnClickListener {
            showWordInputDialog()
        }
        return view
    }

    private fun showWordInputDialog() {
        val wordInput = AlertDialog.Builder(context!!)
        val view = layoutInflater.inflate(R.layout.custom_word_dialog, null)
        wordInput.apply {
            setView(view)
            setCancelable(false)
            setPositiveButton("Add", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    word = view.cet_word.text()
                    setLog("WordFragment::::inserting $word")
                    summaryViewModel.insert(word!!)
                    dialog?.dismiss()
                }
            })
            setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                }
            })
            create()
            show()
        }
    }


}
