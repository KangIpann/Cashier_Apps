package com.example.nyobaukl.meja

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.nyobaukl.R
import com.google.firebase.firestore.FirebaseFirestore

class UbahMeja : AppCompatActivity() {
    private lateinit var etNomorMeja : EditText
    private lateinit var etStatusMeja : EditText
    private lateinit var btnUpdate : Button
    private lateinit var mejaId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@UbahMeja.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor =
            ContextCompat.getColor(this@UbahMeja, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_meja)
        val db = FirebaseFirestore.getInstance()

        etNomorMeja = findViewById(R.id.et_nomerMeja)
        btnUpdate = findViewById(R.id.btn_ubahMeja)
        etStatusMeja = findViewById(R.id.statusMeja)
        mejaId = intent.getStringExtra("id").toString()
        var nomorMeja = intent.getStringExtra("meja")
        var statusReserv = intent.getStringExtra("status")

        etNomorMeja.setText(nomorMeja)
        etStatusMeja.setText(statusReserv)

        btnUpdate.setOnClickListener {
            val namaMenuBaru = etNomorMeja.text.toString().trim()
            val status = etStatusMeja.text.toString().trim()

            if (mejaId != null) {
                db.collection("meja")
                    .document(mejaId!!)
                    .update(
                        mapOf(
                            "meja" to namaMenuBaru,
                            "status" to status,
                        )
                    )
                    .addOnSuccessListener {
                        Toast.makeText(this, "Reservasi berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Reservasi gagal diperbarui", Toast.LENGTH_SHORT).show()
                    }

                finish()
            }
        }
    }}
