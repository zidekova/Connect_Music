package com.example.connectmusic.ui.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.ConnectMusicTopAppBar
import com.example.connectmusic.R
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.home.HomeViewModel
import com.example.connectmusic.ui.navigation.NavigationDestination

import com.example.connectmusic.ui.theme.ConnectMusicTheme
import kotlinx.coroutines.launch

object SearchEntryDestination : NavigationDestination {
    override val route = "search_entry"
    override val titleRes = R.string.search_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchEntryScreen(
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNext: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMethod by remember { mutableStateOf("") }
    val options = listOf("Žáner", "Interpret", "Obdobie")

    viewModel.setSearchMethodListener(object : SearchMethodListener {
        override fun onMethodChanged(method: String) {
            selectedMethod = method
            Log.d("SearchEntryScreen", "Method changed: $method")
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Vyber spôsob vyhľadávania") })
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEach { method ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.setMethod(method)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedMethod == method,
                            onClick = {
                                viewModel.setMethod(method)
                            }
                        )
                        Text(text = method)
                    }
                }
                Button(
                    onClick = {
                        Log.d("SearchEntryScreen", "Button clicked with method: $selectedMethod")
                        onNext(selectedMethod) },
                    enabled = selectedMethod.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ďalej")
                }
            }
        }
    )
}