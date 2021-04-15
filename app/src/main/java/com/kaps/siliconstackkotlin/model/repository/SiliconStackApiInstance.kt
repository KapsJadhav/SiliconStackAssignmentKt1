package com.kaps.siliconstackkotlin.model.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.kaps.siliconstackkotlin.model.Callback.AddUpdateNoteCallback
import com.kaps.siliconstackkotlin.model.Callback.NoteCallback
import com.kaps.siliconstackkotlin.model.Note
import com.kaps.siliconstackkotlin.model.helper.AppConstants
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SiliconStackApiInstance {

    var retrofit: Retrofit? = null
    var siliconStackApiInstance: SiliconStackApiInstance? = null
    lateinit var siliconStackApiService: SiliconStackApiService
    val TAG = "SiliconStackApiInstance"

    fun siliconStackApiInstance(): SiliconStackApiInstance? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(
                    OkHttpClient.Builder()
                        .addNetworkInterceptor { chain ->
                            val builder = chain.request().newBuilder()
                            builder.addHeader("Accept-Language", "en")
                            val request = builder.build()
                            chain.proceed(request)
                        }
                        .connectTimeout(180, TimeUnit.SECONDS)
                        .writeTimeout(180, TimeUnit.SECONDS)
                        .readTimeout(180, TimeUnit.SECONDS).build()
                )
                .baseUrl(AppConstants.ApiNames.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            siliconStackApiService = retrofit!!.create(SiliconStackApiService::class.java)
            AppConstants.PrintLog(TAG,""+siliconStackApiService)
        }
        return siliconStackApiInstance
    }

    @get:Synchronized
    val instance: SiliconStackApiInstance?
        get() {
            if (siliconStackApiInstance == null) {
                if (siliconStackApiInstance == null) {
                    siliconStackApiInstance = siliconStackApiInstance()
                }
            }
            return siliconStackApiInstance
        }

    fun getAllNotes(noteCallback: NoteCallback, context: Context) {
        siliconStackApiInstance()
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context)
        }
        val loginRegisterResponseMutableLiveData: MutableLiveData<List<Note?>?> =
            MutableLiveData<List<Note?>?>()

        siliconStackApiService.getAllNotes()
            ?.enqueue(object : Callback<List<Note?>?> {
                override fun onResponse(
                    call: Call<List<Note?>?>,
                    response: retrofit2.Response<List<Note?>?>
                ) {
                    AppConstants.closeProgressDialog()
                    if (response != null && response.body() != null) {
                        loginRegisterResponseMutableLiveData.setValue(response.body())
                        if (response.body() != null) {
                            noteCallback!!.onSuccess(loginRegisterResponseMutableLiveData!!.value as List<Note>?)
                        } else {
                            noteCallback!!.onError("No Task Found...")
                        }
                    } else {
                        AppConstants.showToastMessage(context, "Something went wrong... try again")
                    }
                }

                override fun onFailure(
                    call: Call<List<Note?>?>,
                    t: Throwable
                ) {
                    AppConstants.closeProgressDialog()
                    noteCallback.onError(t.message)
                    AppConstants.PrintLog(TAG, "Get All Task Response Error -  " + t.message)
                    loginRegisterResponseMutableLiveData.setValue(null)
                }
            })
    }


    fun addNote(
        sUserId: String,
        sTitle: String,
        sBody: String,
        sNote: String,
        sStatus: String,
        addUpdateNoteCallback: AddUpdateNoteCallback,
        context: Context
    ) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context)
        }
        AppConstants.PrintLog(TAG, "UserId : $sUserId")
        AppConstants.PrintLog(TAG, "Title : $sTitle")
        AppConstants.PrintLog(TAG, "Body : $sBody")
        AppConstants.PrintLog(TAG, "Note : $sNote")
        AppConstants.PrintLog(TAG, "Status : $sStatus")
        val userSignUp: MutableLiveData<Note?> = MutableLiveData<Note?>()
        siliconStackApiService!!.addNote(sUserId, sTitle, sBody, sNote, sStatus)
            ?.enqueue(object : Callback<Note?> {
                override fun onResponse(
                    call: Call<Note?>,
                    response: retrofit2.Response<Note?>
                ) {
                    AppConstants.closeProgressDialog()
                    if (response != null && response.body() != null) {
                        var jsonObjectResponse: JSONObject? = null
                        try {
                            jsonObjectResponse = JSONObject(Gson().toJson(response.body()))
                            AppConstants.PrintLog(
                                TAG,
                                "Add Task Response - $jsonObjectResponse"
                            )
                        } catch (e: JSONException) {
                            AppConstants.PrintLog(
                                TAG,
                                "Add Task Response JSON Error - " + e.message
                            )
                            e.printStackTrace()
                        }
                        userSignUp.setValue(response.body())
                        if (response.body() != null) {
                            addUpdateNoteCallback.onSuccessAddUpdateNote(userSignUp.getValue())
                        } else {
                            addUpdateNoteCallback.onError("Error")
                        }
                    } else {
                        AppConstants.showToastMessage(context, "Something went wrong... try again")
                    }
                }

                override fun onFailure(call: Call<Note?>, t: Throwable) {
                    AppConstants.closeProgressDialog()
                    addUpdateNoteCallback.onError(t.message)
                    AppConstants.PrintLog(TAG, "Add Task Response Error -  " + t.message)
                    userSignUp.setValue(null)
                }
            })
    }


    fun updateNote(
        sId: String,
        sUserId: String,
        sTitle: String,
        sBody: String,
        sNote: String,
        sStatus: String,
        addUpdateNoteCallback: AddUpdateNoteCallback,
        context: Context
    ) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context)
        }
        AppConstants.PrintLog(TAG, "Id : $sId")
        AppConstants.PrintLog(TAG, "UserId : $sUserId")
        AppConstants.PrintLog(TAG, "Title : $sTitle")
        AppConstants.PrintLog(TAG, "Body : $sBody")
        AppConstants.PrintLog(TAG, "Note : $sNote")
        AppConstants.PrintLog(TAG, "Status : $sStatus")
        val userSignUp: MutableLiveData<Note?> = MutableLiveData<Note?>()
        siliconStackApiService!!.updateNote(sId, sUserId, sTitle, sBody, sNote, sStatus)
            ?.enqueue(object : Callback<Note?> {
                override fun onResponse(
                    call: Call<Note?>,
                    response: retrofit2.Response<Note?>
                ) {
                    AppConstants.closeProgressDialog()
                    if (response != null && response.body() != null) {
                        var jsonObjectResponse: JSONObject? = null
                        try {
                            jsonObjectResponse = JSONObject(Gson().toJson(response.body()))
                            AppConstants.PrintLog(
                                TAG,
                                "Update Task Response - $jsonObjectResponse"
                            )
                        } catch (e: JSONException) {
                            AppConstants.PrintLog(
                                TAG,
                                "Update Task Response JSON Error - " + e.message
                            )
                            e.printStackTrace()
                        }
                        userSignUp.setValue(response.body())
                        if (response.body() != null) {
                            addUpdateNoteCallback.onSuccessAddUpdateNote(userSignUp.getValue())
                        } else {
                            addUpdateNoteCallback.onError("Error")
                        }
                    } else {
                        AppConstants.showToastMessage(context, "Something went wrong... try again")
                    }
                }

                override fun onFailure(call: Call<Note?>, t: Throwable) {
                    AppConstants.closeProgressDialog()
                    addUpdateNoteCallback.onError(t.message)
                    AppConstants.PrintLog(TAG, "Update Task Response Error -  " + t.message)
                    userSignUp.setValue(null)
                }
            })
    }


    fun deleteNote(
        sId: String,
        sUserId: String,
        addUpdateNoteCallback: AddUpdateNoteCallback,
        context: Context
    ) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context)
        }
        AppConstants.PrintLog(TAG, "Id : $sId")
        AppConstants.PrintLog(TAG, "UserId : $sUserId")
        val userSignUp: MutableLiveData<Note?> = MutableLiveData<Note?>()
        siliconStackApiService!!.deleteNote(sId, sUserId)
            ?.enqueue(object : Callback<Note?> {
                override fun onResponse(
                    call: Call<Note?>,
                    response: retrofit2.Response<Note?>
                ) {
                    AppConstants.closeProgressDialog()
                    if (response != null && response.body() != null) {
                        var jsonObjectResponse: JSONObject? = null
                        try {
                            jsonObjectResponse = JSONObject(Gson().toJson(response.body()))
                            AppConstants.PrintLog(
                                TAG,
                                "Delete Task Response - $jsonObjectResponse"
                            )
                        } catch (e: JSONException) {
                            AppConstants.PrintLog(
                                TAG,
                                "Delete Task Response JSON Error - " + e.message
                            )
                            e.printStackTrace()
                        }
                        userSignUp.setValue(response.body())
                        if (response.body() != null) {
                            addUpdateNoteCallback.onSuccessAddUpdateNote(userSignUp.getValue())
                        } else {
                            addUpdateNoteCallback.onError("Error")
                        }
                    } else {
                        AppConstants!!.showToastMessage(
                            context,
                            "Something went wrong... try again"
                        )
                    }
                }

                override fun onFailure(call: Call<Note?>, t: Throwable) {
                    AppConstants!!.closeProgressDialog()
                    addUpdateNoteCallback.onError(t.message)
                    AppConstants.PrintLog(TAG, "Delete Task Response Error -  " + t.message)
                    userSignUp.setValue(null)
                }
            })
    }
}
