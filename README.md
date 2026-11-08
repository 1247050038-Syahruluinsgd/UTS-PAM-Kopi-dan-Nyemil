☕ Kopi dan Nyemil
Aplikasi mobile Android untuk memesan kopi dan makanan ringan, dibuat berdasarkan wireframe "Kopi dan Nyemil" sebagai tugas Pemrograman Mobile.
Deskripsi
Pengguna membuka aplikasi, mendaftar atau login, memilih menu di halaman Home, mengisi alamat pengiriman, lalu menerima konfirmasi pesanan. Seluruh data bersifat statis (dummy) dan disimpan di memori, tanpa database.
Fitur
Splash / Welcome: logo, nama aplikasi, tombol Start now
Sign Up: pilihan menuju Register atau Login
Register: nama lengkap, username, password (dengan validasi)
Login: validasi username dan password (akun demo: `demo` / `demo123`)
Home: 7 menu makanan/minuman, filter kategori, tombol tambah/kurang jumlah, bottom navigation (Home, Order, Profile) dengan badge jumlah item
Profile: ubah nama, simpan, logout
Alamat Pengiriman (Order): ringkasan pesanan + total, form nama, alamat, patokan
Konfirmasi Pesanan: pesan terima kasih, tombol Oke kembali ke Home
Teknologi
Kotlin
Jetpack Compose + Material 3
Navigation Compose
Android Studio
Cara Menjalankan
Clone repository: `git clone <url-repository-kamu>`
Buka folder project di Android Studio (Ladybug atau lebih baru)
Tunggu Gradle sync selesai
Pilih emulator atau perangkat Android (minSdk 24), lalu klik Run ▶
Screenshot
Splash	Sign Up	Login	Register
![](screenshots/1_splash.png)	![](screenshots/2_signup.png)	![](screenshots/3_login.png)	![](screenshots/4_register.png)

Home	Profile	Alamat	Konfirmasi
![](screenshots/5_home.png)	![](screenshots/6_profile.png)	![](screenshots/7_alamat.png)	![](screenshots/8_konfirmasi.png)
Struktur Folder
```
app/src/main/java/com/example/kopidannyemil/
├── MainActivity.kt        # Navigasi antar layar
├── data/AppData.kt        # Model, dummy data, state aplikasi
└── ui/
    ├── theme/Theme.kt
    ├── components/Components.kt
    └── screens/ (AuthScreens, HomeScreen, OtherScreens)
```
Author
Syahrul Adriansyah – 1247050038
