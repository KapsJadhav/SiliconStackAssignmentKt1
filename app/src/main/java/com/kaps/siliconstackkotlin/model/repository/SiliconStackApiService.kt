package com.kaps.siliconstackkotlin.model.repository

import com.kaps.siliconstackkotlin.model.Note
import com.kaps.siliconstackkotlin.model.helper.AppConstants
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SiliconStackApiService {


    @GET(AppConstants.ApiNames.GET_ALL_TASK)
    fun getAllNotes(): Call<List<Note?>?>?


//    @GET(AppConstants.ApiNames.GET_ALL_TASK)
//    suspend fun allNotes(): Call<List<Note?>?>?

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.ADD_TASK)
    fun addNote(
        @Field("user_id") sUserId: String?,
        @Field("title") sTitle: String?,
        @Field("body") sBody: String?,
        @Field("note") sNote: String?,
        @Field("status") sStatus: String?
    ): Call<Note?>?

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.EDIT_TASK)
    fun updateNote(
        @Field("id") sId: String?,
        @Field("user_id") sUserId: String?,
        @Field("title") sTitle: String?,
        @Field("body") sBody: String?,
        @Field("note") sNote: String?,
        @Field("status") sStatus: String?
    ): Call<Note?>?

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.DELETE_TASK)
    fun deleteNote(
        @Field("id") sId: String?,
        @Field("user_id") sUserId: String?
    ): Call<Note?>?
}