package com.example.lifergame
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterMain(private var year: MutableList<Int>, private var season: MutableList<String>): RecyclerView.Adapter<RecyclerAdapterMain.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemYear: TextView = itemView.findViewById(R.id.tv_year_fragment)
        val itemSeason: TextView = itemView.findViewById(R.id.tv_season_fragment)
        val itemSeasonImage: ImageView = itemView.findViewById(R.id.iv_season_fragment)
        val itemConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.cl_notifications_season)
        val itemColorAutumn: Int = ContextCompat.getColor(itemView.context, R.color.yellow_800_autumn)
        val itemColorWinter: Int = ContextCompat.getColor(itemView.context, R.color.cyan_600_winter)
        val itemColorSpring: Int = ContextCompat.getColor(itemView.context, R.color.light_green_A200_spring)
        val itemColorSummer: Int = ContextCompat.getColor(itemView.context, R.color.yellow_400_summer)





        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on ${position}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notifications_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemYear.text = "${year[position]}. Year"
        holder.itemSeason.text = season[position]
        when(season[position].lowercase()){
            "autumn" -> {holder.itemSeasonImage.setImageResource(R.drawable.ic_autumn);
                holder.itemConstraintLayout.setBackgroundColor(holder.itemColorAutumn)}
            "winter" -> {holder.itemSeasonImage.setImageResource(R.drawable.ic_winter);
                        holder.itemConstraintLayout.setBackgroundColor(holder.itemColorWinter)}
            "spring" -> {holder.itemSeasonImage.setImageResource(R.drawable.ic_spring);
                        holder.itemConstraintLayout.setBackgroundColor(holder.itemColorSpring)}
            "summer" -> {holder.itemSeasonImage.setImageResource(R.drawable.ic_summer);
                        holder.itemConstraintLayout.setBackgroundColor(holder.itemColorSummer)}
        }
    }

    override fun getItemCount(): Int {
        return year.size
    }
}