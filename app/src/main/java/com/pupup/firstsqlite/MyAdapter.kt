package com.pupup.firstsqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context,val listItem:ArrayList<User>,val interfaceClass:ourInterface) : RecyclerView.Adapter<MyAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val email : TextView = itemView.findViewById(R.id.email)
        val password : TextView = itemView.findViewById(R.id.password)
        val id : TextView = itemView.findViewById(R.id.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recycel_view_design,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = listItem[position]
        holder.email.text = p0.email.toString()
        holder.password.text=p0.password.toString()
        holder.id.text=p0.userId.toString()

        // set interface fun on item click
        holder.itemView.setOnLongClickListener {
            interfaceClass.clickOnItem(position)
            true
        }
    }

    // create interface function
    interface ourInterface{
        fun clickOnItem(position: Int)
    }

}