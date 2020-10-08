package com.example.booksam.splash

import android.content.Intent
import android.graphics.Color
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.example.booksam.R
import com.example.booksam.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = Color.TRANSPARENT
            }
        }
        supportActionBar?.hide()
        openMainActivity()

    }


    private fun openMainActivity() {
        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onTick(p0: Long) {}

        }.start()

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
