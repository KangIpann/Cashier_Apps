package com.example.nyobaukl.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nyobaukl.R
import com.example.nyobaukl.meja.UbahMeja
import com.example.nyobaukl.model.dataMeja
import com.google.firebase.firestore.FirebaseFirestore


class ReservasiAdapter(private var reservList: List<dataMeja>) :
    RecyclerView.Adapter<ReservasiAdapter.ReservViewHolder>() {

    inner class ReservViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMeja: TextView = itemView.findViewById(R.id.tv_nomerMeja)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val btnUbah: ImageView = itemView.findViewById(R.id.btn_edit)
        val btnHapus: ImageView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_reservasi_list, parent, false)
        return ReservViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservViewHolder, position: Int) {
        val currentMeja = reservList[position]
        val meja = currentMeja.meja
        holder.tvMeja.text = "Nomor Meja: ${meja}"
        holder.tvStatus.text = currentMeja.status

        holder.btnUbah.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, UbahMeja::class.java)
            intent.putExtra("id", currentMeja.id)
            intent.putExtra("meja", currentMeja.meja)
            intent.putExtra("status", currentMeja.status)
            context.startActivity(intent)
        }

        holder.btnHapus.setOnClickListener{
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("meja").document(currentMeja.id)
            docRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(holder.itemView.context, "Berhasil Menghapus Menu", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(holder.itemView.context, "Gagal Menghapus Menu", Toast.LENGTH_SHORT).show()
                }
        }
    }


    override fun getItemCount(): Int {
        return reservList.size
    }

    fun setMejaList(mejaList: List<dataMeja>) {
        this.reservList = mejaList
        notifyDataSetChanged()
    }
}