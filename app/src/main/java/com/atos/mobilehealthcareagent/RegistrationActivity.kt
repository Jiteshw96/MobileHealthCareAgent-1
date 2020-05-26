package com.atos.mobilehealthcareagent

import android.Manifest
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.atos.mobilehealthcareagent.fitnessharedpreferences.LastSyncSharedPreferences
import com.atos.mobilehealthcareagent.fragments.DatePickerFragment
import com.atos.mobilehealthcareagent.googlefit.BackgroundTask
import com.atos.mobilehealthcareagent.googlefit.readfitnessapi.ReadFitDataApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "StepCounter"

enum class TypeOfData{
    Steps,Calorie,Heart
}


enum class FitActionRequestCode {
    SUBSCRIBE,
    READ_DATA
}
class RegistrationActivity : AppCompatActivity(), OnDateSetListener{

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_HYDRATION, FitnessOptions.ACCESS_WRITE)
        .addDataType(DataType.TYPE_NUTRITION, FitnessOptions.ACCESS_WRITE)
        .build()

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var timeZone = TimeZone.getDefault()
    var currentTime = Calendar.getInstance(timeZone).getTime();
    var date = sdf.format(currentTime)
    var mseconds = sdf.parse(date).time
   lateinit var task:BackgroundTask



    private val runningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    private val dateFormat = DateFormat.getDateInstance()
    var steps = 0
    private val readDataFitApi = ReadFitDataApi(this,fitnessOptions)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val dob = findViewById<EditText>(R.id.dob)
        val spinner = findViewById<Spinner>(R.id.gender_spinner)
       // val  dataAdapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.gender,R.layout.custom_spinner)
        val dataAdapter =
            ArrayAdapter.createFromResource(this, R.array.gender, R.layout.custom_spinner)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
        spinner.setSelection(dataAdapter.count - 1)

        dob.setOnClickListener {
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")
        }

        val done_btn = findViewById<Button>(R.id.done_btn)
        done_btn.setOnClickListener {
          getPermission()
        }
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = day

        val dob = DateFormat.getDateInstance().format(c.time)
        val dobText = findViewById<View>(R.id.dob) as EditText
        dobText.setText(dob)
    }
    private fun checkPermissionsAndRun(fitActionRequestCode: FitActionRequestCode) {
        if (permissionApproved()) {
            fitSignIn(fitActionRequestCode)
        } else {
            requestRuntimePermissions(fitActionRequestCode)
        }
    }


    private fun getPermission(){

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(this, permissions, 0)
        // initializeLogging()
        checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)

        var lastSyncTime =  LastSyncSharedPreferences().getLastSyncTime(applicationContext)
        if(lastSyncTime?.toInt() == 0 ){
            LastSyncSharedPreferences().setLastSyncTime(mseconds,applicationContext)
        }

    }

    private fun permissionApproved(): Boolean {
        val approved = if (runningQOrLater) {
            PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        } else {
            true
        }
        return approved
    }

    /**
     * Checks that the user is signed in, and if so, executes the specified function. If the user is
     * not signed in, initiates the sign in flow, specifying the post-sign in function to execute.
     *
     * @param requestCode The request code corresponding to the action to perform after sign in.
     */
    private fun fitSignIn(requestCode: FitActionRequestCode) {
        if (oAuthPermissionsApproved()) {
            performActionForRequestCode(requestCode)
        } else {
            requestCode.let {
                GoogleSignIn.requestPermissions(
                    this,
                    requestCode.ordinal,
                    getGoogleAccount(), fitnessOptions
                )
            }
        }
    }


    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(getGoogleAccount(), fitnessOptions)

    /**
     * Gets a Google account for use in creating the Fitness client. This is achieved by either
     * using the last signed-in account, or if necessary, prompting the user to sign in.
     * `getAccountForExtension` is recommended over `getLastSignedInAccount` as the latter can
     * return `null` if there has been no sign in before.
     */
    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(this, fitnessOptions)


    private fun requestRuntimePermissions(requestCode: FitActionRequestCode) {
        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        requestCode.let {
            if (shouldProvideRationale) {
                Log.i(TAG, "Displaying permission rationale to provide additional context.")

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    requestCode.ordinal
                )

            } else {
                Log.i(TAG, "Requesting permission")
                // Request permission. It's possible this can be auto answered if device policy
                // sets the permission in a given state or the user denied the permission
                // previously and checked "Never ask again".
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    requestCode.ordinal
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> {
                val postSignInAction = FitActionRequestCode.values()[requestCode]
                postSignInAction.let {
                    performActionForRequestCode(postSignInAction)
                }
            }
            else -> oAuthErrorMsg(requestCode, resultCode)
        }
    }
    /**
     * Runs the desired method, based on the specified request code. The request code is typically
     * passed to the Fit sign-in flow, and returned with the success callback. This allows the
     * caller to specify which method, post-sign-in, should be called.
     *
     * @param requestCode The code corresponding to the action to perform.
     */
    private fun performActionForRequestCode(requestCode: FitActionRequestCode) =
        when (requestCode) {
            FitActionRequestCode.READ_DATA -> readDataFitApi.readDataDaily()
            FitActionRequestCode.SUBSCRIBE -> subscribe()
        }

    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
        val message = """
            There was an error signing into Fit. Check the troubleshooting section of the README
            for potential issues.
            Request code was: $requestCode
            Result code was: $resultCode
        """.trimIndent()
        Log.e(TAG, message)
    }

    /** Records step data by requesting a subscription to background step data.  */
    private fun subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed!")
                } else {
                    Log.w(TAG, "There was a problem subscribing.", task.exception)
                }
            }

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_CALORIES_EXPENDED)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_CALORIES_EXPENDED")
                } else {
                    Log.w(
                        TAG,
                        "There was a problem subscribing. TYPE_CALORIES_EXPENDED",
                        task.exception
                    )
                }
            }

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_HEART_POINTS)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_HEART_CUMULATIVE")
                } else {
                    Log.w(
                        TAG,
                        "There was a problem subscribing. TYPE_HEART_CUMULATIVE",
                        task.exception
                    )
                }
            }

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_DISTANCE_CUMULATIVE)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_DISTANCE_CUMULATIVE")
                } else {
                    Log.w(
                        TAG,
                        "There was a problem subscribing. TYPE_DISTANCE_CUMULATIVE",
                        task.exception
                    )
                }
            }

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_WEIGHT)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_WEIGHT")
                } else {
                    Log.w(TAG, "There was a problem subscribing. TYPE_WEIGHT", task.exception)
                }
            }

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_HEIGHT)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_HEIGHT")
                } else {
                    Log.w(TAG, "There was a problem subscribing. TYPE_HEIGHT", task.exception)
                }
            }

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_HYDRATION)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_HYDRATION")
                } else {
                    Log.w(TAG, "There was a problem subscribing. TYPE_HYDRATION", task.exception)
                }
            }
        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_NUTRITION)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed! TYPE_NUTRITION")
                } else {
                    Log.w(TAG, "There was a problem subscribing. TYPE_NUTRITION", task.exception)
                }
            }

        Fitness.getRecordingClient(
            this,
            GoogleSignIn.getLastSignedInAccount(this)!!
        )
            .subscribe(DataType.TYPE_ACTIVITY_SAMPLES)
            .addOnSuccessListener { Log.i(TAG, "Successfully subscribed! TYPE_ACTIVITY_SAMPLES") }
            .addOnFailureListener {
                Log.i(
                    TAG,
                    "There was a problem subscribing. TYPE_ACTIVITY_SAMPLES"
                )
            }
    }
    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when {
            grantResults.isEmpty() -> {
                // If user interaction was interrupted, the permission request
                // is cancelled and you receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            }
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                // Permission was granted.
                val fitActionRequestCode = FitActionRequestCode.values()[requestCode]
                fitActionRequestCode.let {
                    fitSignIn(fitActionRequestCode)
                }
            }
            else -> {
                // Permission denied.

                // In this Activity we've chosen to notify the user that they
                // have rejected a core permission for the app since it makes the Activity useless.
                // We're communicating this message in a Snackbar since this is a sample app, but
                // core permissions would typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.

                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts(
                    "package",
                    BuildConfig.APPLICATION_ID, null
                )
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }
        }
    }

  
}
