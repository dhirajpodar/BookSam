package com.example.booksam.add

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhiraj.base.BaseActivity
import com.example.booksam.repo.Book
import com.example.booksam.R
import com.example.booksam.add.bottomSheet.BottomSheetOptionPickedListener
import com.example.booksam.add.bottomSheet.SelectBottomSheetFragment
import com.example.booksam.databinding.ActivityAddBinding
import com.example.booksam.common.Field
import com.example.booksam.common.Genre
import com.example.booksam.main.MainActivity
import com.example.booksam.utils.CustomEditText
import com.example.extension.setLog
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.custom_book_view.*
import kotlinx.android.synthetic.main.custom_rating_dialog.view.*
import java.util.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    private var rating: Float = 0.0F
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
        initObserver()
    }

    private fun initObserver() {
        getViewModel().inserted.observe(this, Observer {
            if (it) {
                startActivity(Intent(this@AddActivity, MainActivity::class.java))
                finish()
            }
        })

    }


    private fun initIntent() {
        iv_delete.visibility = View.GONE
        val data = intent.getStringExtra("book_data")
        getViewModel().checkIntentData(data)
        getViewModel().book?.let {
            this.book = it
            iv_delete.visibility = View.VISIBLE
            setLog("AddActivity:::$book")
            setBookInFields(it)
        }

    }

    private fun setBookInFields(book: Book) {
        et_book_name.setText(book.title)
        et_author.setText(book.author)
        et_genre.setText(book.genre)
        checkbox_liked.isChecked = book.favourite

    }


    private fun initView() {
        bottomSheetDialogFragment =
            SelectBottomSheetFragment(object : BottomSheetOptionPickedListener {
                override fun optionPicked(genre: Genre) {
                    et_genre.setText(genre.genre.toUpperCase(Locale.ENGLISH))
                    bottomSheetDialogFragment.dismiss()
                }
            })


        et_genre.getEditText().setOnClickListener {
            bottomSheetDialogFragment.show(supportFragmentManager, "bottom_sheet")
        }

        et_rating.getEditText().setOnClickListener {
            val ratingDialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.custom_rating_dialog, null)
            view.rating_bar.rating = this.rating
            ratingDialog.apply {
                setView(view)
                setCancelable(false)
                setPositiveButton("Okay", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, p1: Int) {
                        rating = view.rating_bar.rating
                        et_rating.setText(rating.toString())
                        dialog?.dismiss()
                    }
                })
                create()
                show()
            }


        }

        btn_add.setOnClickListener {
            val name = et_book_name.text()
            val author = et_author.text()
            val genre = et_genre.text()
            val liked = checkbox_liked.isChecked
            getViewModel().vaidateFields(name, author, genre, liked, rating)

            getViewModel().erroMessage.observe(this, Observer { errorMessage ->
                if (errorMessage.hasError) {
                    showError(errorMessage.field!!, errorMessage.message!!)
                } else {
                    setNullToError()
                }
            })
        }

        iv_delete.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun setNullToError() {
        et_book_name.setError(null)
        et_author.setError(null)
        et_genre.setError(null)
    }

    private fun showError(field: Field, message: String) {
        when (field) {
            Field.TITLE -> et_book_name.setError(message)
            Field.AUTHOR -> et_author.setError(message)
            Field.GENRE -> et_genre.setError(message)
//            Field.RATING -> rating_bar.error = message
            else -> {
            }
        }
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete this?")
            .setPositiveButton(
                "Yes"
            ) { dialog, i ->
                getViewModel().delete(book!!)
                showToast("${book?.title} is deleted.")
                openActivity(MainActivity::class.java)
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


    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun getLayout(): Int = R.layout.activity_add

    override fun getContext(): Context = this

    override fun getViewModel(): AddViewModel =
        ViewModelProvider(this).get(AddViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

}


