package com.example.booksam.bookdetail

import android.content.Context
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.booksam.R
import com.example.booksam.databinding.ActivitySummaryBinding
import com.example.extension.toObj
import com.example.booksam.repo.Book
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : BaseActivity<ActivitySummaryBinding, SummaryViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        initIntent()
    }


    private fun initIntent() {
        val bookInString = intent.getStringExtra("book_data")
        getViewModel().setBook(bookInString)

    }




    override fun getLayout(): Int = R.layout.activity_summary

    override fun getContext(): Context = this

    override fun getViewModel(): SummaryViewModel =
        ViewModelProvider(this).get(SummaryViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel
}
