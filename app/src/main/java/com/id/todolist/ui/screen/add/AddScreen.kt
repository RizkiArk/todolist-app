package com.id.todolist.ui.screen.add;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.id.todolist.ui.theme.TodoListTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val todoName = remember{ mutableStateOf("") }
    val isComplete = remember{ mutableStateOf(false) }
    val viewModel: AddViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.value.message) {
        if (uiState.value.message.isNotEmpty()) {
            scope.launch {
                snackBarHostState.showSnackbar(uiState.value.message)
            }
        }
    }

    LaunchedEffect(key1 = uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            onBackClick()
            viewModel.resetField()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Add Todo")},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
    ) {innerPadding ->
        if (uiState.value.isLoading) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(text = "ToDo Name")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = todoName.value, onValueChange = {
                        todoName.value = it
                    })
                Row {
                    Text(text = "Is Complete")
                    Spacer(modifier = Modifier.weight(1f))
                    Checkbox(checked = isComplete.value, onCheckedChange = {
                        isComplete.value = it
                    })
                }
                Button(onClick = { viewModel.addTodo(
                    todoName = todoName.value, isComplete = isComplete.value)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add ToDo")
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ShowAddScreenPreview() {
    TodoListTheme {
        AddScreen()
    }
}
