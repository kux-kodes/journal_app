package com.example.journal_app.feature.note_creation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.journal_app.R
import com.example.journal_app.data.local.NoteModel
import com.example.journal_app.feature.note_creation.view_model.NoteCreationPageBaseVM
import com.example.journal_app.feature.note_creation.view_model.NoteCreationPageVM
import kotlinx.coroutines.launch

const val TAG: String = "NoteCreationPage"
@ExperimentalMaterial3Api
@Composable
fun NoteCreationPage(navHostController: NavHostController,
                     viewModel: NoteCreationPageBaseVM = hiltViewModel<NoteCreationPageVM>()){
    val titleTextField = "${stringResource(R.string.title_textField)}-${TAG}}"
    val bodyTextField = "${stringResource(R.string.body_textField)}-${TAG}}"
    val titleInput = stringResource(R.string.title_textField_input)
    val bodyInput = stringResource(R.string.body_textField_input)
    val length = viewModel.descriptionText.value.length
    val scope = rememberCoroutineScope()
    val requiredDialogState= remember{mutableStateOf(false)}
    val cancelDialogState= remember{mutableStateOf(false)}
    val snackBarHostState= remember{SnackbarHostState()}
    fun addNote(){
        if(viewModel.titleText.value.isEmpty()|| viewModel.descriptionText.value.isEmpty()){
            requiredDialogState.value = true
            //TODO: why are we setting the value to true?
        } else{
            scope.launch {
                viewModel.addNote(NoteModel(
                    title = viewModel.titleText.value ,
                    note = viewModel.descriptionText.value
                )).onSuccess { navHostController.popBackStack()
                snackBarHostState.showSnackbar(
                    message = "note successfully added" ,
                    withDismissAction = true
                )}
                    .onFailure {  snackBarHostState.showSnackbar(
                        message = it.message ?: "",
                        withDismissAction = true
                    )}
                //TODO: Explain the above code piece
            }
        }
    }
    val appBarState = rememberTopAppBarState()
//TODO: Resolve top bar error and explain why it happened
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val remerberedScrollBehavior = remember {scrollBehavior}
    val view = LocalView.current
    val keyboardHeight = remember {mutableStateOf(0.dp)}
    val viewTreeObserver = remember {view.viewTreeObserver}
    val onGlobalLayoutListener = remember {
        val rect = android.graphics.Rect().apply {
            view.getWindowVisibleDisplayFrame(this)
        }
//TODO: fix errors
    }
}

