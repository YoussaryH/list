package com.youssary.listaccount.database

import androidx.room.*

@Dao
interface ListDao {

    @Query("SELECT * FROM ListDB ORDER BY id asc")
    fun getAll(): List<ListDB>

    @Query("SELECT * FROM ListDB WHERE id = :id")
    fun findById(id: Int): ListDB

    @Query("SELECT COUNT(id) FROM ListDB")
    fun listCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(listData: List<ListDB>)

    @Update
    fun updateList(listData: ListDB)

    @Query("DELETE FROM ListDB")
    fun clearAll(): Int


    @Query("SELECT * FROM ListDB WHERE date IN (SELECT MAX(date) FROM ListDB where isdateok)")
    fun getDataMax(): List<ListDB>

    @Query("SELECT * FROM ListDB WHERE date IN (SELECT MAX(date) FROM ListDB where isdateok GROUP BY id) ORDER BY date asc")
    fun getData(): List<ListDB>

}