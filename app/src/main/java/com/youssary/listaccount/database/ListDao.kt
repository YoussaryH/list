package com.youssary.listaccount.database

import androidx.room.*

@Dao
interface ListDao {

    @Query("SELECT * FROM ListDB")
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



    @Query("SELECT * FROM ListDB as t1 INNER JOIN( SELECT codeId,MAX(date) as fecha FROM ListDB GROUP BY date)as t2 ON t1.date = t2.fecha AND t1.date = t2.fecha and isdateok ORDER BY t1.date desc")
    fun getData(): List<ListDB>

}