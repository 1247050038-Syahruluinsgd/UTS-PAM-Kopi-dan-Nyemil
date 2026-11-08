package com.example.kopidannyemil.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

data class User(val name: String, val username: String, val password: String)

data class MenuItem(
    val id: Int,
    val emoji: String,
    val name: String,
    val description: String,
    val price: Int,
    val category: String
)

object DummyData {
    val categories = listOf("Semua", "Minuman", "Makanan")
    val menu = listOf(
        MenuItem(1, "☕", "Kopi Susu Gula Aren", "Espresso, susu, gula aren", 22000, "Minuman"),
        MenuItem(2, "🍩", "Donat Cokelat", "Donat empuk, glaze cokelat", 12000, "Makanan"),
        MenuItem(3, "🍞", "Sourdough Loaf", "Roti fermentasi alami", 35000, "Makanan"),
        MenuItem(4, "🥛", "Cappuccino", "Espresso, foam susu lembut", 25000, "Minuman"),
        MenuItem(5, "🍵", "Matcha Latte", "Matcha Jepang, susu segar", 27000, "Minuman"),
        MenuItem(6, "🥐", "Butter Croissant", "Croissant renyah, butter premium", 20000, "Makanan"),
        MenuItem(7, "🧋", "Es Kopi Hazelnut", "Kopi dingin, sirup hazelnut", 26000, "Minuman")
    )
}

fun formatRupiah(value: Int): String =
    String.format(Locale.forLanguageTag("id-ID"), "Rp %,d", value)

/** State global aplikasi (tanpa database, semua disimpan di memori). */
class AppState {
    val users = mutableStateListOf(User("Pelanggan Demo", "demo", "demo123"))
    var currentUser by mutableStateOf<User?>(null)
        private set
    val cart = mutableStateMapOf<Int, Int>()

    val displayName: String get() = currentUser?.name ?: "Nama"
    val cartCount: Int get() = cart.values.sum()
    val cartTotal: Int
        get() = cart.entries.sumOf { (id, qty) ->
            (DummyData.menu.firstOrNull { it.id == id }?.price ?: 0) * qty
        }

    fun login(username: String, password: String): Boolean {
        val user = users.firstOrNull { it.username == username.trim() && it.password == password }
        currentUser = user
        return user != null
    }

    /** Return pesan error, atau null jika berhasil. */
    fun register(name: String, username: String, password: String): String? {
        if (name.isBlank() || username.isBlank() || password.isBlank()) return "Semua kolom wajib diisi"
        if (password.length < 6) return "Password minimal 6 karakter"
        if (users.any { it.username.equals(username.trim(), true) }) return "Username sudah dipakai"
        val user = User(name.trim(), username.trim(), password)
        users.add(user)
        currentUser = user
        return null
    }

    fun updateName(newName: String) {
        val old = currentUser ?: return
        val updated = old.copy(name = newName.trim())
        val i = users.indexOf(old)
        if (i >= 0) users[i] = updated
        currentUser = updated
    }

    fun logout() { currentUser = null; cart.clear() }
    fun add(id: Int) { cart[id] = (cart[id] ?: 0) + 1 }
    fun remove(id: Int) {
        val q = (cart[id] ?: 0) - 1
        if (q <= 0) cart.remove(id) else cart[id] = q
    }
    fun clearCart() = cart.clear()
}
