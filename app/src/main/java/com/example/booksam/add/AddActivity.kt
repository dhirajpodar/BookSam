package com.example.booksam.add

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.booksam.repo.Book
import com.example.booksam.R
import com.example.booksam.add.bottomSheet.BottomSheetOptionPickedListener
import com.example.booksam.add.bottomSheet.SelectBottomSheetFragment
import com.example.booksam.databinding.ActivityAddBinding
import com.example.booksam.common.Crud
import com.example.booksam.common.Genre
import com.example.extension.setLog
import com.example.extension.toJsonString
import com.example.extension.toObj
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    private val CODE = "SUCCESS"
    private var book: Book? = null
    private lateinit var bottomSheetDialogFragment: SelectBottomSheetFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        initIntent()
        initView()

    }

    private fun initIntent() {
        iv_delete.visibility = View.GONE
        val data = intent.getStringExtra("book_data")
        data?.let {
            iv_delete.visibility = View.VISIBLE
            book = it.toObj(Book::class.java)
            setLog("AddActivity:::$book")
            setBookInFields(book!!)
        }
    }

    private fun setBookInFields(book: Book) {
        et_book_name.setText(book.title)
        et_author.setText(book.author)
        et_genre.setText(book.genre)
        rating_bar.rating = book.rating
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch_fav.showText = book.favourite
        }

    }


    private fun initView() {
        bottomSheetDialogFragment =
            SelectBottomSheetFragment(object : BottomSheetOptionPickedListener {
                override fun optionPicked(genre: Genre) {
                    et_genre.setText(genre.genre)
                    bottomSheetDialogFragment.dismiss()
                }
            })


        et_genre.setOnClickListener {
            bottomSheetDialogFragment.show(supportFragmentManager, "bottom_sheet")
        }

        btn_add.setOnClickListener {
            validate()
        }

        iv_delete.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this?")
                .setPositiveButton(
                    "Yes"
                ) { dialog, i ->
                    returnToMain(this.book, Crud.DELETE.name)
                    dialog?.dismiss()
                }
                .setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, i: Int) {
                        dialog?.dismiss()
                    }
                })
                .setCancelable(false)
                .create()

            alertDialog.show()
        }
    }

    private fun validate() {

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
        if (this.book == null) {
            returnToMain(book, Crud.ADD.name)
        } else {
            if (this.book == book) {
                returnToMain(book, Crud.NOCHANGE.name)
            } else {
                returnToMain(book, Crud.UPDATE.name)
            }
        }


    }

    fun returnToMain(book: Book?, crud: String) {
        val replyIntent = Intent()
        book?.let {
            replyIntent.putExtra(CODE, book.toJsonString())
        }
        setLog("AddActivity::::$crud")
        replyIntent.putExtra("crud", crud)

        setResult(Activity.RESULT_OK, replyIntent)
        finish()
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


