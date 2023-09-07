package com.example.nyobaukl.transaksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.nyobaukl.R
import com.example.nyobaukl.model.dataMenu
import com.example.nyobaukl.model.dataTransaksi
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import java.util.UUID

class ListMenuTransaksi : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var etNamaCustomer: EditText
    private lateinit var spinnerMenu: Spinner
    private lateinit var spinnerPayment: Spinner
    private lateinit var btnSimpanTransaksi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@ListMenuTransaksi.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@ListMenuTransaksi, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_menu_transaksi)

        db = FirebaseFirestore.getInstance()

        etNamaCustomer = findViewById(R.id.et_nama_customer)
        spinnerMenu = findViewById(R.id.spinner_menu)
        spinnerPayment = findViewById(R.id.spinner_payment)
        btnSimpanTransaksi = findViewById(R.id.btn_simpan_transaksi)

        setupSpinnerMenu()
        setupSpinnerPayment()

        btnSimpanTransaksi.setOnClickListener {
            val idTransaksi = UUID.randomUUID().toString()
            val namaCustomer = etNamaCustomer.text.toString()
            val menu = spinnerMenu.selectedItem.toString()
            val payment = spinnerPayment.selectedItem.toString()
            getHarga(menu) { harga ->
                val tanggal = getCurrentDate()
                val transaksi = dataTransaksi(idTransaksi, namaCustomer, menu, harga.toString(), tanggal, payment)
                db.collection("transaksi")
                    .document(idTransaksi)
                    .set(transaksi)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Transaksi gagal disimpan", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

    private fun setupSpinnerMenu() {
        val placeholder = "Pilih Menu"
        val listMenu = mutableListOf(placeholder)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listMenu)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMenu.adapter = adapter

        spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMenu = spinnerMenu.selectedItem.toString()
                if (selectedMenu != placeholder) {
                    getHarga(selectedMenu) { harga ->

                    }
                } else {

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        getMenuData()
    }

    private fun setupSpinnerPayment() {
        val paymentMethods = listOf("Cash", "Debit", "QRIS")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPayment.adapter = adapter
    }

    private fun getMenuData() {
        db.collection("menu")
            .get()
            .addOnSuccessListener { result ->
                val listMenu = ArrayList<String>()
                for (document in result) {
                    val menu = document.toObject(dataMenu::class.java)
                    menu.id = document.id
                    listMenu.add(menu.nama)
                }

                val adapter = spinnerMenu.adapter as ArrayAdapter<String>
                adapter.clear()
                adapter.add("Pilih Menu")
                adapter.addAll(listMenu)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun getHarga(menu: String, callback: (Int) -> Unit) {
        db.collection("menu")
            .whereEqualTo("nama", menu)
            .get()
            .addOnSuccessListener { result ->
                var harga = 0
                for (document in result) {
                    val menu = document.toObject(dataMenu::class.java)
                    menu.id = document.id
                    harga = menu.harga.toInt()
                }
                callback(harga)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: $exception", Toast.LENGTH_SHORT).show()

                callback(0)
            }
    }
}