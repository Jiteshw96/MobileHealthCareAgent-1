package com.atos.mobilehealthcareagent.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_fitness_data",primaryKeys = arrayOf("uid", "timestamp"))
class UserFitnessData {

    @ColumnInfo(name = "fitness_id")
    var fitness_id = 0



    var uid = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null

    @ColumnInfo(name = "age")
    var age = 0


    var timestamp = 0L

    @ColumnInfo(name = "steps")
    var steps = 0.0

    @ColumnInfo(name = "calorie")
    var calorie = 0.0

    @ColumnInfo(name = "distance")
    var distance = 0.0

    @ColumnInfo(name = "heartpoint")
    var heartpoint = 0.0

    @ColumnInfo(name = "moveminute")
    var moveminute = 0.0

}