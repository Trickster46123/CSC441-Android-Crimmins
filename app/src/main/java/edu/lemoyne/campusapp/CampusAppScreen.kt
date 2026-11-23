
package edu.lemoyne.campusapp

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

// Class 8: Goal name validation
const val MAX_NAME_LENGTH = 40

fun validateGoalName(
    input: String,
    existing: List<String>
): String? {

    val name = input.trim()

    return when {
        name.isEmpty() -> "Enter a goal name"

        name.length < 3 ->
            "Too short — at least 3 characters"

        !name.first().isLetter() ->
            "Start with a letter"

        name.length > MAX_NAME_LENGTH ->
            "Keep it to $MAX_NAME_LENGTH characters or fewer"

        existing.any {
            it.equals(name, ignoreCase = true)
        } -> "\"$name\" is already on the list"

        else -> null
    }
}

// Class 7: Counter demo
@Composable
fun CounterDemo() {

    var count by remember {
        mutableStateOf(0)
    }

    Button(
        onClick = {
            count++
            println("count is now $count")
        }
    ) {
        Text("Tapped $count times")
    }
}

// Lab 22: Main screen owner
@Composable
fun CampusAppScreen(
    showcaseState: ShowcaseUiState,
    loginState: LoginUiState,
    onRefreshShowcase: () -> Unit,
    onLogin: (String, String, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {

    // Original Momentum goals
    val goals = remember {
        mutableStateListOf(
            "Finish assignments",
            "Workout",
            "Work on personal projects",
            "Prepare for my career",
            "Improve my programming skills"
        )
    }

    var currentScreen by rememberSaveable {
        mutableStateOf("home")
    }

    when (currentScreen) {

        // Original Home screen
        "home" -> HomeScreen(
            goals = goals,
            onAddGoal = { goals.add(it) },
            onSeeAll = {
                currentScreen = "list"
            },
            onAbout = {
                currentScreen = "about"
            },
            onInterestsClick = {
                currentScreen = "login"
            },
            modifier = modifier
        )

        // Original goals list
        "list" -> ListScreen(
            goals = goals,
            onBack = {
                currentScreen = "home"
            },
            onRemove = {
                goals.remove(it)
            },
            onRemoveAll = {
                goals.clear()
            },
            modifier = modifier
        )

        // Original About screen
        "about" -> AboutScreen(
            onBack = {
                currentScreen = "home"
            },
            modifier = modifier
        )

        // Lab 22: Login before accessing interests
        "login" -> LoginScreen(
            state = loginState,
            onLogin = { email, password ->
                onLogin(email, password) {
                    currentScreen = "interests"
                }
            },
            onBack = {
                currentScreen = "home"
            },
            modifier = modifier
        )

        // Lab 22: Authenticated Supabase screen
        "interests" -> InterestsScreen(
            state = showcaseState,
            onBack = {
                currentScreen = "home"
            },
            onRefresh = onRefreshShowcase,
            modifier = modifier
        )
    }
}

// Home Screen
@Composable
fun HomeScreen(
    goals: List<String>,
    onAddGoal: (String) -> Unit,
    onSeeAll: () -> Unit,
    onAbout: () -> Unit,
    onInterestsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    var newGoal by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        // Original Momentum header
        Image(
            painter = painterResource(
                id = R.drawable.header
            ),
            contentDescription =
                "Abstract digital lines representing progress and momentum",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Goal input
        OutlinedTextField(
            value = newGoal,
            onValueChange = {
                newGoal = it.take(MAX_NAME_LENGTH)
                errorMessage = null
            },
            label = {
                Text("Goal name")
            },
            singleLine = true,
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )

        // Goal validation
        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // Character counter
        Text(
            text = "${newGoal.length} / $MAX_NAME_LENGTH",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add goal
        Button(
            onClick = {

                val problem = validateGoalName(
                    newGoal,
                    goals
                )

                if (problem == null) {
                    onAddGoal(newGoal.trim())
                    newGoal = ""
                    errorMessage = null
                } else {
                    errorMessage = problem
                }
            },
            enabled = newGoal.isNotBlank()
        ) {
            Text("Add goal")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Momentum title
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

        // Goal count
        Text(
            text = if (goals.size == 1) {
                "1 goal"
            } else {
                "${goals.size} goals"
            },
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Existing goals list button
        Button(
            onClick = onSeeAll
        ) {
            Text("See all goals")
        }

        // Existing About button
        TextButton(
            onClick = onAbout
        ) {
            Text("About")
        }

        // Lab 22: Login navigation
        Button(
            onClick = onInterestsClick
        ) {
            Text("My Interests")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Original footer
        Text(
            text = "Last updated September 2026",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// List Screen
@Composable
fun ListScreen(
    goals: List<String>,
    onBack: () -> Unit,
    onRemove: (String) -> Unit,
    onRemoveAll: () -> Unit,
    modifier: Modifier = Modifier
) {

    BackHandler {
        onBack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("Back")
        }

        Text(
            text = "All goals",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (goals.size == 1) {
                "1 goal"
            } else {
                "${goals.size} goals"
            },
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Remove all goals
        if (goals.isNotEmpty()) {
            TextButton(
                onClick = onRemoveAll
            ) {
                Text("Remove all")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (goals.isEmpty()) {

            Text(
                text = "No goals yet. Add one on the home screen.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(onClick = onBack) {
                Text("Go home")
            }

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {
                items(goals) { goal ->
                    GoalRow(
                        name = goal,
                        onRemove = {
                            onRemove(goal)
                        }
                    )
                }
            }
        }
    }
}

// Goal Row
@Composable
fun GoalRow(
    name: String,
    onRemove: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            TextButton(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}

// About Screen
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
            .fillMaxSize()
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

// Home Screen Preview
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    CampusAppTheme {
        HomeScreen(
            goals = listOf(
                "Finish assignments",
                "Workout"
            ),
            onAddGoal = {},
            onSeeAll = {},
            onAbout = {}
        )
    }
}

// Dark Mode Preview
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenDarkPreview() {

    CampusAppTheme {
        Surface {
            HomeScreen(
                goals = listOf(
                    "Finish assignments",
                    "Workout"
                ),
                onAddGoal = {},
                onSeeAll = {},
                onAbout = {}
            )
        }
    }
}

// List Screen Preview
@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {

    CampusAppTheme {
        ListScreen(
            goals = listOf(
                "Finish assignments",
                "Workout",
                "Work on personal projects",
                "Prepare for my career",
                "Improve my programming skills"
            ),
            onBack = {},
            onRemove = {},
            onRemoveAll = {}
        )
    }
}

// Empty List Preview
@Preview(showBackground = true)
@Composable
fun ListScreenEmptyPreview() {

    CampusAppTheme {
        ListScreen(
            goals = emptyList(),
            onBack = {},
            onRemove = {},
            onRemoveAll = {}
        )
    }
}

// About Screen Preview
@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {

    CampusAppTheme {
        AboutScreen(
            onBack = {}
        )
    }
}
