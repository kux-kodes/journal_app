package com.example.journal_app.feature.note_creation

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.journal_app.R
import com.example.journal_app.commons.TestTags
import com.example.journal_app.components.dialog.TextDialog
import com.example.journal_app.data.local.NoteModel
import com.example.journal_app.feature.note_creation.components.NoteCreationAppBar
import com.example.journal_app.feature.note_creation.view_model.NoteCreationPageBaseVM
import com.example.journal_app.feature.note_creation.view_model.NoteCreationPageMockVM
import com.example.journal_app.feature.note_creation.view_model.NoteCreationPageVM
import kotlinx.coroutines.launch

const val TAG: String = "NoteCreationPage"

@ExperimentalMaterial3Api
@Composable
fun NoteCreationPage(
    navHostController: NavHostController,
    viewModel: NoteCreationPageBaseVM = hiltViewModel<NoteCreationPageVM>()
) {
    val titleTextField = "${stringResource(R.string.title_textField)}-${TAG}}"
    val bodyTextField = "${stringResource(R.string.body_textField)}-${TAG}}"
    val titleInput = stringResource(R.string.title_textField_input)
    val bodyInput = stringResource(R.string.body_textField_input)
    val length = viewModel.descriptionText.value.length
    val scope = rememberCoroutineScope()
    val requiredDialogState = remember { mutableStateOf(false) }
    val cancelDialogState = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    fun addNote() {
        if (viewModel.titleText.value.isEmpty() || viewModel.descriptionText.value.isEmpty()) {
            requiredDialogState.value = true
        } else {
            scope.launch {
                viewModel.addNote(
                    NoteModel(
                        title = viewModel.titleText.value,
                        note = viewModel.descriptionText.value
                    )
                ).onSuccess {
                    navHostController.popBackStack()
                    snackBarHostState.showSnackbar(
                        message = "note successfully added",
                        withDismissAction = true
                    )
                }
                    .onFailure {
                        snackBarHostState.showSnackbar(
                            message = it.message ?: "",
                            withDismissAction = true
                        )
                    }
            }
        }
    }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val rememberedScrollBehavior = remember { scrollBehavior }
    val view = LocalView.current
    val keyboardHeight = remember { mutableStateOf(0.dp) }
    val viewTreeObserver = remember { view.viewTreeObserver }
    val onGlobalLayoutListener = remember {
        ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect().apply {
                view.getWindowVisibleDisplayFrame(this)
            }
            val keyboardHeightNew = view.rootView.height - rect.bottom
            if (keyboardHeightNew.dp != keyboardHeight.value) {
                keyboardHeight.value = keyboardHeightNew.dp
            }
        }
    }
    DisposableEffect(view) {
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        }
    }
    Scaffold(
        topBar = {
            NoteCreationAppBar(descriptionTextLength = length,
                onBackPressed = { cancelDialogState.value = true },
                scrollBehavior = rememberedScrollBehavior)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { addNote() },
                modifier = Modifier.semantics { testTag = "add-note-fab" },
                text = {
                    Text(
                        stringResource(R.string.save),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                icon = {},
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.primary

            )
        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = keyboardHeight.value)
                ) {
                    TextField(value = viewModel.titleText.value,
                        onValueChange = { viewModel.titleText.value = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        singleLine = false,
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = titleTextField
                            },
                        placeholder = {
                            Text(
                                text = titleInput,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }

        },
        modifier= Modifier
            .semantics { testTag = TestTags.NOTE_CREATION_PAGE }
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface
    )
    val missingFieldName = if(viewModel.titleText.value.isEmpty()){
        "Title"
    } else if(viewModel.descriptionText.value.isEmpty()){
        "Description"
    } else{
        ""
    }
    TextDialog(
        title = stringResource(R.string.required_title),
        description = stringResource(R.string.required_confirmation_text, missingFieldName),
        isOpened = requiredDialogState.value,
        onDismissCallback = { requiredDialogState.value = false },
        onConfirmCallback = { requiredDialogState.value = false })

    TextDialog(
        title = stringResource(R.string.cancel_title),
        description = stringResource(R.string.cancel_confirmation_text),
        isOpened = cancelDialogState.value,
        onDismissCallback = { cancelDialogState.value = false },
        onConfirmCallback = {
            navHostController.popBackStack()
            cancelDialogState.value = false
        })
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NoteCreationPreview(){
    NoteCreationPage(
        navHostController = rememberNavController(),
        viewModel = NoteCreationPageMockVM()

    )
}

