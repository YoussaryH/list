package com.youssary.listaccount.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ListDB::class], version = 1)
abstract class ListDatabase() : RoomDatabase() {

    abstract fun ListDao(): ListDao
}