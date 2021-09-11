package com.example.lifergame
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterEvents(private var eventsMutableList: MutableList<String>): RecyclerView.Adapter<RecyclerAdapterEvents.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemEvents: TextView = itemView.findViewById(R.id.tv_notifications_events)
        val itemEventsList: RecyclerView = itemView.findViewById(R.id.rv_list_of_events)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notifications_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemEvents.text = eventsMutableList[position]
        holder.
    }

    override fun getItemCount(): Int {
        return eventsMutableList.size
    }
}