package com.example.nyobaukl.meja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nyobaukl.R
import com.example.nyobaukl.adapter.MenuAdapter
import com.example.nyobaukl.adapter.ReservasiAdapter
import com.example.nyobaukl.model.dataMeja
import com.example.nyobaukl.model.dataMenu
import com.google.firebase.firestore.FirebaseFirestore

class ListReservasi : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var reservasiAdapter: ReservasiAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@ListReservasi.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@ListReservasi, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_menu)

        recyclerView = findViewById(R.id.rv_menu)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        reservasiAdapter = ReservasiAdapter(emptyList())
        recyclerView.adapter = reservasiAdapter

        db = FirebaseFirestore.getInstance()

        swipeRefreshLayout.setOnRefreshListener {
            fetchData()
        }

        fetchData()
    }

    private fun fetchData() {
        db.collection("meja")
            .get()
            .addOnSuccessListener { result ->
                val mejaList = ArrayList<dataMeja>()
                for (document in result) {
                    val meja = document.toObject(dataMeja::class.java)
                    mejaList.add(meja)
                }
                reservasiAdapter.setMejaList(mejaList)
                swipeRefreshLayout.isRefreshing = false
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal mengambil data: $exception", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
    }
    }
