package com.example.fathia

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fathia.model.MahasiswaViewModel

@Composable
fun UpdateMahasiswa(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: MahasiswaViewModel
) {
    val context = LocalContext.current
    var nrpInput by remember { mutableStateOf("") }
    var namaInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var jurusanInput by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            OutlinedTextField(
                value = nrpInput,
                onValueChange = { nrpInput = it },
                label = { Text("NRP") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = namaInput,
                onValueChange = { namaInput = it },
                label = { Text("Nama") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = jurusanInput,
                onValueChange = { jurusanInput = it },
                label = { Text("Jurusan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    if (nrpInput.isNotEmpty() && namaInput.isNotEmpty() && emailInput.isNotEmpty() && jurusanInput.isNotEmpty()) {
                        viewModel.addMahasiswa(nrpInput, namaInput, emailInput, jurusanInput)
                        Toast.makeText(context, "Mahasiswa berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                        nrpInput = ""
                        namaInput = ""
                        emailInput = ""
                        jurusanInput = ""
                    } else {
                        Toast.makeText(context, "Mohon lengkapi semua data.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF8A56AC)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A56AC))
            ) {
                Text(text = "Tambah Mahasiswa", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController?.navigate("search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF8A56AC)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A56AC))
            ) {
                Text(text = "Cari Mahasiswa", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        IconButton(
            onClick = { navController?.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Home",
                tint = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateMahasiswaPreview() {
    val dummyRepository = Repository()
    val dummyViewModel = MahasiswaViewModel(repository = dummyRepository)
    UpdateMahasiswa(viewModel = dummyViewModel)
}
