package com.example.nyobaukl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nyobaukl.R
import com.example.nyobaukl.model.dataModel

class DataAdapter (private var dataList:List<dataModel>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDataName : TextView = itemView.findViewById(R.id.tv_namaUser)
        val tvJob : TextView = itemView.findViewById(R.id.tv_jobdesk)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.data_user_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = dataList[position]
        holder.tvDataName.text = currentData.name
        holder.tvJob.text = currentData.email
    }



    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setUserList(dataList: List<dataModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

}