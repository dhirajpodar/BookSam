package com.example.booksam.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.repo.Book
import com.example.booksam.R
import com.example.booksam.databinding.ActivityAddBinding
import com.example.extension.toJsonString
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    private val CODE = "SUCCESS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        initView()
    }


    private fun initView() {
        val replyIntent = Intent()
        btn_add.setOnClickListener {
            val name = et_book_name.text.toString()
            val author = et_author.text.toString()
            val genre = et_genre.text.toString()
            val favourite = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                switch_fav.showText
            } else {
                false
            }
            val rating = rating_bar.rating
            val book = Book(name, author, genre, rating, favourite)
            replyIntent.putExtra(CODE, book.toJsonString())
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun getLayout(): Int = R.layout.activity_add

    override fun getContext(): Context = this

    override fun getViewModel(): AddViewModel =
        ViewModelProvider(this).get(AddViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel


}
