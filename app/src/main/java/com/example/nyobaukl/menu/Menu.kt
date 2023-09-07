package com.example.nyobaukl.menu

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Menu : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBack : ImageView
    private lateinit var daftarMenu : Button
    private lateinit var tambahMenu : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@Menu.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@Menu, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        tambahMenu = findViewById(R.id.tambahMenu)
        daftarMenu = findViewById(R.id.daftarMenu)
        btnBack = findViewById(R.id.btn_back)
        tambahMenu.setOnClickListener(this)
        daftarMenu.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tambahMenu ->{
                val intent = Intent(this, TambahMenu::class.java)
                startActivity(intent)
            }
            R.id.daftarMenu ->{
                val intent = Intent(this, DaftarMenu::class.java)
                startActivity(intent)
            }
            R.id.btn_back ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}