package com.id.todolist.ui.component;

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.todolist.domain.model.TodoModel
import com.id.todolist.ui.theme.TodoListTheme
import com.id.todolist.ui.theme.Typography


@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    data: TodoModel = TodoModel.empty(),
    onDeleteClick: (TodoModel) -> Unit = {},
    onCheckClicked: (TodoModel) -> Unit = {},
) {
    Column(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = data.todoName, style = Typography.titleLarge)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {onDeleteClick(data)}) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
            Checkbox(checked = data.isCompleted, onCheckedChange = {onCheckClicked(data)})
        }
    }
}

@Composable
@Preview
fun ShowTodoCardPreview() {
    TodoListTheme {
        TodoCard()
    }
}
