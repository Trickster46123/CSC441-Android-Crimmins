
package edu.lemoyne.campusapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

class MainActivity : ComponentActivity() {

    // Existing Showcase ViewModel
    private val showcaseViewModel: ShowcaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            // Collect Supabase state
            val showcaseState by showcaseViewModel.uiState
                .collectAsStateWithLifecycle()

            CampusAppTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    CampusAppScreen(
                        showcaseState = showcaseState,
                        onRefreshShowcase = showcaseViewModel::refresh,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
