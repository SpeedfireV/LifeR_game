package com.example.lifergame.activities

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifergame.R
import com.example.lifergame.adapters.EventsAndSeasonsDatabaseHandler
import com.example.lifergame.adapters.RecyclerAdapterMain
import com.example.lifergame.models.SeasonsModelClass
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    val databaseHandler: EventsAndSeasonsDatabaseHandler = EventsAndSeasonsDatabaseHandler(this)
    val seasonList = mutableListOf<String>()
    val yearList = mutableListOf<Int>()
    val eventsList = mutableListOf<String>()


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNextSeason: Button = findViewById(R.id.b_next_season)
        buttonNextSeason.setOnClickListener {

            databaseHandler.newSeason(SeasonsModelClass(0, 0, "", "An event happened;And another event;Third event also happened"), false)
            seasonsAdapter()
        }


            // Bottom navigation
        val drawerLayout : DrawerLayout = findViewById(R.id.main_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)



        // Database Handler
        if (databaseHandler.getInfoAboutSeasons().size == 0) {
            databaseHandler.deleteLife()
            databaseHandler.newSeason(SeasonsModelClass(0, 1, databaseHandler.randomSeason(), "First year happened"), true)
        }


        // Energy Bar
        val energyBar : ProgressBar = findViewById(R.id.pb_energy)
        val currentEnergy = 10
        energyBar.max = 10

        ObjectAnimator.ofInt(energyBar, "progress", currentEnergy)
            .start()


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_family_tree -> Toast.makeText(applicationContext, "Clicked Family Tree", Toast.LENGTH_SHORT).show()
                R.id.nav_properties -> Toast.makeText(applicationContext, "Clicked Properties", Toast.LENGTH_SHORT).show()
                R.id.nav_restart -> {Toast.makeText(applicationContext, "Clicked Restart", Toast.LENGTH_SHORT).show()
                    databaseHandler.deleteLife()
                    databaseHandler.newSeason(SeasonsModelClass(0, 1, databaseHandler.randomSeason(), "First Year Happened"), true)
                    seasonsAdapter()}
            }

            true
        }


    }

    override fun onStart() {
        super.onStart()

        bottomNavigation(R.id.bottomNavigationView, R.id.fcv_bottom_navigation_fragment)

        seasonsAdapter()

    }

    fun bottomNavigation(navigationView: Int, bottomNav: Int) {

        val bottomNavigationView = findViewById<BottomNavigationView>(navigationView)
        val navController = findNavController(bottomNav)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun seasonsAdapter() {


        val infoAboutSeasons = databaseHandler.getInfoAboutSeasons()
        seasonList.clear()
        yearList.clear()
        for (i in infoAboutSeasons){
            seasonList.add(i.currentSeason)
            yearList.add(i.year)
            eventsList.add(i.events)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val chosenMenuItem = bottomNavigationView.selectedItemId

        if (chosenMenuItem == R.id.historyFragment) {
            val rvMainFragment = findViewById<RecyclerView>(R.id.rv_parent_seasons_events)
            rvMainFragment.adapter = RecyclerAdapterMain(yearList, seasonList, eventsList)
            rvMainFragment.layoutManager = LinearLayoutManager(this)} else {
                bottomNavigationView.selectedItemId = R.id.historyFragment
        }
    }

}