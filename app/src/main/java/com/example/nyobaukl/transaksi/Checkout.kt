package com.example.nyobaukl.transaksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nyobaukl.R
import com.example.nyobaukl.adapter.TransaksiAdapter
import com.example.nyobaukl.model.dataTransaksi
import com.google.firebase.firestore.FirebaseFirestore

class Checkout : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    lateinit var transaksiAdapter: TransaksiAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@Checkout.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@Checkout, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        recyclerView = findViewById(R.id.rv_transaksi)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        transaksiAdapter = TransaksiAdapter(ArrayList())
        recyclerView.adapter = transaksiAdapter

        getDataFromFirestore()

        swipeRefreshLayout.setOnRefreshListener {
            getDataFromFirestore()
        }
    }

    private fun getDataFromFirestore() {

        db.collection("transaksi")
            .get()
            .addOnSuccessListener { result ->
                val transaksiList = ArrayList<dataTransaksi>()
                for (document in result) {
                    val idTransaksi = document.getString("id_transaksi") ?: ""
                    val tanggal = document.getString("tanggal") ?: ""
                    val namaCustomer = document.getString("nama_customer") ?: ""
                    val menu = document.getString("menu") ?: ""
                    val harga = document.getString("harga") ?: ""
                    val metodePembayaran = document.getString("metode_pembayaran") ?: ""

                    val transaksi = dataTransaksi(
                        idTransaksi,
                        tanggal,
                        namaCustomer,
                        menu,
                        harga,
                        metodePembayaran
                    )
                    transaksiList.add(transaksi)
                }

                transaksiAdapter.transaksiList = transaksiList
                transaksiAdapter.notifyDataSetChanged()

                swipeRefreshLayout.isRefreshing = false
            }
            .addOnFailureListener { exception ->

                swipeRefreshLayout.isRefreshing = false
            }
    }
}