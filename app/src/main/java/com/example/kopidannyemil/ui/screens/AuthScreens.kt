package com.example.kopidannyemil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kopidannyemil.data.AppState
import com.example.kopidannyemil.ui.components.*
import com.example.kopidannyemil.ui.theme.*

/** 1. Splash / Welcome */
@Composable
fun SplashScreen(onStart: () -> Unit) {
    Scaffold(containerColor = Cream) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.weight(1f))
            Box(Modifier.size(120.dp).clip(CircleShape).background(Peach), contentAlignment = Alignment.Center) {
                Text("☕", fontSize = 56.sp)
            }
            Spacer(Modifier.height(24.dp))
            Text("Kopi dan Nyemil", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
            Spacer(Modifier.height(8.dp))
            Text("Get a taste of your wish", color = Brown)
            Spacer(Modifier.weight(1f))
            PrimaryButton("Start now", onStart)
        }
    }
}

/** 2. Sign up (pilihan Register / Login) */
@Composable
fun SignUpScreen(onRegister: () -> Unit, onLogin: () -> Unit) {
    Scaffold(containerColor = Cream) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().padding(24.dp)) {
            Spacer(Modifier.height(48.dp))
            ScreenTitle("Sign up")
            Spacer(Modifier.height(12.dp))
            Text("Join as a member & experience more fun from various meal at mesen_gube marketing app", color = Ink.copy(alpha = .7f))
            Spacer(Modifier.weight(1f))
            PrimaryButton("Register", onRegister)
            Spacer(Modifier.height(12.dp))
            OutlineButton("Login", onLogin)
        }
    }
}

/** 3. Login User */
@Composable
fun LoginScreen(state: AppState, onSuccess: () -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(containerColor = Cream) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
            TextButton(onBack) { Text("← Kembali", color = Ink) }
            Spacer(Modifier.height(24.dp))
            ScreenTitle("Login User")
            Spacer(Modifier.height(4.dp))
            Text("Akun demo: demo / demo123", color = Brown)
            Spacer(Modifier.height(24.dp))
            AppTextField(username, { username = it; error = null }, "username")
            Spacer(Modifier.height(12.dp))
            AppTextField(password, { password = it; error = null }, "password", password = true)
            error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp)) }
            Spacer(Modifier.height(32.dp))
            PrimaryButton("Login", {
                if (state.login(username, password)) onSuccess() else error = "Username atau password salah"
            })
        }
    }
}

/** 4. Register */
@Composable
fun RegisterScreen(state: AppState, onSuccess: () -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(containerColor = Cream) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
            TextButton(onBack) { Text("← Kembali", color = Ink) }
            Spacer(Modifier.height(24.dp))
            ScreenTitle("Register")
            Spacer(Modifier.height(24.dp))
            AppTextField(name, { name = it; error = null }, "nama lengkap")
            Spacer(Modifier.height(12.dp))
            AppTextField(username, { username = it; error = null }, "username")
            Spacer(Modifier.height(12.dp))
            AppTextField(password, { password = it; error = null }, "password", password = true)
            error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp)) }
            Spacer(Modifier.height(32.dp))
            PrimaryButton("Register", {
                error = state.register(name, username, password)
                if (error == null) onSuccess()
            })
        }
    }
}
