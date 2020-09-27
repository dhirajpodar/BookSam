package com.example.booksam.add

import android.app.Activity
import android.content.Context
import android.content.Intent
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
        initView()
    }


    private fun initView() {
        var replyIntent = Intent()
        btn_add.setOnClickListener {
            val name = et_book_name.text.toString()
            val author = et_author.text.toString()
            val book = Book( name, author)
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
