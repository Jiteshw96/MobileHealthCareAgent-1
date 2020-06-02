package com.atos.mobilehealthcareagent.googlefit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.ListenableWorker
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.UserFitnessData
import com.atos.mobilehealthcareagent.fitnessharedpreferences.LastSyncSharedPreferences
import com.atos.mobilehealthcareagent.fragments.SecondFragment
import com.atos.mobilehealthcareagent.googlefit.insertvalueintofitapi.InsertStepsFitApi
import com.atos.mobilehealthcareagent.googlefit.readfitnessapi.ReadFitDataApi
import com.atos.mobilehealthcareagent.service.ServiceInputToDB
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class BackgroundTask {

    var context:Context
    var NotificationChannelID="mobilehealthcareagent"
    var NotificationChannelName="mobilehealthcareagent"
    var NotificationContentTitle="Mobile HealthCare Agent"

   constructor(context:Context) {

        this.context = context
    }
    companion object {
        const val KEY_TASK_OUTPUT = "key_task_output"
    }

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_HYDRATION, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_NUTRITION, FitnessOptions.ACCESS_WRITE)
        .build()

    lateinit  var db: AppDatabase
    lateinit  var readFitDataApi: ReadFitDataApi
    lateinit var insertStepsFitApi: InsertStepsFitApi



   suspend fun getFitnessData(){

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var lastSyncDate =  LastSyncSharedPreferences().getLastSyncTime(context)
        var mStartDate = sdf.format(lastSyncDate).toString()
        var timeZone = TimeZone.getDefault()
        var currentTime = Calendar.getInstance(timeZone).getTime();
        var date = sdf.format(currentTime)
        var mSeconds = sdf.parse(date).time
        var mEndDate = sdf.format(mSeconds).toString()
        var mStartTimeInMili = sdf.parse(mStartDate)
        var mEndTimeInMili = sdf.parse(mEndDate)
        readFitDataApi = ReadFitDataApi(this.context,fitnessOptions)
        insertStepsFitApi= InsertStepsFitApi(this.context,fitnessOptions)
        db =  AppDatabase.getAppDatabase(this.context) as AppDatabase


       /* //Func for Insertion of steps
       CoroutineScope(Dispatchers.IO).launch {
           val task: Task<Void> =  insertStepsFitApi.insertData(64)

       }*/


        //Get Data With Time Interval
       /* CoroutineScope(Dispatchers.IO).launch {

            var user = UserFitnessData()
            user.firstName = "John"
            user.age = 22
            user.lastName = "wick"
            user.timestamp = mSeconds.toLong()

            withContext(Dispatchers.Default) {


          *//*  //Task Get Steps
            val taskGetSteps: Task<DataReadResponse> =  readFitDataApi.getStepsTimeInterval(
                GetDateDetailsStartEndTime.DateStartEnd(
                    mStartDate, mEndDate,
                    mStartTimeInMili.time, mEndTimeInMili.time
                )
            )

            taskGetSteps.addOnSuccessListener{
                CoroutineScope(Dispatchers.IO).launch {
                    var steps:Int =  DataParsing(taskGetSteps.result,com.google.android.gms.fitness.data.Field.FIELD_STEPS)
                    user.steps = steps.toLong()
                }

            }*//*

            //Task Get Calories
            val taskGetCalories:Task<DataReadResponse> =  readFitDataApi.getCalorieInTimeInterval(
                GetDateDetailsStartEndTime.DateStartEnd(
                    mStartDate, mEndDate,
                    mStartTimeInMili.time, mEndTimeInMili.time
                )
            )

            taskGetCalories.addOnSuccessListener{
                   var calories:Double = DataParsing(it,com.google.android.gms.fitness.data.Field.FIELD_CALORIES)
                    user.calorie = calories.toLong()


            }

         *//*   //Task Get HeartPoints
            val taskGetHeartPoints:Task<DataReadResponse> =  readFitDataApi.getHeartPointimeInterval(
                GetDateDetailsStartEndTime.DateStartEnd(
                    mStartDate, mEndDate,
                    mStartTimeInMili.time, mEndTimeInMili.time
                )
            )

            taskGetHeartPoints.addOnSuccessListener{
                CoroutineScope(Dispatchers.IO).launch {
                 var heartPoints:Double =  DataParsing(it,com.google.android.gms.fitness.data.Field.FIELD_INTENSITY)
                    user.heartpoint = heartPoints.toInt()
                }

            }*//*


         *//*   //Task Get Distance
            val taskGetDistanec:Task<DataReadResponse> =  readFitDataApi.getDistanceimeInterval(
                GetDateDetailsStartEndTime.DateStartEnd(
                    mStartDate, mEndDate,
                    mStartTimeInMili.time, mEndTimeInMili.time
                )
            )
            taskGetDistanec.addOnSuccessListener{
                CoroutineScope(Dispatchers.IO).launch {
                   var distance:Double = DataParsing(it,com.google.android.gms.fitness.data.Field.FIELD_DISTANCE)
                    user.distance = distance.toLong()
                }

            }*//*
          *//*  //Task Get Movement
            val taskGetMoveMinutes:Task<DataReadResponse> =  readFitDataApi.getMoveMinuteInterval(
                GetDateDetailsStartEndTime.DateStartEnd(
                    mStartDate, mEndDate,
                    mStartTimeInMili.time, mEndTimeInMili.time
                )
            )

            taskGetMoveMinutes.addOnSuccessListener{
                CoroutineScope(Dispatchers.IO).launch {
                    var moveMinutes:Double = DataParsing(it,com.google.android.gms.fitness.data.Field.FIELD_INTENSITY)
                    user.moveminute = moveMinutes.toLong()
                }

            }*//*

            }



            async {
              //  LastSyncSharedPreferences().setLastSyncTime(mSeconds,context)
                db.userDao()?.insertAllFitnessData(user)
                Log.i("Database Data" , db.userDao()?.allFitnessData?.size.toString())
                //  Log.i("Database Data Size" , db.userDao()?.fitnessData?.size.toString())
            }.await()

        }*/

        CoroutineScope(Dispatchers.IO).launch {
            readDataDaily()
            var user = UserFitnessData()
            user.firstName = "John"
            user.age = 22
            user.lastName = "wick"
            user.timestamp = mSeconds.toLong()

            withContext(Dispatchers.Default) {


                //Task Get Steps
                val taskGetSteps: Task<DataReadResponse> = readFitDataApi.getStepsTimeInterval(
                    GetDateDetailsStartEndTime.DateStartEnd(
                        mStartDate, mEndDate,
                        mStartTimeInMili.time, mEndTimeInMili.time
                    )
                )

                taskGetSteps.addOnSuccessListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        var steps = DataParsingSteps(
                            taskGetSteps.result,
                            com.google.android.gms.fitness.data.Field.FIELD_STEPS
                        )
                        user.steps = steps
                    }



                //Task Get Calories
                val taskGetCalories: Task<DataReadResponse> =
                    readFitDataApi.getCalorieInTimeInterval(
                        GetDateDetailsStartEndTime.DateStartEnd(
                            mStartDate, mEndDate,
                            mStartTimeInMili.time, mEndTimeInMili.time
                        )
                    )

                taskGetCalories.addOnSuccessListener {
                    var calories =
                        DataParsingCalorie(it, com.google.android.gms.fitness.data.Field.FIELD_CALORIES)
                    user.calorie = calories

                    //Task Get HeartPoints
                    val taskGetHeartPoints: Task<DataReadResponse> =
                        readFitDataApi.getHeartPointimeInterval(
                            GetDateDetailsStartEndTime.DateStartEnd(
                                mStartDate, mEndDate,
                                mStartTimeInMili.time, mEndTimeInMili.time
                            )
                        )

                    taskGetHeartPoints.addOnSuccessListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            var heartPoints = DataParsingHeartPoint(
                                it,
                                com.google.android.gms.fitness.data.Field.FIELD_INTENSITY
                            )
                            user.heartpoint = heartPoints
                            //Task Get Distance
                            val taskGetDistanec: Task<DataReadResponse> =
                                readFitDataApi.getDistanceimeInterval(
                                    GetDateDetailsStartEndTime.DateStartEnd(
                                        mStartDate, mEndDate,
                                        mStartTimeInMili.time, mEndTimeInMili.time
                                    )
                                )
                            taskGetDistanec.addOnSuccessListener {
                                CoroutineScope(Dispatchers.IO).launch {
                                    var distance = DataParsingDistance(
                                        it,
                                        com.google.android.gms.fitness.data.Field.FIELD_DISTANCE
                                    )
                                    user.distance = distance
                                    //Task Get Movement
                                    val taskGetMoveMinutes: Task<DataReadResponse> =
                                        readFitDataApi.getMoveMinuteInterval(
                                            GetDateDetailsStartEndTime.DateStartEnd(
                                                mStartDate, mEndDate,
                                                mStartTimeInMili.time, mEndTimeInMili.time
                                            )
                                        )

                                    taskGetMoveMinutes.addOnSuccessListener {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            var moveMinutes = DataParsingMoveMinute(
                                                it,
                                                com.google.android.gms.fitness.data.Field.FIELD_DURATION
                                            )
                                            user.moveminute = moveMinutes
                                            LastSyncSharedPreferences().setLastSyncTime(
                                                mSeconds,
                                                context
                                            )
                                          //  db.userDao()?.insertAllFitnessData(user)
                                            Log.i(
                                                "Database Data",
                                                db.userDao()?.allFitnessData?.size.toString()
                                            )
                                        }
                                    }
                                }
                            }

                        }

                    }

                }
            }

            }
        }
   }
    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(context.applicationContext, fitnessOptions)
    fun readDataDaily() {

        var userFitnessData=UserFitnessData()
        userFitnessData.firstName=db?.userDao()?.all?.get(0)?.firstName
        userFitnessData.lastName=db?.userDao()?.all?.get(0)?.lastName
        userFitnessData.age= db?.userDao()?.all?.get(0)?.age!!
        userFitnessData.fitness_id=db?.userDao()?.allFitnessData?.size!!+1
        userFitnessData.uid= db?.userDao()?.all?.get(0)?.uid!!

        Fitness.getHistoryClient(context.applicationContext, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { dataSet ->
                val total = when {
                    dataSet.isEmpty -> 0
                    else -> {
                       // steps = dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                        dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                    }

                }

                Log.i(ContentValues.TAG, " ------------------ ")
                Log.i("Success Value", "Total steps: ${total.toDouble()}")

                userFitnessData.steps=total.toDouble()
                    //////// Calorie
                Fitness.getHistoryClient(context.applicationContext, getGoogleAccount())
                    .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                    .addOnSuccessListener { dataSet ->
                        val total = when {
                            dataSet.isEmpty -> 0
                            else -> (dataSet.dataPoints.first().getValue(Field.FIELD_CALORIES).asFloat()).toInt()
                        }
                        Log.i("Success Value", "Total CALORIES: ${total.toDouble()}")
                        userFitnessData.calorie=total.toDouble()

                    //////////////////// moveminute

                        Fitness.getHistoryClient(context.applicationContext, getGoogleAccount())
                            .readDailyTotal(DataType.TYPE_MOVE_MINUTES)
                            .addOnSuccessListener { dataSet ->
                                val total = when {
                                    dataSet.isEmpty -> 0
                                    else -> dataSet.dataPoints.first().getValue(Field.FIELD_DURATION).asInt()
                                }
                                Log.i("Success Value", "Total MOVE MINUTES: ${total.toDouble()}")
                                userFitnessData.moveminute=total.toDouble()

                    /////////////////// Distance

                                Fitness.getHistoryClient(context.applicationContext, getGoogleAccount())
                                    .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                                    .addOnSuccessListener { dataSet ->
                                        val total = when {
                                            dataSet.isEmpty -> 0
                                            else -> dataSet.dataPoints.first().getValue(Field.FIELD_DISTANCE).asFloat()
                                        }
                                        Log.i("Success Value", "Total DISTANCE: ${((total).toString())}")

                                        userFitnessData.distance=((total).toString()).toDouble()

                    /////////////////// Heart Point

                                        Fitness.getHistoryClient(context.applicationContext, getGoogleAccount())
                                            .readDailyTotal(DataType.TYPE_HEART_POINTS)
                                            .addOnSuccessListener { dataSet ->
                                                val total = when {
                                                    dataSet.isEmpty -> 0
                                                    else -> dataSet.dataPoints.first().getValue(Field.FIELD_INTENSITY).asFloat()
                                                }
                                                Log.i("Success Value", "Heart: ${((total).toString()).toDouble()}")
                                                userFitnessData.heartpoint=((total).toString()).toDouble()


                                                val cal = Calendar.getInstance()
                                                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                                val s = SimpleDateFormat("yyyy-MM-dd")

                                                var date=s.format(Date(cal.timeInMillis))

                                                var mStartDate=date+" 00:00:01"
                                                var mStartTimeInMili=sdf.parse(mStartDate)

                                                userFitnessData.timestamp=mStartTimeInMili.time

                                                db.userDao()?.insertAllFitnessData(userFitnessData)

                                            }
                                            .addOnFailureListener { e ->
                                                Log.w(ContentValues.TAG, "There was a problem getting the DISTANCE.", e)
                                            }

                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(ContentValues.TAG, "There was a problem getting the DISTANCE.", e)
                                    }


                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "There was a problem getting the MOVE MINUTES count.", e)
                            }



                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "There was a problem getting the CALORIES count.", e)
                    }



            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "There was a problem getting the step count.", e)
            }


    }


    fun DataParsingSteps(dataReadResult: DataReadResponse?,fieldName: Field): Double{
        Log.i("Bucket",dataReadResult?.buckets.toString())
        val data = arrayListOf<String>()
        var totalData:Double = 0.0
        if(dataReadResult!!.buckets.isNotEmpty()){
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach {
                    if (it.dataPoints.size > 0){
                        data.add(it.dataPoints.get(0).getValue(fieldName).toString())
                    }
                }
            }
            for(element in data){
                Log.i("ArrayData",element.toString())
                totalData = totalData + (element.toString()).toDouble()
            }
            if(data.isNotEmpty()){
              //  displayNotification("Data", totalData.toString())
            }else{
                //displayNotification("Data", "No Record")
            }
        }
        return  totalData
    }

    fun DataParsingCalorie(dataReadResult: DataReadResponse?,fieldName: Field): Double{
        Log.i("Bucket",dataReadResult?.buckets.toString())
        val data = arrayListOf<String>()
        var totalData:Double = 0.0
        if(dataReadResult!!.buckets.isNotEmpty()){
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach {
                    if (it.dataPoints.size > 0){
                        data.add(it.dataPoints.get(0).getValue(fieldName).toString())
                    }
                }
            }
            for(element in data){
                Log.i("ArrayData",element.toString())
                totalData = totalData + (element.toString()).toDouble()
            }
            if(data.isNotEmpty()){
              //  displayNotification("Data", totalData.toString())
            }else{
                //displayNotification("Data", "No Record")
            }
        }
        return  totalData
    }

    fun DataParsingHeartPoint(dataReadResult: DataReadResponse?,fieldName: Field): Double{
        Log.i("Bucket",dataReadResult?.buckets.toString())
        val data = arrayListOf<String>()
        var totalData:Double = 0.0
        if(dataReadResult!!.buckets.isNotEmpty()){
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach {
                    if (it.dataPoints.size > 0){
                        data.add(it.dataPoints.get(0).getValue(fieldName).toString())
                    }
                }
            }
            for(element in data){
                Log.i("ArrayData",element.toString())
                totalData = totalData + (element.toString()).toDouble()
            }
            if(data.isNotEmpty()){
              //  displayNotification("Data", totalData.toString())
            }else{
               // displayNotification("Data", "No Record")
            }
        }
        return  totalData
    }
    fun DataParsingDistance(dataReadResult: DataReadResponse?,fieldName: Field): Double{
        Log.i("Bucket",dataReadResult?.buckets.toString())
        val data = arrayListOf<String>()
        var totalData:Double = 0.0
        if(dataReadResult!!.buckets.isNotEmpty()){
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach {
                    if (it.dataPoints.size > 0){
                        data.add(it.dataPoints.get(0).getValue(fieldName).toString())
                    }
                }
            }
            for(element in data){
                Log.i("ArrayData",element.toString())
                totalData = totalData + (element.toString()).toDouble()
            }
            if(data.isNotEmpty()){
               // displayNotification("Data", totalData.toString())
            }else{
              //  displayNotification("Data", "No Record")
            }
        }
        return  totalData
    }
    fun DataParsingMoveMinute(dataReadResult: DataReadResponse?,fieldName: Field): Double{
        Log.i("Bucket",dataReadResult?.buckets.toString())
        val data = arrayListOf<String>()
        var totalData:Double = 0.0
        if(dataReadResult!!.buckets.isNotEmpty()){
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach {
                    if (it.dataPoints.size > 0){
                        data.add(it.dataPoints.get(0).getValue(fieldName).toString())
                    }
                }
            }
            for(element in data){
                Log.i("ArrayData",element.toString())
                totalData = totalData + (element.toString()).toDouble()
            }
            if(data.isNotEmpty()){
               // displayNotification("Data", totalData.toString())
            }else{
                //displayNotification("Data", "No Record")
            }
        }
        return  totalData
    }

     fun displayNotification(task: String, desc: String?) {
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationChannelID,
                NotificationChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val builder =
            NotificationCompat.Builder(context, NotificationChannelID)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
        manager.notify(1, builder.build())
        Log.e("Time", Calendar.getInstance().getTime().toString())
    }
}
