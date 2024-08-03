package com.example.projectandroid2.placedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectandroid2.model.Place

@Database(entities = [Place::class], version = 1, exportSchema = false)
abstract class PlacesDatabaseTesting : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}