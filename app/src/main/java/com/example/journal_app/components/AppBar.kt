package com.example.journal_app.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.journal_app.R

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailAppBar(
    isEditing: Boolean,
    descriptionTextLength: Int,
    onBackPressed: () -> Unit,
    onClosePressed: () -> Unit,
    onDeletePressed: () -> Unit,
    onSharePressed: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior){
    TopAppBar(
        title = {
            Text(
                text = if (descriptionTextLength > 0) "$descriptionTextLength" else
                    stringResource(id = R.string.add_new_note),
                color = MaterialTheme.colorScheme.primary,
                //modifier = Modifier.padding(start = 4.dp),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        /*
        navigationIcon = {
            TopNavBarIcon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back_nav_icon), Modifier.semantics {  }) {
                onBackPressed())
        } */
        scrollBehavior = scrollBehavior,
       // modifier = Modifier.semantics{ },
    )
}