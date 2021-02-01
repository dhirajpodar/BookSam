package com.example.booksam.main

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiraj.base.BaseActivity
import com.example.booksam.Utils
import com.example.booksam.BR
import com.example.booksam.R
import com.example.booksam.add.AddActivity
import com.example.booksam.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), LifecycleObserver {
    private lateinit var mainViewModel: MainViewModel
    private var bookAdapterSpiritual: BookAdapter? = null
    private var bookAdapterBusiness: BookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = Color.TRANSPARENT
            }
        }
        supportActionBar?.hide()
        setViewModel()
        initViewPager()
        initRecyclerView()
        initObservers()
        initView()

    }

    private fun initViewPager() {
        val list = Utils.getFacts()
        viewPager_image.adapter = VpImageAdapter(list)
        indicator.setViewPager(viewPager_image)
        initTimer()

    }


    private fun initTimer() {

        object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                val position = viewPager_image.currentItem
                if (position == 9) {
                    viewPager_image.currentItem = 0
                } else {
                    viewPager_image.currentItem = position + 1
                }
                initTimer()
            }

            override fun onTick(p0: Long) {}

        }.start()
    }


    private fun initObservers() {
        mainViewModel.books.observe(this, Observer {
            mainViewModel.setBooksByGenre()
        })

        mainViewModel.booksBySpiritual.observe(this, Observer { books ->
            books?.let {
                bookAdapterSpiritual?.setBooks(it)
            }
        })
        mainViewModel.booksByBusiness.observe(this, Observer { books ->
            books?.let {
                bookAdapterBusiness?.setBooks(it)
            }
        })
    }


    private fun setViewModel() {
        mainViewModel = getViewModel()

    }

    private fun initRecyclerView() {
        bookAdapterSpiritual = BookAdapter()
        rv_genre_spiritual.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_genre_spiritual.adapter = bookAdapterSpiritual

        bookAdapterBusiness = BookAdapter()
        rv_genre_business.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_genre_business.adapter = bookAdapterBusiness
    }


    private fun initView() {
        fb_add.setOnClickListener {
            openActivity(AddActivity::class.java)
        }

    }


    override fun getLayout(): Int = R.layout.activity_main

    override fun getContext(): Context = this

    override fun getViewModel(): MainViewModel =
        ViewModelProvider(this).get(MainViewModel::class.java)


    override fun getBindingVariable(): Int = BR.viewModel

    override fun onDestroy() {
        super.onDestroy()
//        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }


}



