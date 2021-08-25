package com.neouto.munazam.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.neouto.munazam.data.dao.NoteDao
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.utils.Constants

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false,
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    // Singleton pattern
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }

}