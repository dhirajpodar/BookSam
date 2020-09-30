package com.example.booksam.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.booksam.R
import com.example.booksam.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        openMainActivity()

    }


    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }

    /*private fun getData() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<Response> { emitter ->
                val bookRepo = BookRepoImpl.getInstance(this)
                val books = bookRepo.getAllBooks()

                emitter.onNext(Response(Constants.SUCCESS, books))

            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val books = response.data as MutableLiveData<List<Book>>
                    openMainActivity(books)
                }, {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                })
        )
    }*/
}
