package com.agus.kitabisatestproject

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.agus.kitabisatestproject.database.AppDatabase
import com.agus.kitabisatestproject.database.DaoWatchList
import com.agus.kitabisatestproject.model.WatchList
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class DbReadWriteTest{

    private lateinit var daoWatchList: DaoWatchList
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        daoWatchList = appDatabase.daoWatchlist()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadList() {
        val watchList = WatchList(9999,"title coba", "ini adalah coba coba","ini posterpath","31 MAret 2020")
        daoWatchList.insert(watchList)

        var watchItem = daoWatchList.getWatchMovie(9999)

        assertEquals(watchList, watchItem)
    }

}