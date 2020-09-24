package com.example.booksam.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.booksam.Book
import com.example.booksam.R
import com.example.booksam.databinding.ActivityAddBinding
import com.example.booksam.main.MainActivity
import com.example.extension.toJsonString
import com.example.repo.BookDataBase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    private val CODE = "SUCCESS"
    lateinit var bookDataBase: BookDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabase()
        initView()
    }

    private fun initDatabase() {
        bookDataBase = BookDataBase.getInstance(this)
    }

    private fun initView() {
        var replyIntent = Intent()
        btn_add.setOnClickListener {
            val name = et_book_name.text.toString()
            val author = et_author.text.toString()
            val book = Book(11,name,author)
            replyIntent.putExtra(CODE,book.toJsonString())
            setResult(Activity.RESULT_OK,replyIntent)
            finish()
        }
    }


    override fun getLayout(): Int = R.layout.activity_add

    override fun getContext(): Context = this

    override fun getViewModel(): AddViewModel =
        ViewModelProvider(this).get(AddViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel


}
