package com.example.fathia.model

import com.google.gson.annotations.SerializedName

data class Mahasiswa(
    @SerializedName("nama") var nama: String? = null,
    @SerializedName("nrp") var nrp: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("jurusan") var jurusan: String? = null,
    @SerializedName("id") var id: String? = null
)