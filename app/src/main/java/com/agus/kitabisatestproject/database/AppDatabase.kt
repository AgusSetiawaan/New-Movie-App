package com.agus.kitabisatestproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.agus.kitabisatestproject.model.WatchList
import com.agus.kitabisatestproject.util.Constant

@Database(entities = [WatchList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoWatchlist(): DaoWatchList

    companion object{
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        @JvmStatic
        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, Constant.DATABASE).build()

                //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
            }
            return INSTANCE!!
        }

        operator fun invoke(context: Context)= INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: getAppDatabase(context).also { INSTANCE = it}
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }

    }


}
