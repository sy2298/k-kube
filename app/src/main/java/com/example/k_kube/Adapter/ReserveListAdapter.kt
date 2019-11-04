package com.example.kcube.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.k_kube.R
import com.example.kcube.Data.Cube


class ReserveListAdapter(var items:ArrayList<Cube>,val context: Context):RecyclerView.Adapter<ReserveListAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener ?= null
    interface OnItemClickListener{
        fun OnItemClick(holder:ReserveListAdapter.ViewHolder,view:View,data:Cube,position:Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reserve_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }

    override fun onBindViewHolder(holder: ReserveListAdapter.ViewHolder, position: Int) {
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.name.text = items[position].name
        holder.time.text = items[position].dateList[0].time.hour_start.toString()+" : "+items[position].dateList[0].time.minute_start.toString()+" - "+items[position].dateList[items[position].dateList.size-1].time.hour_end+" : "+items[position].dateList[items[position].dateList.size-1].time.minute_end
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var name:TextView
        var time:TextView
        init{
            name = itemView.findViewById(R.id.reserve_item_name)
            time = itemView.findViewById(R.id.reserve_item_time)
            itemView.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.OnItemClick(this,it,items[position],position)
            }
        }

    }

}