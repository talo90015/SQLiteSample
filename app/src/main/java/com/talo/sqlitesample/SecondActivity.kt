package com.talo.sqlitesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.talo.sqlitesample.databinding.ActivitySecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    lateinit var database: ExpenseDatabase

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

        database = Room.databaseBuilder(this,
            ExpenseDatabase::class.java, "expanse.db")
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            val expenses = database.expenseDao().getAll()
            val adapter = object : RecyclerView.Adapter<ViewHolder>(){
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
                    return ViewHolder(view)
                }

                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    val exp = expenses[position]
                    holder.date.text = exp.date
                    holder.info.text = exp.info
                    holder.amount.text = exp.amount.toString()
                }

                override fun getItemCount(): Int {
                    return expenses.size
                }
            }
            runOnUiThread {
                binding.recycleView.setHasFixedSize(true)
                binding.recycleView.layoutManager = LinearLayoutManager(this@SecondActivity)
                binding.recycleView.adapter = adapter
            }
        }

    }
    private fun fabClick(){
        binding.fab.setOnClickListener {
            /*databaseBuilder
                firstValue -> 指定當前Activity
                secondValue-> 產生資料庫類別(ExpenseDatabase抽象類別)
                thirdValue -> 資料庫名稱
            */
            database = Room.databaseBuilder(this,
            ExpenseDatabase::class.java, "expanse.db")
                .build()

            //查詢database
            CoroutineScope(Dispatchers.IO).launch {
                val expenses = database.expenseDao().getAll()
                Log.d("list : ", expenses.size.toString())
            }

            //避免在UI執行緒運行資料庫存取耗時工作
            Executors.newSingleThreadExecutor().execute {
                for (expense in data){
                    database.expenseDao().add(expense)
                }

            }
            Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show()
        }
    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.date)
        val info = itemView.findViewById<TextView>(R.id.info)
        val amount = itemView.findViewById<TextView>(R.id.amount)
    }
}