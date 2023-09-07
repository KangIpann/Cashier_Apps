package com.example.nyobaukl.meja

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.nyobaukl.MainActivity
import com.example.nyobaukl.R

class Meja : AppCompatActivity(), View.OnClickListener {

    private lateinit var btn_tambah_reservasi: Button
    private lateinit var btn_lihat_reservasi: Button
    private lateinit var btn_back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@Meja.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@Meja, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja)

        btn_tambah_reservasi = findViewById(R.id.btn_tambahReservasi)
        btn_lihat_reservasi = findViewById(R.id.btn_listReservasi)
        btn_back = findViewById(R.id.btn_back)
        btn_lihat_reservasi.setOnClickListener(this)
        btn_tambah_reservasi.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_tambahReservasi -> {
                val intent = Intent(this, TambahReservasi::class.java)
                startActivity(intent)
            }
            R.id.btn_listReservasi -> {
                val intent = Intent(this, ListReservasi::class.java)
                startActivity(intent)
            }
            R.id.btn_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}