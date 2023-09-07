package com.example.nyobaukl.menu

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
import com.google.firebase.firestore.FirebaseFirestore
import com.example.nyobaukl.adapter.MenuAdapter
import com.example.nyobaukl.model.dataMenu

class DaftarMenu : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@DaftarMenu.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@DaftarMenu, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_menu)

        recyclerView = findViewById(R.id.rv_menu)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        menuAdapter = MenuAdapter(emptyList())
        recyclerView.adapter = menuAdapter

        db = FirebaseFirestore.getInstance()

        swipeRefreshLayout.setOnRefreshListener {
            fetchData()
        }

        fetchData()
    }

    private fun fetchData() {
        db.collection("menu")
            .get()
            .addOnSuccessListener { result ->
                val menuList = ArrayList<dataMenu>()
                for (document in result) {
                    val menu = document.toObject(dataMenu::class.java)
                    menuList.add(menu)
                }
                menuAdapter.setMenuList(menuList)
                swipeRefreshLayout.isRefreshing = false
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal mengambil data: $exception", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
    }
}