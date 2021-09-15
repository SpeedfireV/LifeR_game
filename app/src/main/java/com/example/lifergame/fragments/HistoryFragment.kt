package com.example.lifergame.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifergame.adapters.EventsAndSeasonsDatabaseHandler
import com.example.lifergame.R
import com.example.lifergame.adapters.RecyclerAdapterMain

class HistoryFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHandler = EventsAndSeasonsDatabaseHandler(view.context)

        val rvMainFragment = view.findViewById<RecyclerView>(R.id.rv_parent_seasons_events)

        val infoAboutSeasons = databaseHandler.getInfoAboutSeasons()
        seasonList.clear()
        yearList.clear()
        for (i in infoAboutSeasons){
            seasonList.add(i.currentSeason)
            yearList.add(i.year)
            eventsList.add(i.events)
        }
        rvMainFragment.layoutManager = LinearLayoutManager(activity)
        rvMainFragment.adapter = RecyclerAdapterMain(yearList, seasonList, eventsList)


    }

    private fun seasonsAdapter(view: View, databaseHandler: EventsAndSeasonsDatabaseHandler, fragmentResumed: Boolean) {

        val infoAboutSeasons = databaseHandler.getInfoAboutSeasons()
        seasonList.clear()
        yearList.clear()
        for (i in infoAboutSeasons){
            seasonList.add(i.currentSeason)
            yearList.add(i.year)
            eventsList.add(i.events)
        }

        if (fragmentResumed == true){
            val rvMainFragment = view.findViewById<RecyclerView>(R.id.rv_parent_seasons_events)

            rvMainFragment.layoutManager = LinearLayoutManager(view.context)
            rvMainFragment.adapter = RecyclerAdapterMain(yearList, seasonList, eventsList)
        }
    }

}