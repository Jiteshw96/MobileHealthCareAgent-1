package com.atos.mobilehealthcareagent.googlefit.readfitnessapi

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
//import com.atos.mobilehealthcareagent.TAG
import com.atos.mobilehealthcareagent.googlefit.GetDataByTime
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.atos.mobilehealthcareagent.googlefit.getEndTimeString
import com.atos.mobilehealthcareagent.googlefit.getStartTimeString
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.Task
import java.text.DateFormat


class ReadFitDataApi(cntx: Context, mFitnessOptions: FitnessOptions){

    lateinit var cntx: Context
    lateinit var mFitnessOptions: FitnessOptions
    private val dateFormat = DateFormat.getDateInstance()
    var steps = 0


    init {
        this.cntx=cntx
        this.mFitnessOptions=mFitnessOptions
    }

    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(cntx.applicationContext, mFitnessOptions)

    /** Data Read Functions begins ***/

     fun readDataDaily() {
        Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { dataSet ->
                val total = when {
                    dataSet.isEmpty -> 0
                    else -> {
                        steps = dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                        dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                    }
                }
                Log.i(TAG, " ------------------ ")
                Log.i(TAG, "Total steps: $total")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the step count.", e)
            }

        Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
            .addOnSuccessListener { dataSet ->
                val total = when {
                    dataSet.isEmpty -> 0
                    else -> (dataSet.dataPoints.first().getValue(Field.FIELD_CALORIES).asFloat()).toInt()
                }
                Log.i(TAG, "Total CALORIES: $total")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the CALORIES count.", e)
            }


        Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_MOVE_MINUTES)
            .addOnSuccessListener { dataSet ->
                val total = when {
                    dataSet.isEmpty -> 0
                    else -> dataSet.dataPoints.first().getValue(Field.FIELD_DURATION).asInt()
                }
                Log.i(TAG, "Total MOVE MINUTES: $total")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the MOVE MINUTES count.", e)
            }



        Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
            .addOnSuccessListener { dataSet ->
                val total = when {
                    dataSet.isEmpty -> 0
                    else -> dataSet.dataPoints.first().getValue(Field.FIELD_DISTANCE).asFloat()
                }
                Log.i(TAG, "Total DISTANCE: $total")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the DISTANCE.", e)
            }

    }

    /**
     * Asynchronous task to read the history data. When the task succeeds, it will print out the
     * data.
     */
     fun readHistoryDatateps(): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataSteps(dateFormat)

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.

            //   printData(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

     fun readHistoryHeartPoints(): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataHeartPoints(dateFormat)

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                printData(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }


     fun readHistoryWight(): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataWight(dateFormat)

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                printData(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

     fun readHistoryHyd(): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataHyd(dateFormat)

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                printData(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }


     fun readHistoryDataCalorie(): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataCalorie(dateFormat)

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                printData(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

    fun getSevenDaysHistoryDatateps(objectdaysTimeInMiliSecond: GetDateDetailsStartEndTime.DateStartEnd): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataStepsSevenDaysHistory(
                dateFormat,
                objectdaysTimeInMiliSecond.mStartTimeInMili,
                objectdaysTimeInMiliSecond.mEndTimeInMili
            )

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                Log.i(
                    objectdaysTimeInMiliSecond.mStartDate,
                    "---" + objectdaysTimeInMiliSecond.mStartDate + "---"
                )
                if (dataReadResponse.buckets.isNotEmpty()) {
                    Log.i(
                        TAG,
                        "Number of returned buckets of DataSets is: " + dataReadResponse.buckets.size
                    )
                    for (bucket in dataReadResponse.buckets) {
                        bucket.dataSets.forEach { dumpDataSet(it) }
                    }
                } else if (dataReadResponse.dataSets.isNotEmpty()) {
                    Log.i(TAG, "Number of returned DataSets is: " + dataReadResponse.dataSets.size)
                    dataReadResponse.dataSets.forEach { dumpDataSet(it) }
                }
                Log.i("-------------", "-------------------")

                //  printDataHistory(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }


    fun getStepsTimeInterval(objectdaysTimeInMiliSecond: GetDateDetailsStartEndTime.DateStartEnd): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataStepsTimeInterval(
                dateFormat,
                objectdaysTimeInMiliSecond.mStartTimeInMili,
                objectdaysTimeInMiliSecond.mEndTimeInMili
            )

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                Log.i(
                    objectdaysTimeInMiliSecond.mStartDate,
                    "---" + objectdaysTimeInMiliSecond.mStartDate + "---"
                )
                if (dataReadResponse.buckets.isNotEmpty()) {
                    Log.i(
                        TAG,
                        "Number of returned buckets of DataSets is: " + dataReadResponse.buckets.size
                    )
                    for (bucket in dataReadResponse.buckets) {
                        bucket.dataSets.forEach { dumpDataSet(it) }
                    }
                } else if (dataReadResponse.dataSets.isNotEmpty()) {
                    Log.i(TAG, "Number of returned DataSets is: " + dataReadResponse.dataSets.size)
                    dataReadResponse.dataSets.forEach { dumpDataSet(it) }
                }
                Log.i("-------------", "-------------------")

                //  printDataHistory(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }


     fun getCalorieInTimeInterval(objectdaysTimeInMiliSecond: GetDateDetailsStartEndTime.DateStartEnd): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataCalorieTimeInterval(
                dateFormat,
                objectdaysTimeInMiliSecond.mStartTimeInMili,
                objectdaysTimeInMiliSecond.mEndTimeInMili
            )

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                Log.i(
                    objectdaysTimeInMiliSecond.mStartDate,
                    "---" + objectdaysTimeInMiliSecond.mStartDate + "---"
                )
                if (dataReadResponse.buckets.isNotEmpty()) {
                    Log.i(
                        TAG,
                        "Number of returned buckets of DataSets is: " + dataReadResponse.buckets.size
                    )
                    for (bucket in dataReadResponse.buckets) {
                        bucket.dataSets.forEach { dumpDataSet(it) }
                    }
                } else if (dataReadResponse.dataSets.isNotEmpty()) {
                    Log.i(TAG, "Number of returned DataSets is: " + dataReadResponse.dataSets.size)
                    dataReadResponse.dataSets.forEach { dumpDataSet(it) }
                }
                Log.i("-------------", "-------------------")

                //  printDataHistory(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

     fun getDistanceimeInterval(objectdaysTimeInMiliSecond: GetDateDetailsStartEndTime.DateStartEnd): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataDistanceTimeInterval(
                dateFormat,
                objectdaysTimeInMiliSecond.mStartTimeInMili,
                objectdaysTimeInMiliSecond.mEndTimeInMili
            )

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                Log.i(
                    objectdaysTimeInMiliSecond.mStartDate,
                    "---" + objectdaysTimeInMiliSecond.mStartDate + "---"
                )
                if (dataReadResponse.buckets.isNotEmpty()) {
                    Log.i(
                        TAG,
                        "Number of returned buckets of DataSets is: " + dataReadResponse.buckets.size
                    )
                    for (bucket in dataReadResponse.buckets) {
                        bucket.dataSets.forEach { dumpDataSet(it) }
                    }
                } else if (dataReadResponse.dataSets.isNotEmpty()) {
                    Log.i(TAG, "Number of returned DataSets is: " + dataReadResponse.dataSets.size)
                    dataReadResponse.dataSets.forEach { dumpDataSet(it) }
                }
                Log.i("-------------", "-------------------")

                //  printDataHistory(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

     fun getHeartPointimeInterval(objectdaysTimeInMiliSecond: GetDateDetailsStartEndTime.DateStartEnd): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessDataHeartPointTimeInterval(
                dateFormat,
                objectdaysTimeInMiliSecond.mStartTimeInMili,
                objectdaysTimeInMiliSecond.mEndTimeInMili
            )

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                Log.i(
                    objectdaysTimeInMiliSecond.mStartDate,
                    "---" + objectdaysTimeInMiliSecond.mStartDate + "---"
                )
                if (dataReadResponse.buckets.isNotEmpty()) {
                    Log.i(
                        TAG,
                        "Number of returned buckets of DataSets is: " + dataReadResponse.buckets.size
                    )
                    for (bucket in dataReadResponse.buckets) {
                        bucket.dataSets.forEach { dumpDataSet(it) }
                    }
                } else if (dataReadResponse.dataSets.isNotEmpty()) {
                    Log.i(TAG, "Number of returned DataSets is: " + dataReadResponse.dataSets.size)
                    dataReadResponse.dataSets.forEach { dumpDataSet(it) }
                }
                Log.i("-------------", "-------------------")

                //  printDataHistory(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

     fun getMoveMinuteInterval(objectdaysTimeInMiliSecond: GetDateDetailsStartEndTime.DateStartEnd): Task<DataReadResponse> {
        // Begin by creating the query.
        val readRequest = GetDataByTime()
            .queryFitnessMoveMinutetPointTimeInterval(
                dateFormat,
                objectdaysTimeInMiliSecond.mStartTimeInMili,
                objectdaysTimeInMiliSecond.mEndTimeInMili
            )

        // Invoke the History API to fetch the data with the query
        return Fitness.getHistoryClient(cntx.applicationContext, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                // For the sake of the sample, we'll print the data so we can see what we just
                // added. In general, logging fitness information should be avoided for privacy
                // reasons.
                Log.i(
                    objectdaysTimeInMiliSecond.mStartDate,
                    "---" + objectdaysTimeInMiliSecond.mStartDate + "---"
                )
                if (dataReadResponse.buckets.isNotEmpty()) {
                    Log.i(
                        TAG,
                        "Number of returned buckets of DataSets is: " + dataReadResponse.buckets.size
                    )
                    for (bucket in dataReadResponse.buckets) {
                        bucket.dataSets.forEach { dumpDataSet(it) }
                    }
                } else if (dataReadResponse.dataSets.isNotEmpty()) {
                    Log.i(TAG, "Number of returned DataSets is: " + dataReadResponse.dataSets.size)
                    dataReadResponse.dataSets.forEach { dumpDataSet(it) }
                }
                Log.i("-------------", "-------------------")

                //  printDataHistory(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }


    /** Data Read Functions ends ***/

    /** Data printing Functions begins ***/
     fun printData(dataReadResult: DataReadResponse) {
        // [START parse_read_data_result]
        // If the DataReadRequest object specified aggregated data, dataReadResult will be returned
        // as buckets containing DataSets, instead of just DataSets.
        if (dataReadResult.buckets.isNotEmpty()) {
            Log.i(TAG, "Number of returned buckets of DataSets is: " + dataReadResult.buckets.size)
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach { dumpDataSet(it) }
            }
        } else if (dataReadResult.dataSets.isNotEmpty()) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.dataSets.size)
            dataReadResult.dataSets.forEach { dumpDataSet(it) }
        }
        Log.i("---------------------", "---------------------");
        // [END parse_read_data_result]
    }

    // [START parse_dataset]
     fun dumpDataSet(dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")

        for (dp in dataSet.dataPoints) {
            Log.i(TAG, "Data point:")
            Log.i(TAG, "\tType: ${dp.dataType.name}")
            Log.i(TAG, "\tStart: ${dp.getStartTimeString()}")
            Log.i(TAG, "\tEnd: ${dp.getEndTimeString()}")
            dp.dataType.fields.forEach {
                Log.i(TAG, "\tField: ${it.name} Value: ${dp.getValue(it)}")
            }
        }
    }
     fun printDataHistory(dataReadResult: DataReadResponse) {
        // [START parse_read_data_result]
        // If the DataReadRequest object specified aggregated data, dataReadResult will be returned
        // as buckets containing DataSets, instead of just DataSets.
        if (dataReadResult.buckets.isNotEmpty()) {
            Log.i(TAG, "Number of returned buckets of DataSets is: " + dataReadResult.buckets.size)
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach { dumpDataSet(it) }
            }
        } else if (dataReadResult.dataSets.isNotEmpty()) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.dataSets.size)
            dataReadResult.dataSets.forEach { dumpDataSet(it) }
        }
        // [END parse_read_data_result]
    }
    // [END parse_dataset]
    /** Data printing Functions ends ***/
}