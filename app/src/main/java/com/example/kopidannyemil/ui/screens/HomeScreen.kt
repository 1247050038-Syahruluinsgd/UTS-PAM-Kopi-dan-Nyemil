package com.example.kopidannyemil.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kopidannyemil.data.*
import com.example.kopidannyemil.ui.components.*
import com.example.kopidannyemil.ui.theme.*

/** 5. Home */
@Composable
fun HomeScreen(state: AppState, onOrder: () -> Unit, onProfile: () -> Unit) {
    var category by remember { mutableStateOf("Semua") }
    val items = DummyData.menu.filter { category == "Semua" || it.category == category }

    Scaffold(
        containerColor = Cream,
        bottomBar = { AppBottomBar("home", state.cartCount, {}, onOrder, onProfile) }
    ) { pad ->
        Column(Modifier.padding(pad).fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(16.dp))
            ScreenTitle("Halo ${state.displayName},")
            Text("Mau ngopi atau nyemil apa hari ini?", color = Ink.copy(alpha = .6f))
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DummyData.categories.forEach { c ->
                    FilterChip(
                        selected = category == c, onClick = { category = c }, label = { Text(c) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Ink, selectedLabelColor = Color.White)
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 16.dp)) {
                items(items, key = { it.id }) { item ->
                    MenuCard(item, state.cart[item.id] ?: 0, { state.add(item.id) }, { state.remove(item.id) })
                }
            }
        }
    }
}

@Composable
private fun MenuCard(item: MenuItem, qty: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Peach.copy(alpha = .5f)),
        border = BorderStroke(1.dp, PeachBorder)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(68.dp).clip(RoundedCornerShape(14.dp)).background(Peach), contentAlignment = Alignment.Center) {
                Text(item.emoji, fontSize = 32.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, color = Ink)
                Text("• ${item.description}", fontSize = 12.sp, color = Ink.copy(alpha = .7f))
                Text(formatRupiah(item.price), fontWeight = FontWeight.SemiBold, color = Brown)
            }
            if (qty == 0) {
                Button(onAdd, colors = ButtonDefaults.buttonColors(containerColor = Ink, contentColor = Color.White)) { Text("Tambah") }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onRemove, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(34.dp)) { Text("−", color = Ink) }
                    Text("$qty", modifier = Modifier.padding(horizontal = 10.dp), fontWeight = FontWeight.Bold)
                    Button(onAdd, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(34.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Ink)) { Text("+") }
                }
            }
        }
    }
}
