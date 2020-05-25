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


}