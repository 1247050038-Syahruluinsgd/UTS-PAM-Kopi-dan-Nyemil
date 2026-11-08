package com.example.kopidannyemil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kopidannyemil.data.*
import com.example.kopidannyemil.ui.components.*
import com.example.kopidannyemil.ui.theme.*

/** 6. Profile */
@Composable
fun ProfileScreen(state: AppState, onHome: () -> Unit, onOrder: () -> Unit, onLogout: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(state.currentUser?.name ?: "") }

    Scaffold(
        containerColor = Cream,
        bottomBar = { AppBottomBar("profile", state.cartCount, onHome, onOrder, {}) }
    ) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
            Spacer(Modifier.height(16.dp))
            ScreenTitle("Halo ${state.displayName},")
            Spacer(Modifier.height(20.dp))
            Text("Profile Saya", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Profile Setting", color = Ink.copy(alpha = .6f))
            Spacer(Modifier.height(16.dp))
            AppTextField(name, { name = it }, "nama")
            Spacer(Modifier.height(8.dp))
            Text("Username: ${state.currentUser?.username ?: "-"}", color = Ink.copy(alpha = .6f))
            Spacer(Modifier.height(32.dp))
            PrimaryButton("Save", {
                if (name.isBlank()) Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                else { state.updateName(name); Toast.makeText(context, "Profil berhasil disimpan", Toast.LENGTH_SHORT).show() }
            })
            Spacer(Modifier.height(12.dp))
            OutlineButton("Logout", onLogout)
        }
    }
}

/** 7. Alamat pengiriman (Order) */
@Composable
fun AddressScreen(state: AppState, onBack: () -> Unit, onSubmit: () -> Unit) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf(state.currentUser?.name ?: "") }
    var address by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }

    Scaffold(containerColor = Cream) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
            TextButton(onBack) { Text("← Kembali", color = Ink) }
            ScreenTitle("Alamat")
            Spacer(Modifier.height(16.dp))

            Card(colors = CardDefaults.cardColors(containerColor = Peach.copy(alpha = .5f))) {
                Column(Modifier.padding(16.dp).fillMaxWidth()) {
                    Text("Ringkasan Pesanan", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    if (state.cart.isEmpty()) {
                        Text("Keranjang masih kosong. Pilih menu di Home dulu ya.", color = Brown)
                    } else {
                        state.cart.entries.toList().forEach { (id, qty) ->
                            DummyData.menu.firstOrNull { it.id == id }?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("${it.name} x$qty", Modifier.weight(1f))
                                    Text(formatRupiah(it.price * qty))
                                }
                            }
                        }
                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", fontWeight = FontWeight.Bold)
                            Text(formatRupiah(state.cartTotal), fontWeight = FontWeight.Bold, color = Brown)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            AppTextField(fullName, { fullName = it }, "Nama Lengkap")
            Spacer(Modifier.height(12.dp))
            AppTextField(address, { address = it }, "Alamat", minLines = 3)
            Spacer(Modifier.height(12.dp))
            AppTextField(landmark, { landmark = it }, "Patokan atau detail lengkap", minLines = 3)
            Spacer(Modifier.height(28.dp))
            PrimaryButton("Order dan Kirim", {
                when {
                    state.cart.isEmpty() -> Toast.makeText(context, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
                    fullName.isBlank() || address.isBlank() -> Toast.makeText(context, "Nama dan alamat wajib diisi", Toast.LENGTH_SHORT).show()
                    else -> onSubmit()
                }
            })
        }
    }
}

/** 8. Konfirmasi pesanan */
@Composable
fun ConfirmationScreen(state: AppState, onOk: () -> Unit) {
    Scaffold(containerColor = Cream) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(48.dp))
            ScreenTitle("Halo ${state.displayName},", Modifier.align(Alignment.Start))
            Spacer(Modifier.weight(1f))
            Box(Modifier.size(110.dp).clip(CircleShape).background(Mint), contentAlignment = Alignment.Center) {
                Text("OK", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = MintDark)
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "Terima kasih sudah memesan\ntunggu aja sebentar kurir kami\nsegera mengantarkan pesananmu!",
                textAlign = TextAlign.Center, color = Ink.copy(alpha = .8f)
            )
            Spacer(Modifier.weight(1f))
            PrimaryButton("Oke", onOk)
        }
    }
}
