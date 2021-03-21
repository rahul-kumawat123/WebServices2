package com.example.webservices2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomAdapter(private val context: Context,private val dataList: List<DataModel>): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {


    class CustomViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.nameDataTextView)
        val messageTv: TextView = view.findViewById(R.id.messageDataTextView)
        val imageIv: ImageView = view.findViewById(R.id.imageDataImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.nameTv.text = dataList[position].postName
        holder.messageTv.text = dataList[position].postMsg

        Glide.with(context)
            .load(dataList[position].postImageURL)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.imageIv)

    }

    override fun getItemCount(): Int {
       return dataList.size
    }
}