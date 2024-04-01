package com.example.helloandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloandroid.ui.theme.HelloandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Greeting("Leyla")
                        val context = LocalContext.current
                        Button(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Hello Leyla!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            },
                        ) {
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Hello")
                        }
                        MoodSlider()
                        EnergySlider()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Cyan) {
        Text(
            text = "Hello my name is $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}
@Composable
fun MoodSlider() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Text(text = "How are you feeling today?")
        Button(onClick = { if (sliderPosition > 0) {sliderPosition -= 25} }) {
            Text("-")
        }
        Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                steps = 3,
                valueRange = 0f..100f
            )
        Text(text = sliderPosition.toString())
        Button(onClick = { if (sliderPosition < 100) {sliderPosition += 25} }) {
            Text("+")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergySlider() {
    val sliderState = remember {
        SliderState(
            valueRange = 0f..100f,
            steps = 3,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            }
        )
    }
    val interactionSource = remember { MutableInteractionSource() }
    val colors = SliderDefaults.colors(thumbColor = Color.Red, activeTrackColor = Color.Green,
        activeTickColor = Color.Blue, inactiveTickColor = Color.Blue)
    val colorStops = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "How much energy do you have?")
        Slider(
            state = sliderState,
            modifier = Modifier.semantics { contentDescription = "Energy level" }.width(150.dp),
            interactionSource = interactionSource,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    colors = colors
                )
                Box(
                    modifier = Modifier.height(30.dp).width(20.dp).background(Color.Blue)
                )
            },
//            content = { VerticalThumbSlider(sliderState)},
                track = {
                SliderDefaults.Track(
                    colors = colors,
                    sliderState = sliderState
                )
                Box(
                    modifier = Modifier.height(30.dp).requiredWidth(150.dp)
                        .border(3.dp, Color.Black).background(Brush.horizontalGradient(colors = colorStops))
                )
            },
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloandroidTheme {
        Greeting("Leyla")
    }
}

@Preview(
    name = "Mood Slider",
    group = "Sliders",
    showBackground = true,
)
@Composable
fun MoodSliderPreview() {
    MoodSlider()
}

@Preview(
    name = "Energy Slider",
    group = "Sliders",
    showBackground = true,
)
@Composable
fun EnergySliderPreview() {
    EnergySlider()
}