package com.talo.sqlitesample


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.talo.sqlitesample.databinding.ActivitySecondBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    val data = arrayListOf<Expense>(
        Expense("2022/5/15", "AA", 10),
        Expense("2022/5/16", "BB", 20),
        Expense("2022/5/17", "CC", 30),
        Expense("2022/5/18", "DD", 40))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fabClick()
    }
    private fun fabClick(){
        binding.fab.setOnClickListener {
            /*databaseBuilder
                firstValue -> 指定當前Activity
                secondValue-> 產生資料庫類別(ExpenseDatabase抽象類別)
                thirdValue -> 資料庫名稱
            */
            val database = Room.databaseBuilder(this,
            ExpenseDatabase::class.java, "expanse.db")
                .build()

            //避免在UI執行緒運行資料庫存取耗時工作
            Executors.newSingleThreadExecutor().execute {
                for (expense in data){
                    database.expenseDao().add(expense)
                }

            }
            Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show()
        }
    }
}