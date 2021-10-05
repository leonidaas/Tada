package de.leonfuessner.tada.ui.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun OverviewMoreDropdown(
    expanded: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(onClick = onDeleteClick) {
            Text("Delete")
        }
    }
}

@Composable
fun DetailDropdown(
    expanded: Boolean,
    onDoneRemoveClick: () -> Unit,
    onRemoveAllClick: () -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            onClick = { onDoneRemoveClick() }
        ) {
            Text("Remove finished tasks")
        }
        DropdownMenuItem(
            onClick = { onRemoveAllClick() }
        ) {
            Text("Clear all tasks")
        }
    }
}


