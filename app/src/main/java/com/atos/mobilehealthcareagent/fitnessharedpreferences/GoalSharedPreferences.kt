package com.atos.mobilehealthcareagent.fitnessharedpreferences

import android.content.Context

class GoalSharedPreferences {
    fun setGoal(step: String, context: Context) {

        val sharedPreference =
            context.getSharedPreferences("Goal_SharedPreferences", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("stepGoal", step)
        editor.commit()

    }

    fun getGoal(context: Context): String? {

        val sharedPreference =
            context.getSharedPreferences("Goal_SharedPreferences", Context.MODE_PRIVATE)
        var stepGoal = sharedPreference.getString("stepGoal", "")
        return stepGoal
    }


    fun removeGoal(context: Context){

        val sharedPreference =
            context.getSharedPreferences("Goal_SharedPreferences", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.clear()
        editor.remove("stepGoal")
        editor.commit()

    }
}