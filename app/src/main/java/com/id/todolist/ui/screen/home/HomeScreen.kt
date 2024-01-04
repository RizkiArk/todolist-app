package com.id.todolist.ui.screen.home;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.id.todolist.ui.component.TodoCard
import com.id.todolist.ui.theme.TodoListTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onAddClickListener: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.value.message) {
        if (uiState.value.message.isNotEmpty()) {
            scope.launch {
                snackBarHostState.showSnackbar(uiState.value.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "ToDo List APP")})
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        floatingActionButton = {
            IconButton(onClick = onAddClickListener) {
                Icon(modifier = Modifier.size(40.dp), imageVector = Icons.Default.AddCircle, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {innerPadding ->
        if (uiState.value.isLoading) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                CircularProgressIndicator()
            }
        } else if (uiState.value.isSuccess) {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(uiState.value.listTodo) {
                    TodoCard(data = it, onDeleteClick = {data ->
                        viewModel.deleteTodo(data.id, data.todoName)
                    }, onCheckClicked = {data ->
                        viewModel.updateTodo(id = data.id, isComplete = !data.isCompleted, todoName = data.todoName)
                    })
                }
            }
        }
    }
}

@Composable
@Preview
fun ShowHomeScreenPreview() {
    TodoListTheme {
        HomeScreen()
    }
}
