package com.example.lifergame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class EventsAndSeasonsDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EventsDatabase"
        private const val TABLE_EVENTS = "EventsTable"

        private const val KEY_ID = "_id"
        private const val KEY_SEASONS = "seasons"
        private const val KEY_YEAR = "year"
        private const val KEY_EVENTS = "events"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EVENTS_TABLE = ("CREATE TABLE " + TABLE_EVENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SEASONS + " TEXT," + KEY_EVENTS + " TEXT," + KEY_YEAR + " TEXT" + ")")
        db?.execSQL(CREATE_EVENTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_EVENTS)
        onCreate(db)
    }

    fun newSeason(info: SeasonsModelClass, firstYear: Boolean) : Long {
        val db = this.writableDatabase
        val dbReadable = getInfoAboutSeasons()
        val dbReadableSize = dbReadable.size

        val contentValues = ContentValues()
        if (firstYear == true) {
            contentValues.put(KEY_SEASONS, info.currentSeason)
            contentValues.put(KEY_YEAR, info.year)
            contentValues.put(KEY_EVENTS, info.events)}
        else {
            when (dbReadable[dbReadableSize - 1].currentSeason) {
                "Winter" -> contentValues.put(KEY_SEASONS, "Spring")
                "Spring" -> contentValues.put(KEY_SEASONS, "Summer")
                "Summer" -> contentValues.put(KEY_SEASONS, "Autumn")
                "Autumn" -> contentValues.put(KEY_SEASONS, "Winter")
            }
            when (dbReadableSize % 4) {
                0 -> contentValues.put(KEY_YEAR, dbReadable[dbReadableSize - 1].year + 1)
                else -> contentValues.put(KEY_YEAR, dbReadable[dbReadableSize - 1].year)
            }
            contentValues.put(KEY_EVENTS, info.events)}

        val success = db.insert(TABLE_EVENTS, null, contentValues)

        db.close()
        return success
    }

    fun getInfoAboutSeasons(): ArrayList<SeasonsModelClass> {

        val infoList: ArrayList<SeasonsModelClass> = ArrayList<SeasonsModelClass>()

        val selectQuery = "SELECT  * FROM ${TABLE_EVENTS}"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var currentSeason: String
        var year: Int
        var events: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                year = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_YEAR))
                currentSeason = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SEASONS))
                events = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENTS))

                val data = SeasonsModelClass(id = id, currentSeason = currentSeason, year = year, events = events)
                infoList.add(data)
            } while (cursor.moveToNext())
        }
        return infoList
    }

    fun UpdateEvents(event: SeasonsModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EVENTS, event.events)

        val success = db.update(TABLE_EVENTS, contentValues, KEY_ID + "=" + event.id, null)
        return success
    }

    fun deleteLife() : Unit {
        val db = this.writableDatabase
        val success = db.execSQL("delete from " + TABLE_EVENTS);
        return success
    }


    fun randomSeason() : String {
        val seasonsList = listOf<String>("Winter", "Autumn", "Spring", "Summer")
        return seasonsList.random()
    }




}