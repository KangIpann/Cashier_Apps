package com.example.nyobaukl.menu

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.nyobaukl.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class TambahMenu : AppCompatActivity() {
    private lateinit var img_menu : ImageView
    private lateinit var et_namaMenu : EditText
    private lateinit var et_hargaMenu : EditText
    private lateinit var et_deskripsiMenu : EditText
    private lateinit var btn_tambah : Button
    private lateinit var dbImage : FirebaseStorage
    private lateinit var dbData : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this@TambahMenu.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@TambahMenu, android.R.color.holo_orange_light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_menu)
        dbImage = Firebase.storage
        dbData = FirebaseFirestore.getInstance()

        img_menu = findViewById(R.id.iv_menu)
        et_namaMenu = findViewById(R.id.namaMenu)
        et_hargaMenu = findViewById(R.id.hargaMenu)
        et_deskripsiMenu = findViewById(R.id.deskripsiMenu)
        btn_tambah = findViewById(R.id.btnTambah)

        img_menu.setOnClickListener{
            selectImage()
        }
        btn_tambah.setOnClickListener{
            uploadImage()
        }
    }

    private fun selectImage() {
        val item = arrayOf<CharSequence>("Kamera", "Galeri")
        AlertDialog.Builder(this)
            .setTitle("Ambil Gambar Dari")
            .setItems(item) { dialog, which ->
                when (which) {
                    0 -> {
                        // Open camera
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, 100)
                    }
                    1 -> {
                        // Open gallery
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, 200)
                    }
                }
            }
            .create()
            .show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Get image from camera
            val image = data?.extras?.get("data") as Bitmap
            img_menu.setImageBitmap(image)
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            // Get image from gallery
            val uri = data?.data
            img_menu.setImageURI(uri)
        }
    }

    private fun uploadImage() {
        // Upload image to Firebase Storage
        img_menu.isDrawingCacheEnabled = true
        img_menu.buildDrawingCache()
        val bitmap = (img_menu.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val storageRef = dbImage.reference
        val imageRef = storageRef.child("images/${et_namaMenu.text}.jpg") // Use "nama" as the image file name
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {

            Toast.makeText(this, "Upload Gagal", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->

            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageURL = uri.toString()


                saveData(imageURL)
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal mendapatkan URL gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveData(imageURL: String) {
        val menu = hashMapOf(
            "nama" to et_namaMenu.text.toString(),
            "harga" to et_hargaMenu.text.toString(),
            "deskripsi" to et_deskripsiMenu.text.toString(),
            "gambar" to imageURL
        )

        dbData.collection("menu")
            .add(menu)
            .addOnSuccessListener { documentReference ->
                val menuId = documentReference.id // Get the AUTO-ID generated by Firestore
                // Add the AUTO-ID to the menu document
                dbData.collection("menu")
                    .document(menuId)
                    .update("id", menuId)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show()
            }
    }
}