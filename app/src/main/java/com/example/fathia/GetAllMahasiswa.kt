package com.example.fathia

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.fathia.model.Mahasiswa
import com.example.fathia.model.MahasiswaViewModel

@Composable
fun GetAllMahasiswa(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: MahasiswaViewModel
) {
    // Mengamati data mahasiswa dari ViewModel
    val mahasiswaData by viewModel.allMahasiswa.collectAsState(initial = emptyList())
    val isLoading by viewModel.loading.observeAsState(initial = false)
    val errorMessage by viewModel.error.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllMahasiswa()  // Memanggil fungsi untuk mendapatkan data mahasiswa
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading && mahasiswaData.isEmpty()) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(text = errorMessage!!, color = Color.Red)
        } else {
            val dataToDisplay = mahasiswaData.take(3)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(dataToDisplay) { mahasiswa ->
                    MahasiswaItem(mahasiswa = mahasiswa, onDelete = {
                        viewModel.deleteMahasiswa(mahasiswa.nrp ?: "")
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController?.navigate("update") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A56AC))
        ) {
            Text("Tambah", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = { navController?.navigate("search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A56AC))
        ) {
            Text("Cari Mahasiswa", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MahasiswaItem(mahasiswa: Mahasiswa, onDelete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }

        Text(text = "NRP: ${mahasiswa.nrp ?: "null"}", fontSize = 14.sp)
        Text(text = "Nama: ${mahasiswa.nama ?: "null"}", fontSize = 14.sp)
        Text(text = "Email: ${mahasiswa.email ?: "null"}", fontSize = 14.sp)
        Text(text = "Jurusan: ${mahasiswa.jurusan ?: "null"}", fontSize = 14.sp)
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun GetAllMahasiswaPreview() {
    // Anda mungkin perlu menggunakan data dummy atau inisialisasi dummy ViewModel
    val dummyRepository = Repository()
    val dummyViewModel = MahasiswaViewModel(repository = dummyRepository)
    GetAllMahasiswa(viewModel = dummyViewModel)
}
