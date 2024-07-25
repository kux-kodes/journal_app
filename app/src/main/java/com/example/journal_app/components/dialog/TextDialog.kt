package com.example.journal_app.components.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journal_app.R

@Composable
fun TextDialog(
    modifier: Modifier = Modifier,
    isOpened: Boolean,
    onDismissCallback: () -> Unit,
    onConfirmCallback: (() -> Unit)? = null,
    //TODO: How do onDismiss and onConfirm work?
    title: String = stringResource(R.string.warning_title),
    description: String = stringResource(R.string.delete_confirmation_text),
) {
    if (isOpened) {
        //TODO: What is an alert dialog? how does it work?
        AlertDialog(
            onDismissRequest = { onDismissCallback() },
            title = {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            confirmButton = {
                if (onConfirmCallback != null) {
                    TextButton(
                        onClick = { onConfirmCallback() },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.semantics { testTag = TestTags.CONFIRM_BUTTON }
                    ) {
                        Text(
                            text = stringResource(R.string.confirm),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismissCallback() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.semantics { testTag = TestTags.DISMISS_BUTTON }
                ) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
         )
        }
    }
//TODO: Fully explain how the above text and it's pieces work