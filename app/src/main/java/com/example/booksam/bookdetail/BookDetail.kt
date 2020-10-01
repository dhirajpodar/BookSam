package com.example.booksam.bookdetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.booksam.R
import com.example.booksam.databinding.ActivityBookDetailBinding
import com.example.extension.toObj
import com.example.repo.Book
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetail : BaseActivity<ActivityBookDetailBinding, BookDetailViewModel>() {

    private lateinit var bookDetailViewModel: BookDetailViewModel
    private var book: Book? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initIntent()
        setViewModel()
        initViewPager()

    }

    private fun initIntent() {
        val bookInString = intent.getStringExtra("book_data")
        bookInString?.let {
            book = it.toObj(Book::class.java)
        }

    }

    private fun setViewModel() {
        bookDetailViewModel = getViewModel()
        book?.let {
            tv_title.text = it.title
            tv_author.text = it.author
        }
    }

    private fun initTabLayout() {
        val tabTiles = arrayOf("Words", "Phrases")
        TabLayoutMediator(ty_tabs, viewPager) { tab, position ->
            tab.text = tabTiles[position]
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    private fun initViewPager() {
        book?.let {
            viewPager.adapter = ViewPagerAdapter(it)
            initTabLayout()
            Unit
        }


    }


    override fun getLayout(): Int = R.layout.activity_book_detail

    override fun getContext(): Context = this

    override fun getViewModel(): BookDetailViewModel =
        ViewModelProvider(this).get(BookDetailViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel
}
