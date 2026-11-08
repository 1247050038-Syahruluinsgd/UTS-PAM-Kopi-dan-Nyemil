package com.example.kopidannyemil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kopidannyemil.data.AppState
import com.example.kopidannyemil.ui.screens.*
import com.example.kopidannyemil.ui.theme.KopiNyemilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { KopiNyemilTheme { KopiNyemilApp() } }
    }
}

object Routes {
    const val SPLASH = "splash"
    const val SIGNUP = "signup"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val ADDRESS = "address"
    const val CONFIRM = "confirm"
}

private fun NavHostController.goHome() {
    navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = false }; launchSingleTop = true }
}

@Composable
fun KopiNyemilApp() {
    val nav = rememberNavController()
    val state = remember { AppState() }

    NavHost(nav, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) { SplashScreen { nav.navigate(Routes.SIGNUP) } }
        composable(Routes.SIGNUP) {
            SignUpScreen(
                onRegister = { nav.navigate(Routes.REGISTER) },
                onLogin = { nav.navigate(Routes.LOGIN) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(state, onSuccess = {
                nav.navigate(Routes.HOME) { popUpTo(Routes.SPLASH) { inclusive = true } }
            }, onBack = { nav.popBackStack() })
        }
        composable(Routes.REGISTER) {
            RegisterScreen(state, onSuccess = {
                nav.navigate(Routes.HOME) { popUpTo(Routes.SPLASH) { inclusive = true } }
            }, onBack = { nav.popBackStack() })
        }
        composable(Routes.HOME) {
            HomeScreen(state,
                onOrder = { nav.navigate(Routes.ADDRESS) },
                onProfile = { nav.navigate(Routes.PROFILE) { launchSingleTop = true } })
        }
        composable(Routes.PROFILE) {
            ProfileScreen(state,
                onHome = { nav.goHome() },
                onOrder = { nav.navigate(Routes.ADDRESS) },
                onLogout = {
                    state.logout()
                    nav.navigate(Routes.SPLASH) { popUpTo(0) }
                })
        }
        composable(Routes.ADDRESS) {
            AddressScreen(state, onBack = { nav.popBackStack() },
                onSubmit = { nav.navigate(Routes.CONFIRM) })
        }
        composable(Routes.CONFIRM) {
            ConfirmationScreen(state) {
                state.clearCart()
                nav.goHome()
            }
        }
    }
}
