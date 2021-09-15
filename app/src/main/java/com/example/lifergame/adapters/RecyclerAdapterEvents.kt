package com.example.lifergame.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lifergame.R

class RecyclerAdapterEvents(private val events: String, private val colorOfBackground: Int): RecyclerView.Adapter<RecyclerAdapterEvents.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clListOfEvents: ConstraintLayout = itemView.findViewById(R.id.cl_child_event)
        val tvEvents: TextView = itemView.findViewById(R.id.tv_notifications_events)
        val mutableListOfEvents: List<String> = events.split(";")


        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on ${position}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.child_notifications_events_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val splittedEvents: List<String> = holder.mutableListOfEvents
        holder.clListOfEvents.setBackgroundColor(colorOfBackground)
        holder.tvEvents.text = "- ${splittedEvents[position]}"
    }

    override fun getItemCount(): Int {
        val splittedEvents: List<String> = events.split(";")
        return splittedEvents.size
    }
}