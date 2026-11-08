package com.example.kopidannyemil.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Cream = Color(0xFFFAF6F1)
val Ink = Color(0xFF1C1C1C)
val Peach = Color(0xFFFFE8D6)
val PeachBorder = Color(0xFFF3CBAA)
val Brown = Color(0xFF8A4B1F)
val Mint = Color(0xFFB9F0D2)
val MintDark = Color(0xFF1B7A4B)

private val Scheme = lightColorScheme(
    primary = Ink,
    onPrimary = Color.White,
    secondary = Brown,
    background = Cream,
    surface = Color.White
)

@Composable
fun KopiNyemilTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
