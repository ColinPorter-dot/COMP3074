package ca.gbc.comp3074.porter_colin.labex1ver2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ca.gbc.comp3074.porter_colin.labex1ver2.ui.theme.LabEx1Ver2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabEx1Ver2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var i = remember { mutableStateOf(1)}
    var message = remember { mutableStateOf(context.resources.getString(R.string.message)) }
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message.value,
            modifier = modifier
        )
        Button(onClick = {
            val msgs = context.resources.getStringArray(R.array.values)
            message.value = msgs[i.value] //context.resources.getString(R.id.message)
            i.value = (i.value+1) % msgs.size
        }) {
            Text("Click Me!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabEx1Ver2Theme {
        Greeting("Android")
    }
}