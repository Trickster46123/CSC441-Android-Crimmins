package edu.lemoyne.campusapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.BackHandler


// --- Class 8 · Step 2: one rule book for goal names ---
const val MAX_NAME_LENGTH = 40

fun validateGoalName(input: String, existing: List<String>): String? {
    val name = input.trim()

    return when {
        name.isEmpty() -> "Enter a goal name"

        // --- Lab 8 · Task 1: minimum length ---
        name.length < 3 -> "Too short — at least 3 characters"

        // --- Lab 8 · Task 2: my own rule ---
        !name.first().isLetter() -> "Start with a letter"

        name.length > MAX_NAME_LENGTH ->
            "Keep it to $MAX_NAME_LENGTH characters or fewer"

        existing.any { it.equals(name, ignoreCase = true) } ->
            "\"$name\" is already on the list"

        else -> null
    }
}


// --- Class 7 · Step 1: a counter that remembers ---
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0) }

    Button(
        onClick = {
            count++
            println("count is now $count")
        }
    ) {
        Text("Tapped $count times")
    }
}


// --- Class 9 · Step 2: one owner for the data ---
@Composable
fun CampusAppScreen(modifier: Modifier = Modifier) {
    // --- Class 7 · Step 2: the list lives in state ---
    val goals = remember {
        mutableStateListOf(
            "Finish assignments",
            "Workout",
            "Work on personal projects",
            "Prepare for my career"
        )
    }

    // --- Class 9 · Step 4: which screen is showing is just state ---
    var currentScreen by rememberSaveable { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> HomeScreen(
            goals = goals,
            onAddGoal = { goals.add(it) },
            onSeeAll = { currentScreen = "list" },

            // --- Lab 9 · Task 2: open About ---
            onAbout = { currentScreen = "about" },
            modifier = modifier
        )

        "list" -> ListScreen(
            goals = goals,
            onBack = { currentScreen = "home" },
            modifier = modifier
        )

        // --- Lab 9 · Task 2: navigate to the About screen ---
        "about" -> AboutScreen(
            onBack = { currentScreen = "home" },
            modifier = modifier
        )
    }
}


// --- Class 9 · Step 2: HomeScreen gets its data from outside ---
// --- Lab 9 · Task 2: About screen callback ---
@Composable
fun HomeScreen(
    goals: List<String>,
    onAddGoal: (String) -> Unit,
    onSeeAll: () -> Unit,
    onAbout: () -> Unit,
    modifier: Modifier = Modifier
) {

    // --- Class 7 · Step 3: what's typed lives in state ---
    var newGoal by remember { mutableStateOf("") }

    // --- Class 8 · Step 3: the error message lives in state too ---
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // --- Class 6 · Step 3: a column, so things stack ---
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {

        // --- Lab 6 · Task 3: a picture of my own ---
        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription =
                "Abstract digital lines representing progress and momentum",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Class 7 · Step 3: the text field ---
        OutlinedTextField(
            value = newGoal,

            // --- Class 8 · Step 4: the field itself pushes back ---
            onValueChange = {
                newGoal = it.take(MAX_NAME_LENGTH)
                errorMessage = null
            },

            label = { Text("Goal name") },
            singleLine = true,
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )

        // --- Class 8 · Step 3: show the problem ---
        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // --- Lab 7 · Task 4: a live character counter ---
        Text(
            text = "${newGoal.length} / $MAX_NAME_LENGTH",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Class 7 · Step 4: the button changes the state ---
        Button(
            onClick = {

                // --- Class 8 · Step 3: check before you add ---
                val problem = validateGoalName(newGoal, goals)

                if (problem == null) {

                    // --- Class 9 · Step 2: ask the owner to add it ---
                    onAddGoal(newGoal.trim())
                    newGoal = ""
                    errorMessage = null

                } else {
                    errorMessage = problem
                }
            },

            // --- Class 8 · Step 5: the sign on the door, not the lock ---
            enabled = newGoal.isNotBlank()
        ) {
            Text("Add goal")
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
            text = if (goals.size == 1) {
                "1 goal"
            } else {
                "${goals.size} goals"
            },
            fontWeight = FontWeight.Bold
        )

        // --- Class 9 · Step 5: open the goal list ---
        Spacer(modifier = Modifier.height(8.dp))
// --- Class 9 · Step 5: open the goal list ---
        Button(onClick = onSeeAll) {
            Text("See all goals")
        }
// --- Lab 9 · Task 2: About button ---
        TextButton(onClick = onAbout) {
            Text("About")
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



// --- Class 9 · Step 3: the second screen ---
@Composable
fun ListScreen(
    goals: List<String>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // --- Class 9 · Step 6: system Back returns home ---
    BackHandler {
        onBack()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back")
        }

        Text(
            text = "All goals",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

// --- Lab 9 · Task 1: count on the list screen ---
        Text(
            text = if (goals.size == 1) "1 goal" else "${goals.size} goals",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (goal in goals) {
            Text(
                text = goal,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

// --- Lab 9 · Task 2: a third screen ---
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBack()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back")
        }

        Text(
            text = "About",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Momentum helps me keep track of my goals and stay motivated."
        )

        Text(
            text = "Built for CSC 441 by Sam Crimmins."
        )
    }
}

// --- Class 9 · Step 2: previews need sample data now ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CampusAppTheme {
        HomeScreen(
            goals = listOf("Finish assignments", "Workout"),
            onAddGoal = {},
            onSeeAll = {},
// --- Lab 9 · Task 2: About preview callback ---
            onAbout = {})
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
            HomeScreen(
                goals = listOf("Finish assignments", "Workout"),
                onAddGoal = {},
                onSeeAll = {},
// --- Lab 9 · Task 2: About preview callback ---
                onAbout = {})
        }
    }
}
// --- Class 9 · Step 7: preview the second screen ---
@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    CampusAppTheme {
        ListScreen(
            goals = listOf(
                "Finish assignments",
                "Workout",
                "Work on personal projects",
                "Prepare for my career"
            ),
            onBack = {}
        )
    }
}