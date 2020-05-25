package com.atos.mobilehealthcareagent.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.DashBoard
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.contract.UserBasicDatabaseInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.databinding.FragmentRegistrationBasicInfoBinding
import com.atos.mobilehealthcareagent.presenter.PersonalDataPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 */
class RegistrationBasicInfo : Fragment(),
    UserBasicDatabaseInterface.UserBasicDataBaseViewInterface {

    lateinit var mPersonalDataPresenter: PersonalDataPresenter
    lateinit var db: AppDatabase
    var mUser: User = User()
    var userInformationSavedIntoDataBase = false
    var binding: FragmentRegistrationBasicInfoBinding? = null

    /**
     * Call after on attach method
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View =
            inflater.inflate(R.layout.fragment_registration_basic_info, container, false)

        binding = DataBindingUtil.bind(view)

        binding?.registrationBasicInfoFragment = this

        binding?.view = view

        binding?.user = mUser

        binding?.mainactivity = activity as DashBoard?

        binding?.userInformationSavedIntoDataBase = userInformationSavedIntoDataBase

        return view

    }

    /**
     * Call after activity created
     * @param savedInstanceState Bundle object containing the fragment's previously saved state. If the fragment has never existed before, the value of the Bundle object is null
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPersonalDataPresenter = PersonalDataPresenter(this)
        checkInfoBasicDataPresentOrNotIntoDB()
    }


    override fun initUserFitnessDatabase() {

        db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase

        Log.e("Database Created", "Ready to Read/Write")


    }


    /**
     * On click Button
     * @param view object of android.view.View
     */
    fun onClickSaveButton(view: View) {
        mPersonalDataPresenter.buttonClickAddBasecDataToDatabase(db, mUser)
        // Log.e("FirstName", edt_first_name.editText?.text.toString())


    }

    override suspend  fun userBasicInfoPresentIntoDB(flag: Boolean) {
        withContext(Dispatchers.Main){
            userInformationSavedIntoDataBase = flag
            binding?.userInformationSavedIntoDataBase = userInformationSavedIntoDataBase
            binding?.invalidateAll()
        }

    }

    fun checkInfoBasicDataPresentOrNotIntoDB() {
        mPersonalDataPresenter.getInfoBasicDataPresentOrNotIntoDB(db)
    }


}
