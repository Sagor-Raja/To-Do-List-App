package com.example.to_dolist

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

class CalculatorActivity : AppCompatActivity() {

    private lateinit var adapter: TransactionAdapter
    private val transactionList = ArrayList<Transaction>()

    private lateinit var tvSummaryTitle: TextView
    private lateinit var tvTotalIncome: TextView
    private lateinit var tvTotalExpense: TextView
    private lateinit var tvBalance: TextView
    private lateinit var tvSelectedPeriod: TextView
    private lateinit var tabLayout: TabLayout
    
    private val currentDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val rvDataList = findViewById<RecyclerView>(R.id.rvDataList)
        val fabAdd = findViewById<ExtendedFloatingActionButton>(R.id.fabAdd)
        val btnPrevious = findViewById<ImageButton>(R.id.btnPrevious)
        val btnNext = findViewById<ImageButton>(R.id.btnNext)
        
        tabLayout = findViewById(R.id.tabLayout)
        tvSelectedPeriod = findViewById(R.id.tvSelectedPeriod)
        tvSummaryTitle = findViewById(R.id.tvSummaryTitle)
        tvTotalIncome = findViewById(R.id.tvTotalIncome)
        tvTotalExpense = findViewById(R.id.tvTotalExpense)
        tvBalance = findViewById(R.id.tvBalance)

        adapter = TransactionAdapter(transactionList) { position ->
            if (tabLayout.selectedTabPosition == 0) {
                showEditDeleteDialog(position)
            } else {
                Toast.makeText(this, "সারাংশ এডিট বা ডিলিট করা যাবে না", Toast.LENGTH_SHORT).show()
            }
        }
        rvDataList.layoutManager = LinearLayoutManager(this)
        rvDataList.adapter = adapter

        updatePeriodDisplay()
        loadDataForSelectedTab()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updatePeriodDisplay()
                loadDataForSelectedTab()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        btnPrevious.setOnClickListener {
            changePeriod(-1)
        }

        btnNext.setOnClickListener {
            changePeriod(1)
        }

        fabAdd.setOnClickListener {
            if (tabLayout.selectedTabPosition == 0) {
                showAddTransactionDialog()
            } else {
                Toast.makeText(this, "লেনদেন যোগ করতে 'দিন' ট্যাব সিলেক্ট করুন", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditDeleteDialog(position: Int) {
        val transaction = transactionList[position]
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_transaction, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTransactionTitle)
        val etAmount = dialogView.findViewById<EditText>(R.id.etTransactionAmount)
        
        etTitle.setText(transaction.title)
        etAmount.setText(transaction.amount.toString())
        
        AlertDialog.Builder(this)
            .setTitle("লেনদেন পরিবর্তন করুন")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newTitle = etTitle.text.toString()
                val newAmount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
                if (newTitle.isNotEmpty()) {
                    transactionList[position] = Transaction(transaction.id, newTitle, newAmount, transaction.type)
                    adapter.notifyItemChanged(position)
                    calculateAndShowSummary(tvSummaryTitle.text.toString())
                    Toast.makeText(this, "আপডেট করা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Delete") { _, _ ->
                transactionList.removeAt(position)
                adapter.notifyItemRemoved(position)
                calculateAndShowSummary(tvSummaryTitle.text.toString())
                Toast.makeText(this, "মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("বাতিল", null)
            .show()
    }

    private fun changePeriod(amount: Int) {
        when (tabLayout.selectedTabPosition) {
            0 -> currentDate.add(Calendar.DAY_OF_YEAR, amount)
            1 -> currentDate.add(Calendar.MONTH, amount)
            2 -> currentDate.add(Calendar.YEAR, amount)
        }
        updatePeriodDisplay()
        loadDataForSelectedTab()
    }

    private fun updatePeriodDisplay() {
        val format = when (tabLayout.selectedTabPosition) {
            0 -> SimpleDateFormat("dd MMM", Locale("bn", "BD"))
            1 -> SimpleDateFormat("MMMM yyyy", Locale("bn", "BD"))
            2 -> SimpleDateFormat("yyyy", Locale("bn", "BD"))
            else -> SimpleDateFormat("dd MMM", Locale("bn", "BD"))
        }
        tvSelectedPeriod.text = format.format(currentDate.time)
    }

    private fun loadDataForSelectedTab() {
        when (tabLayout.selectedTabPosition) {
            0 -> loadDayData()
            1 -> loadMonthData()
            2 -> loadYearData()
        }
    }

    private fun showAddTransactionDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_transaction, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTransactionTitle)
        val etAmount = dialogView.findViewById<EditText>(R.id.etTransactionAmount)

        AlertDialog.Builder(this)
            .setTitle("নতুন লেনদেন যোগ করুন")
            .setView(dialogView)
            .setPositiveButton("আয় (Income)") { _, _ ->
                addTransaction(etTitle.text.toString(), etAmount.text.toString(), "Income")
            }
            .setNegativeButton("খরচ (Expense)") { _, _ ->
                addTransaction(etTitle.text.toString(), etAmount.text.toString(), "Expense")
            }
            .setNeutralButton("বাতিল", null)
            .show()
    }

    private fun addTransaction(title: String, amountStr: String, type: String) {
        if (title.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "সবগুলো ঘর পূরণ করুন", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull() ?: 0.0
        val newTransaction = Transaction(transactionList.size + 1, title, amount, type)

        transactionList.add(0, newTransaction)
        adapter.notifyItemInserted(0)

        calculateAndShowSummary(tvSummaryTitle.text.toString())
        Toast.makeText(this, "লেনদেন সফলভাবে যোগ হয়েছে", Toast.LENGTH_SHORT).show()
    }

    private fun loadDayData() {
        transactionList.clear()
        transactionList.add(Transaction(1, "অফিস বেতন", 25000.0, "Income"))
        transactionList.add(Transaction(2, "রিকশা ভাড়া", 50.0, "Expense"))
        adapter.notifyDataSetChanged()
        calculateAndShowSummary("আজকের সারাংশ")
    }

    private fun loadMonthData() {
        transactionList.clear()
        transactionList.add(Transaction(1, "০১ জানু", 500.0, "Expense"))
        transactionList.add(Transaction(2, "০২ জানু", 25000.0, "Income"))
        adapter.notifyDataSetChanged()
        calculateAndShowSummary("মাসের সারাংশ")
    }

    private fun loadYearData() {
        transactionList.clear()
        transactionList.add(Transaction(1, "জানুয়ারি", 12500.0, "Expense"))
        transactionList.add(Transaction(2, "ফেব্রুয়ারি", 10000.0, "Expense"))
        adapter.notifyDataSetChanged()
        calculateAndShowSummary("বছরের সারাংশ")
    }

    private fun calculateAndShowSummary(title: String) {
        var totalIncome = 0.0
        var totalExpense = 0.0

        for (item in transactionList) {
            if (item.type == "Income") {
                totalIncome += item.amount
            } else {
                totalExpense += item.amount
            }
        }

        val balance = totalIncome - totalExpense

        tvSummaryTitle.text = title
        tvTotalIncome.text = "+ ৳ ${totalIncome.toInt()}"
        tvTotalExpense.text = "- ৳ ${totalExpense.toInt()}"
        tvBalance.text = "৳ ${balance.toInt()}"
    }
}