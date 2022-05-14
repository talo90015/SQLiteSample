package com.talo.sqlitesample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDAO {
    @Insert
    //新增介面
    fun add(expense: Expense)

    //查詢介面
    @Query("select * from Expense")
    fun getAll(): List<Expense>
}