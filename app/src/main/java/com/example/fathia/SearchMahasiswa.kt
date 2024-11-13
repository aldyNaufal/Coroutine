package com.example.fathia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fathia.model.MahasiswaViewModel

@Composable
fun SearchMahasiswa(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: MahasiswaViewModel
) {
    var nrp by remember { mutableStateOf("") }
    val isLoading by viewModel.loading.observeAsState(false)
    val errorMessage by viewModel.error.observeAsState()
    val selectedMahasiswa by viewModel.mahasiswa.observeAsState()
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()

    LaunchedEffect(deleteSuccess) {
        if (deleteSuccess) {
            nrp = "" // Reset input
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nrp,
                onValueChange = { nrp = it },
                label = { Text("NRP") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController?.navigate("update") },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF8A56AC)),
            ) {
                Text("Tambah Mahasiswa")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { viewModel.searchMahasiswa(nrp) },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF8A56AC)),
            ) {
                Text("Cari Mahasiswa")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Delete button only enabled when a mahasiswa is selected
            Button(
                onClick = { selectedMahasiswa?.nrp?.let { viewModel.deleteMahasiswa(it) } },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFD0000)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50)),
                enabled = selectedMahasiswa != null && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Delete Mahasiswa", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator()
            }

            errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            selectedMahasiswa?.let { mahasiswa ->
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "NRP       : ${mahasiswa.nrp}", fontSize = 16.sp)
                    Text(text = "Nama      : ${mahasiswa.nama}", fontSize = 16.sp)
                    Text(text = "Email     : ${mahasiswa.email}", fontSize = 16.sp)
                    Text(text = "Jurusan   : ${mahasiswa.jurusan}", fontSize = 16.sp)
                }
            }
        }

        IconButton(
            onClick = { navController?.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
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
fun PreviewSearchMahasiswa() {
    val dummyRepository = Repository()
    val dummyViewModel = MahasiswaViewModel(repository = dummyRepository)
    SearchMahasiswa(viewModel = dummyViewModel)
}

