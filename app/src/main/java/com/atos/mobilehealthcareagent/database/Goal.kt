package com.atos.mobilehealthcareagent.database

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_goal")
class Goal : BaseObservable() {
    @PrimaryKey(autoGenerate = true)
    var gid = 0

    @ColumnInfo(name = "steps")
    var steps = 0L

    @ColumnInfo(name = "calorie")
    var calorie = 0L

    @ColumnInfo(name = "distance")
    var distance = 0L

    @ColumnInfo(name = "heartpoint")
    var heartpoint = 0

    @ColumnInfo(name = "moveminute")
    var moveminute = 0L

}