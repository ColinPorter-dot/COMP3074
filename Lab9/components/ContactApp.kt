package ca.gbc.comp3074.porter_colin.lab9.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ContactApp(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "contacts"
    ){
        composable("contacts"){
            ContactListScreen(onContactClick = {
                userId -> navController.navigate("contacts/${userId}")
            })
        }

        composable("details/{userId}"){
            backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                ContactDetailsScreen(userId = userId, onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}