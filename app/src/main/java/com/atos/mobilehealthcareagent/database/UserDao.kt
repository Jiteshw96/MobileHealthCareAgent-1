package com.atos.mobilehealthcareagent.database

import androidx.room.*

@Dao
interface UserDao {
    @get:Query("SELECT * FROM user_basic_info")
    val all: List<User?>?

    @Query("SELECT * FROM user_basic_info where first_name LIKE  :firstName AND last_name LIKE :lastName")
    fun findByName(
        firstName: String?,
        lastName: String?
    ): User?

    @Query("SELECT COUNT(*) from user_basic_info")
    fun countUsers(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User?): LongArray?


    @Delete
    fun delete(user: User?)




    @Insert
    fun insertAllFitnessData(vararg mUsersFitnessData: UserFitnessData): LongArray?

    @get:Query("SELECT * FROM user_fitness_data")
    val allFitnessData: List<UserFitnessData?>?

    //SELECT  sum(steps) as value from Datae WHERE Datae.Date BETWEEN 1590060600000 and 1590066388079
    @Query("SELECT COALESCE(SUM(steps),0) FROM user_fitness_data where timestamp between  :startDate AND :endDate")
    fun getStepCount(
        startDate: Long?,
        endDate: Long?
    ): Double?


    //SELECT  sum(Cal) as value from Datae WHERE Datae.Date BETWEEN 1590060600000 and 1590066388079
    @Query("SELECT COALESCE(SUM(calorie),0) FROM user_fitness_data where timestamp between  :startDate AND :endDate")
    fun getCalorieCount(
        startDate: Long?,
        endDate: Long?
    ): Double?


    //SELECT  sum(distance) as value from Datae WHERE Datae.Date BETWEEN 1590060600000 and 1590066388079
    @Query("SELECT COALESCE(SUM(distance),0) FROM user_fitness_data where timestamp between  :startDate AND :endDate")
    fun getDistanceCount(
        startDate: Long?,
        endDate: Long?
    ): Double?


    //SELECT  sum(heartpoint) as value from Datae WHERE Datae.Date BETWEEN 1590060600000 and 1590066388079
    @Query("SELECT COALESCE(SUM(heartpoint),0) FROM user_fitness_data where timestamp between  :startDate AND :endDate")
    fun getHeartPointCount(
        startDate: Long?,
        endDate: Long?
    ): Double?


    //SELECT  sum(moveminute) as value from Datae WHERE Datae.Date BETWEEN 1590060600000 and 1590066388079
    @Query("SELECT COALESCE(SUM(moveminute),0) FROM user_fitness_data where timestamp between  :startDate AND :endDate")
    fun getMoveMinuteount(
        startDate: Long?,
        endDate: Long?
    ): Double?



    @Insert
    fun insertAllGoal(vararg mGoalData: Goal): LongArray?

    @get:Query("SELECT * FROM user_goal")
    val allGoal: List<Goal?>?



}