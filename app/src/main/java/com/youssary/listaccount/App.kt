package com.youssary.listaccount

import android.app.Application
import androidx.room.Room
import com.youssary.listaccount.database.ListDatabase


class App : Application() {

    lateinit var db: ListDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            ListDatabase::class.java, "list-db"
        ).build()
    }
}