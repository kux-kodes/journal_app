package com.example.journal_app.feature.note_details

import android.view.ViewTreeObserver
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.journal_app.R
import com.example.journal_app.components.NoteDetailAppBar
import com.example.journal_app.feature.note_details.viewmodel.NoteDetailsBaseVM
import com.example.journal_app.feature.note_details.viewmodel.NoteDetailsVM
import kotlinx.coroutines.launch

const val TAG: String = "NoteDetailsPage"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsPage(navHostController: NavHostController,
                    viewModel: NoteDetailsBaseVM = hiltViewModel<NoteDetailsVM>(),
                    id: String){
//TODO:Add screen lock rotation
    val titleTextField = "${stringResource(R.string.title_textField)}-$TAG"
    val bodyTextField = "${stringResource(R.string.body_textField)}-$TAG"
    //TODO: why the string template tag?
    val titleInput = stringResource(R.string.title_textField_input)
    val bodyInput = stringResource(R.string.body_textField_input)
    val noteDetailState = viewModel.noteDetail.observeAsState()
    //TODO: How does observe as state work? What is the purpose of this variable?
    val data = noteDetailState.value?.getOrNull()
    val isEditingState = viewModel.isEditing.value
    val focusManager= LocalFocusManager.current
    //TODO: How does this work?
    val loadingState = viewModel.loader.observeAsState()
    //TODO: What is the purpose of this variable?
    val scope = rememberCoroutineScope()
    val requiredDialogState = remember { mutableStateOf(false) }
    val deleteDialogState = remember { mutableStateOf(false) }
    val cancelDialogState = remember { mutableStateOf(false) }
    val openLoadingDialog = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    fun initData(){
        viewModel.descriptionText.value = data?.note ?: ""
        viewModel.textTitle.value = data?.title ?: ""
        //TODO: is this a ternary condition? How does it work?
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getNoteDetail(id.toInt())
    }
    
    LaunchedEffect(key1 = noteDetailState.value) {
        initData()
    }
    
    LaunchedEffect(key1 = isEditingState) {
        if(!isEditingState){
            focusManager.clearFocus()
        }
    }
    
    LaunchedEffect(key1 = loadingState.value) {
        openLoadingDialog.value = loadingState.value ==true
    }
//TODO: What is the point of using a launched effect? How does each launch effect work?
    fun deleteNote(){
        if(data!= null){
            scope.launch {
                viewModel.deleteNote(data)
                    .onSuccess {
                        deleteDialogState.value = false
                        //TODO: Why is the value being set to false?
                        navHostController.popBackStack()
                    }
                    .onFailure {
                        deleteDialogState.value = false
                        //TODO: Why is it still false?
                        snackBarHostState.showSnackbar(
                            message = it.message?: "",
                            withDismissAction = true
                        )
                    }
            }
        }
    }
    fun updateNote(){
        if (viewModel.textTitle.value.isEmpty() || viewModel.descriptionText.value.isEmpty()) {
                requiredDialogState.value = true }
            else {
                if(data != null){
                    focusManager.clearFocus()
                    scope.launch {
                        val updatedNote = data.copy(title = viewModel.textTitle.value
                        , note = viewModel.descriptionText.value)
                        viewModel.updateNote(updatedNote)
                            .onSuccess {
                                viewModel.isEditing.value = false
                                //TODO: Why?
                                snackBarHostState.showSnackbar(
                                    message = "Note successfully updated",
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
    } //TODO: Fully explain the update note function
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val rememberedScrollBehaviour = remember {
        scrollBehavior
    }
    val view = LocalView.current
    val keyboardHeight = remember{ mutableStateOf(0.dp) }
    val viewTreeObserver = remember { view.viewTreeObserver }
    val onGlobalLayoutListener = remember {
        ViewTreeObserver.OnGlobalLayoutListener{
            val rect = android.graphics.Rect().apply {
                view.getWindowVisibleDisplayFrame(this)
            }
            val keyboardHeightNew = view.rootView.height - rect.bottom
            if(keyboardHeightNew.dp != keyboardHeight.value){
                keyboardHeight.value = keyboardHeightNew.dp
            }
        }
    }
    DisposableEffect(view){
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        }
    }
    Scaffold(
        topBar = {
            NoteDetailAppBar()
        }
    ){}
    }



    //TODO: Why do we have to use remember {scroll behaviour} and define scroll behaviour?

}
