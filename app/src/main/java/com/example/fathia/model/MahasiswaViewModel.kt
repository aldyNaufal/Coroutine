package com.example.fathia.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fathia.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MahasiswaViewModel(private val repository: Repository) : ViewModel() {

    private val _mahasiswa = MutableLiveData<Mahasiswa?>()
    val mahasiswa: LiveData<Mahasiswa?> get() = _mahasiswa

    private val _allMahasiswa = MutableStateFlow<List<Mahasiswa>>(emptyList())
    val allMahasiswa: StateFlow<List<Mahasiswa>> get() = _allMahasiswa

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _deleteSuccess = MutableStateFlow<Boolean>(false)
    val deleteSuccess: StateFlow<Boolean> = _deleteSuccess.asStateFlow()

    fun searchMahasiswa(nrp: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getAllMahasiswa()
                if (response.isSuccess) {
                    val mahasiswaList = response.getOrDefault(emptyList())
                    val foundMahasiswa = mahasiswaList.find { it.nrp == nrp }
                    _mahasiswa.value = foundMahasiswa
                    _error.value = if (foundMahasiswa == null) "Mahasiswa tidak ditemukan" else null
                } else {
                    _error.value = "Gagal mencari mahasiswa"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getAllMahasiswa() {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getAllMahasiswa()
            _loading.value = false
            result.onSuccess { data ->
                _allMahasiswa.value = data
                _error.postValue(null) // Reset error
            }.onFailure { exception ->
                _allMahasiswa.value = emptyList() // Reset data if failed
                _error.postValue(exception.message ?: "Unknown error")
            }
        }
    }

    fun deleteMahasiswa(nrp: String) {
        _loading.value = true
        _error.value = null

        viewModelScope.launch {
            val result = repository.deleteMahasiswaByNRP(nrp)
            _loading.value = false

            result.onSuccess {
                _deleteSuccess.value = true
                _mahasiswa.value = null  // Clear displayed mahasiswa after successful delete
            }.onFailure { exception ->
                _error.value = exception.message ?: "Failed to delete mahasiswa"
            }
        }
    }

    fun resetState() {
        _error.value = null
        _deleteSuccess.value = false
    }






    fun addMahasiswa(nrp: String, nama: String, email: String, jurusan: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = repository.addMahasiswa(nrp, nama, email, jurusan)
                result.onSuccess {
                    // Refresh the list to include the newly added mahasiswa
                    getAllMahasiswa()
                    _error.value = null
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Failed to add mahasiswa"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
