package com.example.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.booksam.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Database(version = 2, entities = [Book::class])
abstract class BookDataBase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {

        private var instance: BookDataBase? = null
        private const val DATABASE_NAME = "book_database"


        fun getInstance(context: Context): BookDataBase {
            if (instance == null) {

                synchronized(BookDataBase::class) {
                    instance = Room.databaseBuilder(
                        context, BookDataBase::class.java
                        , DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

            }

            return instance!!
        }

    }


}