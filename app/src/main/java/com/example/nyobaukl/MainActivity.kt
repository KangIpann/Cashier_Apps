package com.example.nyobaukl

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.nyobaukl.databinding.ActivityMainBinding
import com.example.nyobaukl.meja.Meja
import com.example.nyobaukl.menu.Menu
import com.example.nyobaukl.transaksi.Transaksi
import com.example.nyobaukl.user.DataUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogOut : Button
    private lateinit var btnUser : Button
    private lateinit var btnMenu : Button
    private lateinit var btnMeja : Button
    private lateinit var btnTransaksi : Button
    private lateinit var usernameView : TextView
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@MainActivity.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_light)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogOut = findViewById(R.id.btn_LogOut)
        btnMenu = findViewById(R.id.menu)
        btnUser = findViewById(R.id.dataUser)
        btnMeja = findViewById(R.id.meja)
        btnTransaksi = findViewById(R.id.transaksi)
        usernameView = findViewById(R.id.username)
        val currentUser = user.currentUser!!.uid
        val currentGmail = user.currentUser!!.email

//        currentUser?.let { uid ->
//            val usersDb = firestore.collection("User").document(uid)
//            usersDb.get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        val userName = document.getString("name")
//                        if (userName != null) {
//                            usernameView.text = userName
//                        }
//                    } else {
//                        usernameView.text = "User document does not exist"
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    println("Error getting user document: $exception")
//                }
//        }
            val usersDb = firestore.collection("User")
            if (currentUser != null) {
                if (currentGmail != null) {
                    usersDb.document(currentGmail).get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document.exists()) {
                                val username = document.getString("name").toString()
                                usernameView.text = "${usernameView.text} ${username.capitalize()}"
                            } else {
                                usernameView.text = currentUser
                            }
                        } else {
                            task.exception?.message?.let {
                                Log.d(TAG, it)
                            }
                        }
                    }.toString()
                }
            }
            btnTransaksi.setOnClickListener {
            val intent = Intent(this, Transaksi::class.java)
            startActivity(intent)
            }
            btnUser.setOnClickListener {
                val intent = Intent(this, DataUser::class.java)
                startActivity(intent)
            }
            btnMenu.setOnClickListener {
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }
            btnMeja.setOnClickListener {
                val intent = Intent(this, Meja::class.java)
                startActivity(intent)
            }
            btnLogOut.setOnClickListener {
                user.signOut()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }


//        binding.btnLogOut.setOnClickListener{
//            user.signOut()
//            startActivity(
//                Intent(
//                    this,
//                    Login::class.java
//                )
//            )
//            finish()
//        }
    }