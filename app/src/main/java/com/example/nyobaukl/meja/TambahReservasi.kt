package com.example.nyobaukl.meja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.nyobaukl.R
import com.example.nyobaukl.model.dataMeja
import com.example.nyobaukl.model.dataTransaksi
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class TambahReservasi : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var etNomorMeja: EditText
    private lateinit var btnTambahMeja: Button
    private lateinit var spnStatus : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@TambahReservasi.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@TambahReservasi, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_reservasi)

        db = FirebaseFirestore.getInstance()

        etNomorMeja = findViewById(R.id.et_namaMeja)
        btnTambahMeja = findViewById(R.id.btn_nambahMeja)
        spnStatus = findViewById(R.id.spinner_status)

        setupSpinnerStatus()

        btnTambahMeja.setOnClickListener {
            val idTransaksi = UUID.randomUUID().toString()
            val nomorMeja = etNomorMeja.text.toString()
            val status = spnStatus.selectedItem.toString()
            val meja = dataMeja(idTransaksi, nomorMeja, status)
                db.collection("meja")
                    .document(idTransaksi)
                    .set(meja)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Reservasi berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Reservasi gagal disimpan", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    private fun setupSpinnerStatus() {
        val paymentMethods = listOf("Reserved","Kosong")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnStatus.adapter = adapter
    }
    }


