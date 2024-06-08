package com.example.connectmusic.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.data.tables.Song
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object SearchDestination : NavigationDestination {
    override val route = "search_entry"
    override val titleRes = R.string.search_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navigateToSongDetail: (Int) -> Unit,
    //onSearch: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMethod by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isMethodDropdownExpanded by remember { mutableStateOf(false) }
    var isOptionDropdownExpanded by remember { mutableStateOf(false) }

    val methods = listOf("Žáner", "Interpret", "Obdobie")

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_entry_title),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dropdown pro výběr metody
                ExposedDropdownMenuBox(
                    expanded = isMethodDropdownExpanded,
                    onExpandedChange = { isMethodDropdownExpanded = !isMethodDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedMethod,
                        onValueChange = { selectedMethod = it },
                        readOnly = true,
                        label = { Text("Vyber metódu") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isMethodDropdownExpanded
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isMethodDropdownExpanded,
                        onDismissRequest = { isMethodDropdownExpanded = false }
                    ) {
                        methods.forEach { method ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedMethod = method
                                    selectedOption = ""
                                    isMethodDropdownExpanded = false
                                    isOptionDropdownExpanded = false
                                    searchViewModel.loadDataForMethod(selectedMethod)
                                },
                                text = { Text(method, color = MaterialTheme.colorScheme.onSurface) }
                            )
                        }
                    }
                }

                // Dropdown pro výběr možnosti
                val options by searchViewModel.data.collectAsState(initial = emptyList())

                ExposedDropdownMenuBox(
                    expanded = isOptionDropdownExpanded,
                    onExpandedChange = { isOptionDropdownExpanded = !isOptionDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedOption,
                        onValueChange = { selectedOption = it },
                        readOnly = true,
                        label = { Text("Vyber $selectedMethod") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isOptionDropdownExpanded
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        enabled = selectedMethod.isNotEmpty()
                    )
                    ExposedDropdownMenu(
                        expanded = isOptionDropdownExpanded && selectedMethod.isNotEmpty(),
                        onDismissRequest = { isOptionDropdownExpanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOption = option
                                    isOptionDropdownExpanded = false
                                },
                                text = { Text(option, color = MaterialTheme.colorScheme.onSurface) }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (selectedMethod.isNotBlank() && selectedOption.isNotBlank()) {
                            coroutineScope.launch {
                                val randomSong = searchViewModel.getRandomSong(selectedMethod, selectedOption)
                                randomSong?.let {
                                    searchViewModel.setRandomSong(it)
                                    navigateToSongDetail(it.id_song)
                                }
                            }
                        }
                    },
                    enabled = selectedMethod.isNotBlank() && selectedOption.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vyhľadať")
                }
            }
        }
    )
}
