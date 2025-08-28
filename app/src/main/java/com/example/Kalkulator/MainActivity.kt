package com.example.Kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// import androidx.compose.foundation.layout.Box // Tidak terpakai saat ini
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
// import androidx.compose.material3.Scaffold // Tidak terpakai saat ini
import androidx.compose.material3.Text
// import androidx.compose.material3.OutlinedTextField // Komentar karena tidak dipakai di Greeting baru
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // Diperlukan untuk padding(16.dp)
import com.example.Kalkulator.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Ditambahkan jika belum ada secara eksplisit untuk items di LazyColumn
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CalculatorApp()
            }
        }
    }
}

fun Hitung(ekspresi: String): Int{
    var tokens = mutableListOf<String>()
    var num = ""

    for(c in ekspresi){
        if(c.isDigit()){
            num += c
        } else if (c in "+-*/"){
            tokens.add(num)
            tokens.add(c.toString())
            num = ""
        }
    }

    if(num.isNotEmpty()) tokens.add(num)

    var i = 0
    while (i < tokens.size){
        if(tokens[i] == "*" || tokens[i] == "/"){
            val a = tokens[i-1].toIntOrNull() ?: 0
            val b = tokens[i+1].toIntOrNull() ?: 1
            if (b == 0 && tokens[i] == "/") {
                return 0
            }
            val hasilHitung = if (tokens[i] == "*") a * b else if (b != 0) a / b else 0 // Hindari pembagian dengan nol
            tokens[i-1] = hasilHitung.toString()
            tokens.removeAt(i)
            tokens.removeAt(i)
            i--
        }
        i++
    }
    var hasilAkhir = tokens[0].toIntOrNull() ?: 0 // Pengaman
    i = 1
    while (i < tokens.size){
        val op = tokens[i]
        val b = tokens[i + 1].toIntOrNull() ?: 0 // Pengaman
        when(op){
            "+" -> hasilAkhir += b
            "-" -> hasilAkhir -= b
        }
        i += 2
    }
    return hasilAkhir
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .width(64.dp) // Memberi lebar tetap untuk tombol
            .height(64.dp) // Memberi tinggi tetap untuk tombol
    ) {
        Text(text, fontSize = 18.sp)
    }
}

@Composable
fun CalculatorApp() {
    var expression by remember { mutableStateOf("0") } // Menggunakan by delegate
    var history by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding( // Padding diubah di sini
                start = 16.dp,
                top = 32.dp, // Mengurangi padding atas secara signifikan
                end = 16.dp,
                bottom = 16.dp // Menambah padding bawah untuk konsistensi
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Membuat LazyColumn mengisi ruang yang tersedia
            reverseLayout = true // Ini akan membuat item mulai dari bawah
        ) {
            items(history) { historyEntry -> // 'historyEntry' adalah setiap String dalam list 'history'
                Text(
                    text = historyEntry, // Menampilkan item history individual
                    modifier = Modifier
                        .fillMaxWidth() // Mengisi lebar agar rata kanan berfungsi
                        .padding(vertical = 4.dp), // Padding vertikal tetap
                    textAlign = TextAlign.End // Mengatur teks rata kanan
                )
            }
        }

        Spacer(Modifier.height(16.dp)) // Memberi jarak antara LazyColumn dan Text ekspresi

        Text(
            text = expression,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp), // Padding vertikal bisa disesuaikan
            fontSize = 48.sp, // Ukuran font lebih besar untuk display
            textAlign = TextAlign.End // Teks rata kanan
        )

        // Baris tombol angka dan operator
        val buttonRows = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", "C", "=", "+")
        )

        buttonRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Menyebar tombol secara merata
            ) {
                row.forEach { buttonText ->
                    CalculatorButton(
                        text = buttonText,
                        onClick = {
                            if (buttonText == "=") {
                                val result = Hitung(expression)
                                val newHistoryEntry = "$expression=$result"
                                history = history + newHistoryEntry // Menambahkan entri baru ke list, membuat instance baru
                                expression = result.toString()
                            } else if (expression == "0" && buttonText in "0123456789") {
                                expression = buttonText
                            } else if(buttonText == "C"){
                                expression = "0" // Diperbaiki: Tombol C mengatur ulang expression ke "0"
                                history = listOf() // Juga membersihkan history
                            } else {
                                expression += buttonText
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    MyApplicationTheme {
        CalculatorApp()
    }
}
