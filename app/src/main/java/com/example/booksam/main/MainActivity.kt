package com.example.booksam.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiraj.base.BaseActivity
import com.example.Utils
import com.example.android.service.RetrofitClient
import com.example.booksam.BR
import com.example.repo.Book
import com.example.booksam.R
import com.example.booksam.add.AddActivity
import com.example.booksam.bookdetail.BookDetail
import com.example.booksam.databinding.ActivityMainBinding
import com.example.common.Crud
import com.example.common.Genre
import com.example.common.Option
import com.example.extension.setLog
import com.example.extension.toJsonString
import com.example.extension.toObj
import com.example.repo.service.response.WordMeaning
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), OptionSelectedListener {
    private val REQUEST_CODE = 100
    private lateinit var mainViewModel: MainViewModel
    private var bookAdapterSpiritual: BookAdapter? = null
    private var bookAdapterBusiness: BookAdapter? = null
    private var books: List<Book>? = null

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
        initFB()
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
                    viewPager_image.setCurrentItem(0)
                } else {
                    viewPager_image.setCurrentItem(position + 1)
                }
                initTimer()
            }

            override fun onTick(p0: Long) {}

        }.start()
    }


    private fun initObservers() {
        mainViewModel.books.observe(this, Observer { books ->
            books?.let {
                this.books = it
                bookAdapterSpiritual?.setBooks(it.filter { g -> g.genre == Genre.SPIRITUAL.genre })
                bookAdapterBusiness?.setBooks(it.filter { g -> g.genre == Genre.BUSINESS.genre })
            }
        })


    }


    private fun setViewModel() {
        mainViewModel = getViewModel()

    }

    private fun initRecyclerView() {
        bookAdapterSpiritual = BookAdapter(this)
        rv_genre_spiritual.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_genre_spiritual.adapter = bookAdapterSpiritual

        bookAdapterBusiness = BookAdapter(this)
        rv_genre_business.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_genre_business.adapter = bookAdapterBusiness
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
                val operation = it.getStringExtra("crud")
                val book = dataInString?.toObj(Book::class.java)!!
                when (operation) {
                    Crud.ADD.name -> mainViewModel.insert(book)
                    Crud.UPDATE.name -> mainViewModel.update(book)
                    else -> {}
                }
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

    override fun optionPicked(option: Option, position: Int) {
        books?.let {
            val book = books!!.get(position)
            val intent: Intent
            when (option) {
                Option.SUMMARY -> {
                    intent = Intent(this, BookDetail::class.java)
                    intent.putExtra("book_data", book.toJsonString())
                }
                Option.DETAIL -> {
                    intent = Intent(this, AddActivity::class.java)
                    intent.putExtra("book_data", book.toJsonString())
                }
            }

            startActivity(intent)
        }

    }
}



