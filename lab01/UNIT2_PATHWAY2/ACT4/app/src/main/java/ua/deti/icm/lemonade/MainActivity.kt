package ua.deti.icm.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.deti.icm.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        Stand(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Stand(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableStateOf(0) }
    var squeezeCount by remember { mutableStateOf(0) }
    var squeezeAmount by remember { mutableStateOf((2..4).random()) }

    val stepData = when (currentStep) {
        0 -> StepData(R.drawable.lemon_tree, R.string.lemon_tree, R.string.tap_to_select)
        1 -> StepData(R.drawable.lemon_squeeze, R.string.lemon, R.string.tap_to_squeeze)
        2 -> StepData(R.drawable.lemon_drink, R.string.glass_of_lemonade, R.string.tap_to_drink)
        else -> StepData(R.drawable.lemon_restart, R.string.empty_glass, R.string.tap_to_start)
    }

    val click = {
        if (currentStep == 1) {
            if (squeezeCount >= squeezeAmount) {
                currentStep++
                squeezeCount = 0
            }
            squeezeCount++
        } else if (currentStep == 3) {
            currentStep = 0
            squeezeAmount = (2..4).random()
        } else {
            currentStep++
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Lemonade", fontSize = 20.sp) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { click() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Image(
                        painter = painterResource(id = stepData.image),
                        contentDescription = stringResource(stepData.imageLabel)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(stepData.step),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

data class StepData(
    val image: Int,
    val imageLabel: Int,
    val step: Int
)
