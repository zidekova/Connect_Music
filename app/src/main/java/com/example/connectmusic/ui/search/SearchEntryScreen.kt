package com.example.connectmusic.ui.search

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.connectmusic.ConnectMusicTopAppBar
import com.example.connectmusic.R
import com.example.connectmusic.ui.navigation.NavigationDestination

import com.example.connectmusic.ui.theme.ConnectMusicTheme

object SearchEntryDestination : NavigationDestination {
    override val route = "search_entry"
    override val titleRes = R.string.search_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true
) {
    Scaffold(
        topBar = {
            ConnectMusicTopAppBar(
                title = stringResource(SearchEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        SearchEntryBody(
            options = listOf("Žáner", "Obdobie", "Interpret"),
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun SearchEntryBody(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        Text(stringResource(R.string.pick_method))
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            options.forEach { method ->
                Row(
                    modifier = Modifier.selectable(
                        selected = selectedValue == method,
                        onClick = {
                            selectedValue = method
                            onSelectionChanged(method)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedValue == method,
                        onClick = {
                            selectedValue = method
                            onSelectionChanged(method)
                        }
                    )
                    Text(method)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchEntryScreenPreview() {
    ConnectMusicTheme {
        SearchEntryBody(
            options = listOf(stringResource(R.string.genre), stringResource(R.string.decade), stringResource(R.string.interpret)),
            modifier = Modifier.fillMaxHeight()
        )
    }
}