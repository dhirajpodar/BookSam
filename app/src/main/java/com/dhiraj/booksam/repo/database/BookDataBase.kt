package com.example.booksam.repo

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dhiraj.booksam.repo.database.DataConverter
import com.dhiraj.booksam.repo.database.WordPool
import com.example.booksam.common.Genre
import com.example.extension.setLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Book::class, WordPool::class], version = 2)
@TypeConverters(DataConverter::class)
abstract class BookDataBase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: BookDataBase? = null


        fun getInstance(context: Context, scope: CoroutineScope): BookDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDataBase::class.java,
                    "book_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .addCallback(callback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private fun callback(scope: CoroutineScope) = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                scope.launch {
                    INSTANCE?.let {
                        var book = Book(
                            "Book Name 1",
                            "Book Author 1",
                            Genre.SPIRITUAL.genre,
                            3F,
                            false
                        )
                        it.bookDao().insert(book)
                        setLog("Book Name 1 added with ${book.bookId}")

                        book = Book(
                            "Book Name 2",
                            "Book Author 2",
                            Genre.SPIRITUAL.genre,
                            2F,
                            false
                        )
                        it.bookDao().insert(
                            book
                        )
                        setLog("Book Name 2 added with ${book.bookId}")

                        book = Book(
                            "Book Name 3",
                            "Book Author 3",
                            Genre.BUSINESS.genre,
                            3F,
                            false
                        )
                        it.bookDao().insert(
                            book
                        )
                        setLog("Book Name 3 added with ${book.bookId}")
                        book = Book(
                            "Book Name 4",
                            "Book Author 4",
                            Genre.BUSINESS.genre,
                            2F,
                            false
                        )
                        it.bookDao().insert(
                            book
                        )
                        setLog("Book Name 4 added with ${book.bookId}")
                    }
                }
            }
        }
    }
}