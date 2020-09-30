package com.example.booksam.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiraj.base.BaseActivity
import com.example.android.service.RetrofitClient
import com.example.booksam.BR
import com.example.repo.Book
import com.example.booksam.R
import com.example.booksam.add.AddActivity
import com.example.booksam.bookdetail.BookDetail
import com.example.booksam.databinding.ActivityMainBinding
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
    private var bookAdapter: BookAdapter? = null
    private var books: List<Book>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setViewModel()
        initRecyclerView()
        initObservers()
        initFB()
    }


    private fun initObservers() {
        mainViewModel.books.observe(this, Observer { books ->
            books?.let {
                this.books = it
                bookAdapter?.setWords(it)
            }
        })
    }


    private fun setViewModel() {
        mainViewModel = getViewModel()

    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter(this)
        rv_itemView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_itemView.adapter = bookAdapter
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
//                getMeaning(book.title)
                Unit
            }
        }
    }

    private fun getMeaning(title: String) {
        val compositeDisposable = CompositeDisposable()
        val disposable = Observable.create(ObservableOnSubscribe<WordMeaning> { emitter ->

            try {
                val apiService = RetrofitClient.getApiService()
                val response = apiService.getMeaning(title)
                val wordMeaning = response as List<WordMeaning>
                emitter.onNext(wordMeaning[0])
            } catch (e: Exception) {
                emitter.onError(e.fillInStackTrace())
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dictionary ->
                val defination = dictionary.meanings[0].definitions[0].definition
                val partOfSpeech = dictionary.meanings[0].partOfSpeech
                val audio = dictionary.phonetics[0].audio
            }, {
                setLog(it.localizedMessage)
            })

        compositeDisposable.add(disposable)
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

    override fun optionPicked(option: String, position: Int) {
        if (option.equals("SUMMARY")) {
            books?.let {
                val book = books!!.get(position)
                val intent = Intent(this, BookDetail::class.java)
                intent.putExtra("book_data", book.toJsonString())
                startActivity(intent)
            }
        }
    }
}


