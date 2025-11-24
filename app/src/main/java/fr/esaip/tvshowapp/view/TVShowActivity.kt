package fr.esaip.tvshowapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.esaip.tvshowapp.navigation.TVShowNavigation
import fr.esaip.tvshowapp.ui.theme.TVShowAppTheme

@AndroidEntryPoint
class TVShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TVShowAppTheme {
                val navController = rememberNavController()
                TVShowNavigation(navController = navController)
            }
        }
    }
}
