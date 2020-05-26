package com.atos.mobilehealthcareagent.database

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_basic_info")
class User : BaseObservable() {
    @PrimaryKey(autoGenerate = false)
    var uid = 0

    @Bindable
    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @Bindable
    @ColumnInfo(name = "last_name")
    var lastName: String? = null

    @Bindable
    @ColumnInfo(name = "age")
    var age = 0

    @Bindable
    @ColumnInfo(name = "weight")
    var weight = 0

    @Bindable
    @ColumnInfo(name = "height")
    var height = 0


    @ColumnInfo(name = "goal_steps")
    var goal_steps = 7000L

    @ColumnInfo(name = "goal_calorie")
    var goal_calorie = 5600L

    @ColumnInfo(name = "goal_distance")
    var goal_distance = 5600L

    @ColumnInfo(name = "goal_heartpoint")
    var goal_heartpoint = 25

    @ColumnInfo(name = "goal_moveminute")
    var goal_moveminute = 140L


}