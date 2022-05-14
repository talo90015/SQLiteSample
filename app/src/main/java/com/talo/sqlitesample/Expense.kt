package com.talo.sqlitesample

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Expense(
    @NonNull
    @ColumnInfo(name = "dateAt")
    var date: String,
    var info: String,
    var amount: Int) {

    //自動累加主鍵值
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}