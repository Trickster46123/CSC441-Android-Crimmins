package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CampusAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


// --- Class 7 · Step 1: a counter that remembers ---
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0) }

    Button(onClick = {
        count++
        println("count is now $count")
    }) {
        Text("Tapped $count times")
    }
}
// --- Class 6 · Step 1: my own screen ---
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    // --- Class 7 · Step 3: what's typed lives in state ---
    var newGoal by remember { mutableStateOf("") }

    // --- Class 7 · Step 2: the list lives in state ---
    val goals = remember {
        mutableStateListOf(
            "Finish assignments",
            "Workout",
            "Work on personal projects",
            "Prepare for my career"
        )
    }
    // --- Class 6 · Step 3: a column, so things stack ---
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)

    ) {

        // --- Lab 6 · Task 3: a picture of my own ---
        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "Abstract digital lines representing progress and momentum",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Class 7 · Step 3: the text field ---
        OutlinedTextField(
            value = newGoal,
            onValueChange = { newGoal = it },
            label = { Text("Goal name") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- Lab 7 · Task 4: a live character counter ---
        Text(
            text = "${newGoal.length} / 40",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Class 7 · Step 4: the button changes the state ---
        Button(onClick = {
            goals.add(newGoal)
            newGoal = ""
        }) {
            Text("Add goal")
        }
        // --- Lab 7 · Task 1: remove the last item ---
        Button(onClick = {
            if (goals.isNotEmpty()) {
                goals.removeAt(goals.lastIndex)
            }
        }) {
            Text("Remove last")
        }

// --- Lab 7 · Task 3: clear all ---
        Button(onClick = {
            goals.clear()
        }) {
            Text("Clear all")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Class 6 · Step 4: real styling ---
        Text(
            text = "Momentum",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Keep moving toward your goals",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))


        // --- Lab 7 · Task 2: singular and plural ---
        Text(
            text = if (goals.size == 1) "1 goal" else "${goals.size} goals",
            fontWeight = FontWeight.Bold
        )

        for (goal in goals) {
            Text(
                text = goal,
                fontSize = 18.sp
            )
        }

        // --- Lab 6 · Task 2: footer ---
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Last updated September 2026",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// --- Class 6 · Step 2: preview, no build required ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CampusAppTheme {
        HomeScreen()
    }
}

// --- Lab 6 · Task 4: dark mode preview ---
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenDarkPreview() {
    CampusAppTheme {
        Surface {
            HomeScreen()
        }
    }
}
