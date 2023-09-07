package com.example.nyobaukl.transaksi

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.nyobaukl.MainActivity
import com.example.nyobaukl.R

class Transaksi : AppCompatActivity(), View.OnClickListener {

    private lateinit var btn_tambah_transaksi: Button
    private lateinit var btn_lihat_transaksi: Button
    private lateinit var btn_back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@Transaksi.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@Transaksi, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        btn_tambah_transaksi = findViewById(R.id.btn_tambahTransaksi)
        btn_lihat_transaksi = findViewById(R.id.btn_checkout)
        btn_back = findViewById(R.id.btn_back)
        btn_tambah_transaksi.setOnClickListener(this)
        btn_lihat_transaksi.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_tambahTransaksi -> {
                val intent = Intent(this, ListMenuTransaksi::class.java)
                startActivity(intent)
            }
            R.id.btn_checkout -> {
                val intent = Intent(this, Checkout::class.java)
                startActivity(intent)
            }
            R.id.btn_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}