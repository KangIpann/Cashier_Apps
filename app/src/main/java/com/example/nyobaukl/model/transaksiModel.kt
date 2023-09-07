package com.example.nyobaukl.model

data class dataTransaksi(
    val id_transaksi: String = "",
    val nama_customer: String = "",
    val menu: String = "",
    val harga: String = "",
    val tanggal: String = "",
    val metode_pembayaran: String = ""
)