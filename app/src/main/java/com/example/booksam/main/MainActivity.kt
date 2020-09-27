package com.example.booksam.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiraj.base.BaseActivity
import com.example.booksam.BR
import com.example.repo.Book
import com.example.booksam.R
import com.example.booksam.add.AddActivity
import com.example.booksam.databinding.ActivityMainBinding
import com.example.extension.toObj
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private val REQUEST_CODE = 100
    private lateinit var mainViewModel: MainViewModel
    private var bookAdapter: BookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewModel()
        initRecyclerView()
        initObservers()
        initFB()
    }


    private fun initObservers() {
        mainViewModel.books.observe(this, Observer { books ->
            books?.let { bookAdapter?.setWords(it) }
        })
    }


    private fun setViewModel() {
        mainViewModel = getViewModel()

    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter()
        rv_books.layoutManager = LinearLayoutManager(this)
        rv_books.adapter = bookAdapter
    }


    private fun initFB() {
        fb_add.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val dataInString = it.getStringExtra("SUCCESS")
                val book = dataInString?.toObj(Book::class.java)!!
                mainViewModel.insert(book)
                Unit
            }
        }
    }


    override fun getLayout(): Int = R.layout.activity_main

    override fun getContext(): Context = this

    override fun getViewModel(): MainViewModel =
        ViewModelProvider(this).get(MainViewModel::class.java)


    override fun getBindingVariable(): Int = BR.viewModel

    override fun onDestroy() {
        mainViewModel.onClear()
        super.onDestroy()
    }
}


