package com.id.todolist.ui.screen;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.todolist.ui.screen.add.AddScreen
import com.id.todolist.ui.screen.home.HomeScreen
import com.id.todolist.ui.screen.home.HomeViewModel
import com.id.todolist.ui.theme.TodoListTheme

enum class UIContainer {
    HOME, ADD
}

@Composable
fun UIContainer(
    modifier: Modifier = Modifier
) {
    val currentNav = remember{ mutableStateOf(UIContainer.HOME) }

    val homeViewModel: HomeViewModel = hiltViewModel()

    Column(modifier = modifier.fillMaxSize()) {
        when (currentNav.value) {
            UIContainer.HOME -> HomeScreen(viewModel = homeViewModel, onAddClickListener = {
                currentNav.value = UIContainer.ADD
            })
            UIContainer.ADD -> AddScreen(onBackClick = {
                currentNav.value = UIContainer.HOME
                homeViewModel.fetchTodos()
            })
        }
    }
}

@Composable
@Preview
fun ShowUIContainerPreview() {
    TodoListTheme {
        UIContainer()
    }
}
