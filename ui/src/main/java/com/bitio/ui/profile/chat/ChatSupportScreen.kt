package com.bitio.ui.profile.chat

import android.annotation.SuppressLint
import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitio.ui.R
import com.bitio.ui.shared.SharedTopAppBar
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer2Dp
import com.bitio.ui.shared.VerticalSpacer64Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.textStyles.AppThemeTextStyles
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatSupportScreen(
    navController: NavController
) {
    val viewModel = getViewModel<ChatSupportViewModel>()
    val state by viewModel.chatSupportUiState.collectAsState()
    ChatSupportContent(state)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ChatSupportContent(state: ChatSupportUIState) {
    Scaffold(
        topBar = { SharedTopAppBar(title = "Chat Support") {} }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.errorMessage.isNotBlank()) {
                Text(text = state.errorMessage)
            } else {
                ChatSupportContainer(state.chatSupport)
            }
        }
    }
}

@Composable
private fun ChatSupportContainer(chatSupport: List<ChatSupport>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        VerticalSpacer2Dp()
        if (chatSupport.isEmpty()) {
            ChatSupportHint()
        } else {
            ChatSupportHeader()
            ChatsSupportBody(chatSupport)
        }
        Footer({}, {})
    }
}

@Composable
private fun ChatSupportHint() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.chat_support),
            contentDescription = null
        )
        Text(
            text = "Feel free to tell us how to help you",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ChatSupportHeader() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(painter = painterResource(id = R.drawable.lock), contentDescription = null)
        Text(
            text = "Message and calls are end-to end encrypted, no one outside of this chat can read them",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ChatsSupportBody(chatSupport: List<ChatSupport>) {
    chatSupport.forEach {
        if (it is Sender) {
            SenderChats(it)
        } else {
            ReceiverChats(it as Receiver)
        }
    }
}

@Composable
private fun SenderChats(sender: Sender) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .size(sender.message.length.dp)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sender.message,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
        Text(
            text = sender.messageTime,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun ReceiverChats(receiver: Receiver) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .size(receiver.message.length.dp)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = receiver.message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = receiver.messageTime,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun Footer(
    onClickLinkButton: () -> Unit,
    onClickSendButton: () -> Unit
) {
    var chatMessage by remember {
        mutableStateOf("")
    }

    val transition =
        updateTransition(targetState = chatMessage.isNotEmpty(), label = "chatButtonTransition")
    val iconSize by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300) },
        targetValueByState = { if (it) 24.dp else 18.dp }, label = ""
    )
    val iconPainter: Painter =
        painterResource(id = if (chatMessage.isEmpty()) R.drawable.send_outline else R.drawable.send_full)


    Row(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChatBox(
                modifier = Modifier.weight(1f),
                chatMessage,
                onClickLinkButton,
                onValueChange = { chatMessage = it }
            )
            IconButton(
                modifier = Modifier
                    .size(48.dp),
                onClick = onClickSendButton
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize),
                )
            }
        }
    }
}

@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    value: String,
    onClickLinkButton: () -> Unit,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        value = value, onValueChange = onValueChange,
        textStyle = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodySmall,
        placeholder = {
            Text(
                text = "Message",
                style = MaterialTheme.typography.bodySmall
            )
        },
        trailingIcon = {
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = onClickLinkButton
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.link),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color(0xFFC2C8D1),
            unfocusedContainerColor = Color(0xFFDEE3EB),
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
    )
}