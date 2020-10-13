package com.example.booksam.bookdetail

import android.content.Context
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.booksam.R
import com.example.booksam.databinding.ActivityBookDetailBinding
import com.example.extension.toObj
import com.example.booksam.repo.Book
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_book_detail.*

class SummaryActivity : BaseActivity<ActivityBookDetailBinding, SummaryViewModel>() {

    private lateinit var bookDetailViewModel: SummaryViewModel
    private var book: Book? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initIntent()
        setViewModel()
//        initFragment()
//        initViewPager()

    }

    /*  private fun initFragment() {
          val fragmentManager = supportFragmentManager
          val summaryFragment = SummaryFragment(this)
          val fragmentTransaction = fragmentManager.beginTransaction()
          fragmentTransaction.add(R.id.rl_container, summaryFragment, "summary_fragment")
          fragmentTransaction.commit()

      }
  */
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

    override fun getViewModel(): SummaryViewModel =
        ViewModelProvider(this).get(SummaryViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel
}
