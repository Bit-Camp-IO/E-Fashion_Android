package com.bitio.ui.profile.chat

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitio.ui.R
import com.bitio.ui.shared.SharedTopAppBar
import com.bitio.ui.shared.VerticalSpacer2Dp
import com.bitio.ui.theme.textStyles.AppThemeTextStyles
import org.koin.androidx.compose.getViewModel
import kotlin.random.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatSupportScreen(
    navController: NavController
) {
    val viewModel = getViewModel<ChatSupportViewModel>()
    val state by viewModel.chatSupportUiState.collectAsState()
    ChatSupportContent(
        state,
        onClickLinkButton ={},
        viewModel::sendMessage,
        navController::popBackStack
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ChatSupportContent(
    state: ChatSupportUIState,
    onClickLinkButton: () -> Unit,
    onClickSendButton: (Sender) -> Unit,
    onClickBackButton: () -> Unit
) {
    Scaffold(
        topBar = {
            SharedTopAppBar(
                title = "Chat Support",
                onClickBackButton = onClickBackButton
            )
        }
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
                ChatSupportContainer(
                    state.chatSupport,
                    onClickLinkButton,
                    onClickSendButton
                )
            }
        }
    }
}

@Composable
private fun ChatSupportContainer(
    chatSupport: List<ChatSupport>,
    onClickLinkButton: () -> Unit,
    onClickSendButton: (Sender) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (chatSupport.isEmpty()) {
            VerticalSpacer2Dp()
            ChatSupportHint()
        } else {
            ChatSupportHeader()
            ChatsSupportBody(chatSupport, Modifier.weight(1f))
        }
        Footer(onClickLinkButton, onClickSendButton)
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
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.lock),
            contentDescription = null,
        )
        Text(
            text = "Message and calls are end-to end encrypted, no one outside of this chat can read them",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ChatsSupportBody(
    chatSupport: List<ChatSupport>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        reverseLayout = true
    ) {
        items(
            count = chatSupport.size,
            contentType = { ChatSupport::class.java },
            key = { it }
        ) { index ->
            if (chatSupport[index] is Sender) {
                SenderChats(chatSupport[index] as Sender)
            } else {
                ReceiverChats(chatSupport[index] as Receiver)
            }
        }
    }
}

@Composable
private fun SenderChats(sender: Sender) {
    CustomMessageCard(
        sender.message,
        sender.messageTime
    )
}

@Composable
private fun ReceiverChats(receiver: Receiver) {
    CustomMessageCard(
        receiver.message,
        receiver.messageTime,
        Alignment.Start,
        Color(0xFFDEE3EB),
        Color.Black,
        RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 100.dp,
            bottomEnd = 100.dp
        )
    )
}

@Composable
private fun CustomMessageCard(
    message: String,
    messageTime: String,
    alignment: Alignment.Horizontal = Alignment.End,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(
        topStart = 100.dp,
        topEnd = 30.dp,
        bottomStart = 100.dp
    )
) {
    Column(
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(shape = shape)
                .wrapContentSize()
                .background(backgroundColor)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = textColor
            )
        }
        Text(
            text = messageTime,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun Footer(
    onClickLinkButton: () -> Unit,
    onClickSendButton: (Sender) -> Unit
) {
    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChatBox(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { message = it },
            onClickLinkButton = onClickLinkButton
        )

        CustomSendButton(message.isEmpty()) {
            onClickSendButton(
                Sender(
                    id = Random(100).nextInt().toString(),
                    message = message,
                    messageTime = "11:45 PM"
                )
            )
            message = ""
        }
    }
}

@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClickLinkButton: () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        value = value,
        onValueChange = onValueChange,
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
            focusedContainerColor = Color(0xFFDEE3EB),
            unfocusedContainerColor = Color(0xBFDEE3EB),
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.None
        ),
    )
}

@Composable
private fun CustomSendButton(
    isMessageBoxEmpty: Boolean,
    onClickSendButton: () -> Unit,
) {

    val transition =
        updateTransition(targetState = isMessageBoxEmpty, label = "chatButtonTransition")
    val iconSize by transition.animateDp(
        targetValueByState = { if (it) 18.dp else 24.dp }, label = "",
        transitionSpec = {
            tween(durationMillis = 25)
        }
    )
    val iconPainter: Painter =
        painterResource(id = if (transition.currentState) R.drawable.send_outline else R.drawable.send_full)

    IconButton(
        modifier = Modifier
            .size(48.dp),
        onClick = onClickSendButton,
        enabled = !transition.currentState
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
        )
    }
}