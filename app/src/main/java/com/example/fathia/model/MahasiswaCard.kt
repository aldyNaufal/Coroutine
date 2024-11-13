package com.example.fathia.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MahasiswaCard(mahasiswa: Mahasiswa) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Nama: ${mahasiswa.nama ?: "N/A"}")
            Text("NRP: ${mahasiswa.nrp ?: "N/A"}")
            Text("Email: ${mahasiswa.email ?: "N/A"}")
            Text("Jurusan: ${mahasiswa.jurusan ?: "N/A"}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMahasiswaCard() {
    MahasiswaCard(
        mahasiswa = Mahasiswa(
            nama = "Budi",
            nrp = "12345",
            email = "budi@example.com",
            jurusan = "Teknik Informatika"
        )
    )
}