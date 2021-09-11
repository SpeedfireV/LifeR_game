package com.example.lifergame

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsFragment : Fragment() {

    val seasonList = mutableListOf<String>()
    val yearList = mutableListOf<Int>()
    val eventsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pol = view.findViewById<RecyclerView>(R.id.rv_list_of_events)

        val rvMainFragment = view.findViewById<RecyclerView>(R.id.rv_seasons_events)
        val databaseHandler = EventsAndSeasonsDatabaseHandler(view.context)

        val infoAboutSeasons = databaseHandler.getInfoAboutSeasons()
        seasonList.clear()
        yearList.clear()
        for (i in infoAboutSeasons){
            seasonList.add(i.currentSeason)
            yearList.add(i.year)
            eventsList.add(i.events)
        }
        rvMainFragment.layoutManager = LinearLayoutManager(activity)
        rvMainFragment.adapter = RecyclerAdapterMain(yearList, seasonList)

        pol.layoutManager = LinearLayoutManager(activity)
        pol.adapter = RecyclerAdapterEvents(mutableListOf("EVENT HAPPENED"))


    }
}