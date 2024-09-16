package com.example.greetingsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingsapp.ui.theme.GreetingsAppTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingsAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    // generate a greeting based on the time of day
    fun genGreeting(name: String) {

        val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        when (time) {
            in 5..11 -> greeting = "Good morning, $name! Hope you have a good day today! ‧₊˚❀༉‧₊˚."
            in 12..17 -> greeting = "Good afternoon, $name! Hope you had a good morning!"
            in 18..20 -> greeting =
                "Good evening, $name! Hope you had a good day today! ⊹₊⟡⋆࣪ ִֶָ☾."

            in 21..1 -> greeting = "Good night, $name! It's time to sleep soon!"
            else -> greeting = "Hello $name! Go to bed! It's late!"
        }
    }


    // display textfield, button, and greeting
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter your name!",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.hsv(207.1f, 0.93f, 0.38f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // had issues with empty name case, would go to the "please enter your name" text without pressing the button
        // made greeting prompt user to enter name, instead of using if-else conditions below
        ElevatedButton(onClick = {
            // checks for all spaces/whitespace
            if (name.isEmpty()) {
                greeting = "Please enter your name, thanks!\n\n(❁´◡`❁)"
            }
            // includes spaces, in case of first + last name cases
            // excludes names that are just line breaks or spaces
            else if ((name.filter { it in 'A'..'Z' || it in 'a'..'z' || it == ' '}.length != name.length) ||
                    (name.filter { it == ' '}.length == name.length)){
                greeting = "Please enter a valid name!"
            }
            else {
                genGreeting(name)
            }
        }) {
            Text(text = "Greet")
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = greeting,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.hsv(207.1f, 0.93f, 0.38f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 30.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingsAppTheme {
        Greeting()
    }
}