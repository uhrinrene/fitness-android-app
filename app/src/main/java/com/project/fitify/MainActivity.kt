package com.project.fitify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.project.fitify.navigation.NavGraph
import com.project.fitify.ui.theme.FitifyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitifyTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
