package com.atos.mobilehealthcareagent.fitnessharedpreferences

import android.content.Context

class LastSyncSharedPreferences {
    fun setLastSyncTime(lastSyncTime: Long, context: Context) {

        val sharedPreference =
            context.getSharedPreferences("Last_Sync_SharedPreferences", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putLong("lastSyncTime", lastSyncTime)
        editor.commit()

    }

    fun getLastSyncTime(context: Context): Long? {

        val sharedPreference =
            context.getSharedPreferences("Last_Sync_SharedPreferences", Context.MODE_PRIVATE)
        var time = sharedPreference.getLong("lastSyncTime", 0L)
        return time
    }


    fun removeLastSyncTime(context: Context){

        val sharedPreference =
            context.getSharedPreferences("Last_Sync_SharedPreferences", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.clear()
        editor.remove("lastSyncTime")

    }
}