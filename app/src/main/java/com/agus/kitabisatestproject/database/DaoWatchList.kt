package com.agus.kitabisatestproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.agus.kitabisatestproject.model.WatchList
import io.reactivex.Observable

@Dao
interface DaoWatchList {


    @Query("SELECT * FROM watchlist")
    fun getAllWatchList(): LiveData<List<WatchList>>

    @Query("SELECT * FROM watchlist where idBackend =:idBackend")
    fun getDetailsMovies(idBackend: Int): Observable<WatchList>

    @Query("SELECT * FROM watchlist where idBackend =:idBackend")
    fun getWatchMovie(idBackend: Int): WatchList

    @Insert
    fun insert(vararg watchlist: WatchList)

    @Insert
    fun insertAll(watchlistList: List<WatchList>)

    @Delete
    fun delete(watchlist: WatchList)

    @Query("DELETE FROM watchlist where 1=1")
    fun deleteAll()

    @Query("DELETE FROM watchlist where idBackend =:idBackend")
    fun deleteByIdBackend(idBackend: Int)

    @Query("SELECT COUNT(*) FROM watchlist")
    fun selectCount(): Observable<Int?>

}
