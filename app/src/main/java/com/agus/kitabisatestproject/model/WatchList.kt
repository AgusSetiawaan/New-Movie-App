package com.agus.kitabisatestproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "watchlist")
data class WatchList (

    @ColumnInfo(name = "idBackend")
    var idBackend: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "release_date")
    var releaseDate: String = ""
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null


}

