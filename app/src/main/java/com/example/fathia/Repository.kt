package com.example.fathia

import com.example.fathia.api.ApiConfig
import com.example.fathia.model.AddMahasiswaResponse
import com.example.fathia.model.Mahasiswa
import com.example.fathia.model.UpdateMahasiswaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class Repository {
    private val apiService = ApiConfig.apiService

    // Fungsi untuk mencari Mahasiswa berdasarkan nrp
    suspend fun searchMahasiswa(nrp: String): Result<Mahasiswa?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMahasiswa(nrp)
                if (response.isSuccessful) {
                    Result.success(response.body()?.data?.firstOrNull())
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: IOException) {
                Result.failure(e) // Koneksi atau error I/O
            } catch (e: Exception) {
                Result.failure(e) // Error tak terduga lainnya
            }
        }
    }

    // Fungsi untuk mendapatkan semua data Mahasiswa
    suspend fun getAllMahasiswa(): Result<List<Mahasiswa>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAllMahasiswa()
                if (response.isSuccessful) {
                    Result.success(response.body()?.data ?: emptyList())
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Fungsi untuk menambahkan Mahasiswa baru
    suspend fun addMahasiswa(nrp: String, nama: String, email: String, jurusan: String): Result<AddMahasiswaResponse?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.addMahasiswa(nrp, nama, email, jurusan)
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Fungsi untuk menghapus Mahasiswa berdasarkan id
    suspend fun deleteMahasiswaByNRP(nrp: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                // Cari mahasiswa berdasarkan NRP
                val mahasiswaResponse = apiService.getMahasiswa(nrp)
                if (!mahasiswaResponse.isSuccessful) {
                    return@withContext Result.failure(HttpException(mahasiswaResponse))
                }

                // Ambil ID dari mahasiswa yang ditemukan
                val mahasiswa = mahasiswaResponse.body()?.data?.firstOrNull()
                    ?: return@withContext Result.failure(Exception("Mahasiswa with NRP $nrp not found"))

                val id = mahasiswa.id
                    ?: return@withContext Result.failure(Exception("Invalid ID for NRP $nrp"))

                // Lakukan delete dengan ID
                val deleteResponse = apiService.delMahasiswa(id)
                if (deleteResponse.isSuccessful) {
                    val responseBody = deleteResponse.body()
                    if (responseBody?.success == true) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception(responseBody?.message ?: "Delete operation failed"))
                    }
                } else {
                    Result.failure(HttpException(deleteResponse))
                }
            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }



    // Fungsi untuk memperbarui data Mahasiswa
    suspend fun updateMahasiswa(nrp: String, nama: String, email: String, jurusan: String): Result<UpdateMahasiswaResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateMahasiswa(nrp, nama, email, jurusan)
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
