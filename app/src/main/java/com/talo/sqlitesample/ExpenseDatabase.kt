package com.talo.sqlitesample

import androidx.room.Database
import androidx.room.RoomDatabase

//建立抽象類別繼承RoomDatabase
@Database(entities = [Expense::class], version = 1)
abstract  class  ExpenseDatabase : RoomDatabase(){

    //建立抽象類別存取ExpenseDAO
    abstract fun expenseDao(): ExpenseDAO
}
