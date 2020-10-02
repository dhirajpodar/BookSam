package com.example.booksam.add.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booksam.R
import com.example.common.Genre
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.item_bottom_sheet.view.*

class SelectBottomSheetFragment(val bottomSheetOptionPickedListener: BottomSheetOptionPickedListener) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.tv_business.setOnClickListener {
            bottomSheetOptionPickedListener.optionPicked(Genre.BUSINESS)

        }

        view.tv_spiritual.setOnClickListener {
            bottomSheetOptionPickedListener.optionPicked(Genre.SPIRITUAL)
        }
    }
}