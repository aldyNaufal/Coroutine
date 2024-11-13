package com.example.fathia.api

import com.example.fathia.model.AddMahasiswaResponse
import com.example.fathia.model.DeleteMahasiswaResponse
import com.example.fathia.model.MahasiswaResponse
import com.example.fathia.model.UpdateMahasiswaResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("mahasiswa")
    suspend fun getAllMahasiswa(): Response<MahasiswaResponse>

    @FormUrlEncoded
    @POST("mahasiswa")
    suspend fun addMahasiswa(
        @Field ("nrp") nrp: String?,
        @Field ("nama") nama: String?,
        @Field ("email") email: String?,
        @Field ("jurusan") jurusan: String?
    ): Response<AddMahasiswaResponse?>

    @FormUrlEncoded
    @PUT("mahasiswa")
    suspend fun updateMahasiswa(
        @Field("nrp") nrp: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("jurusan") jurusan: String
    ): Response<UpdateMahasiswaResponse>

    @GET("mahasiswa")
    suspend fun getMahasiswa(@Query("nrp") nrp: String? = null): Response<MahasiswaResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "mahasiswa", hasBody = true)
    suspend fun delMahasiswa(
        @Field("id") id: String
    ): Response<DeleteMahasiswaResponse>

}