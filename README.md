# Kalkulator Android dengan Jetpack Compose

Proyek ini adalah aplikasi **kalkulator sederhana** berbasis Android menggunakan **Jetpack Compose**.  
Kalkulator ini mendukung operasi dasar (`+`, `-`, `*`, `/`), menampilkan **riwayat perhitungan**, dan memiliki tombol **C (Clear)** untuk mengatur ulang.

---

## 📌 Fitur
- Operasi dasar: **tambah, kurang, kali, bagi**
- Menangani **prioritas operator** (`*` dan `/` lebih dulu)
- **Riwayat perhitungan** ditampilkan menggunakan `LazyColumn` (item terbaru di bawah)
- **Tombol Clear (`C`)**:
  - Reset ekspresi menjadi `0`
  - Menghapus seluruh riwayat
- UI responsive menggunakan **Column & Row**
- Tombol dinamis di-generate dari `List<List<String>>`

---

## 💻 Teknologi
- Kotlin
- Jetpack Compose (Material 3)
- Android Studio Arctic Fox / Bumblebee ke atas
- Mutable state dengan `remember` & Compose delegation (`by`)

---

## 📝 Struktur Kode
- **MainActivity.kt**
  - `MainActivity` → entry point, memanggil `CalculatorApp()`
  - `Hitung(ekspresi: String): Int` → parser ekspresi dan evaluator operator dengan prioritas
  - `CalculatorButton` → composable tombol dengan style tetap
  - `CalculatorApp` → UI utama kalkulator
    - `Text` menampilkan ekspresi
    - `LazyColumn` menampilkan riwayat perhitungan
    - `Row` dan `forEach` untuk membuat tombol secara dinamis

---

## 🛠 Cara Menjalankan
1. Clone repository ini atau download ZIP.
2. Buka di **Android Studio**.
3. Pastikan menggunakan **Kotlin 1.8+** dan Compose Material 3.
4. Jalankan aplikasi di emulator atau device Android.
