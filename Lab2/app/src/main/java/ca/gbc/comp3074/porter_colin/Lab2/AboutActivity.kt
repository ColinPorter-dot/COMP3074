package ca.gbc.comp3074.porter_colin.Lab2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ca.gbc.comp3074.porter_colin.Lab2.ui.theme.Lab2Theme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LabEx2(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LabEx2(modifier: Modifier = Modifier) {
    var cnt = remember { mutableIntStateOf(0) }
    var step = remember { mutableIntStateOf(1) }
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_standin),
                contentDescription = "Application Logo",
                modifier = Modifier.width(200.dp).height(100.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text("Count: ${cnt.intValue}")
            Text("Step: ${step.intValue}")
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
             Button(onClick = {cnt.intValue -= step.intValue}, modifier = Modifier.weight(1f),
                 colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                 Icon(
                     imageVector = Icons.Default.Remove,
                     contentDescription = "Icon Call"
                 )
            }
            Button(onClick = {cnt.intValue += step.intValue}, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Icon Call"
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Button(onClick = {cnt.intValue = 0; step.intValue = 1}, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("Reset")
            }
            Button(onClick = {step.intValue += 1}, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
                Text("Step")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Lab2Theme {
        LabEx2()
    }
}