package com.example.journal_app.feature.note_creation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.journal_app.R

@OptIn(ExperimentalMaterial3Api::class)
//TODO: how do you use OptIn?
//TODO: What does ::class mean? why is it necessary?
@Composable
fun NoteCreationAppBar(
    descriptionTextLength: Int,
    onBackPressed: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
    //TODO: How does onBackPressed work?
){
    TopAppBar(title = {
        Text(text = if(descriptionTextLength > 0) "$descriptionTextLength" else
            stringResource(R.string.add_new_note),
            color = MaterialTheme.colorScheme.primary)

    })
}
