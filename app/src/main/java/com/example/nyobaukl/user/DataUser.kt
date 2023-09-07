package com.example.nyobaukl.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nyobaukl.MainActivity
import com.example.nyobaukl.R
import com.example.nyobaukl.adapter.DataAdapter
import com.example.nyobaukl.model.dataModel
import com.google.firebase.firestore.FirebaseFirestore

class DataUser : AppCompatActivity() {

    private lateinit var loadingData: TextView
    private lateinit var db: FirebaseFirestore
    private lateinit var userList: ArrayList<dataModel>
    private lateinit var btnBack: ImageView
    private lateinit var userAdapter: DataAdapter
    private lateinit var recyclerViewUser: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@DataUser.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@DataUser, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_user)

        supportActionBar?.hide()
        recyclerViewUser = findViewById(R.id.dataUser)
        recyclerViewUser.layoutManager = LinearLayoutManager(this)
        userAdapter = DataAdapter(ArrayList())
        recyclerViewUser.adapter = userAdapter

        userList = arrayListOf()

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        db = FirebaseFirestore.getInstance()
        fetchData()
    }

    private fun fetchData() {
        db.collection("User")
            .get()
            .addOnSuccessListener { result ->
                val userlist = ArrayList<dataModel>()
                for (document in result) {
                    val user = document.toObject(dataModel::class.java)
                    userlist.add(user)
                }
                userAdapter.setUserList(userlist)
            }
    }
}
