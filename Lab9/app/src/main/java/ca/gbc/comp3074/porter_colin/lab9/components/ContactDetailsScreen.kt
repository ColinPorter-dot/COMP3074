package ca.gbc.comp3074.porter_colin.lab9.components

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ca.gbc.comp3074.porter_colin.lab9.api.RetrofitInstance
import ca.gbc.comp3074.porter_colin.lab9.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    userId: Int?,
    onBack:()-> Unit
){
    var user by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null)}
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        if (userId == null){
            errorMessage = "Invalid user ID"
            loading = false
            return@LaunchedEffect
        }
        try {
            user = RetrofitInstance.api.getUser(userId)
        } catch (e:Exception){
            errorMessage = "Failed to load user details"
        } finally {
            loading = false
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) {
        paddingValues -> when{
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${errorMessage}")
                }
            }
            user != null -> {
                ContactDetails(
                    user = user!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ContactDetails(user: User, modifier: Modifier){
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize().padding(60.dp)) {
        Text(text = "Name: ${user.name}")
        Text(text = "Email: ${user.email}")
        Text(text = "Phone: ${user.phone}", modifier = modifier.clickable{
            dialPhoneNumber(context, user.phone)
        })
        Text(text = "Website: ${user.website}")
        Text(text = "ID: ${user.id}")
    }
}

fun dialPhoneNumber(context: Context, phoneNumber: String){
    val phoneUri = "tel:$phoneNumber".toUri()
    val intent = Intent(Intent.ACTION_DIAL, phoneUri)
    context.startActivity(intent)
}