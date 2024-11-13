package com.example.fathia.model

import com.google.gson.annotations.SerializedName

data class MahasiswaResponse(
    @SerializedName("data")
    val data: List<Mahasiswa>?
)

data class AddMahasiswaResponse(
    val success: Boolean,
    val message: String
)

data class UpdateMahasiswaResponse(
    val success: Boolean,
    val message: String
)

data class DeleteMahasiswaResponse(
    val success: Boolean,
    val message: String
)
