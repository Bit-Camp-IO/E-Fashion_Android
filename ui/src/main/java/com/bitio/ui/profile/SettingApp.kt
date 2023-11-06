package com.bitio.ui.profile

import android.annotation.SuppressLint
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer8Dp


@Composable
fun SettingApp(
    isDarkModeEnabled: Boolean,
    isNotificationEnabled: Boolean,
    modifier: Modifier = Modifier,
    onSwitchTheme: (Boolean) -> Unit,
    onSwitchNotification: (Boolean) -> Unit,
    onMyProfileClicked: () -> Unit,
    onLocationScreenClicked: () -> Unit,
    onOrderStatusScreenClicked: () -> Unit,
    onChatSupportScreenClicked: () -> Unit,
    onChangePasswordScreenClicked: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        SettingItem(
            startPainterIcon = painterResource(id = R.drawable.profile),
            text = stringResource(id = R.string.my_profile),
            modifier = Modifier.clickable(onClick = onMyProfileClicked)
        )
        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color(0x406F6F6F)
        )

        SettingItem(
            startPainterIcon = painterResource(id = R.drawable.location),
            text = stringResource(id = R.string.location),
            modifier = Modifier.clickable(onClick = onLocationScreenClicked)

        )
        SettingItem(
            startPainterIcon = painterResource(id = R.drawable.bag),
            text = stringResource(id = R.string.order_status),
            modifier = Modifier.clickable(onClick = onOrderStatusScreenClicked)
        )

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color(0x406F6F6F)
        )
        SettingItem(
            startPainterIcon = painterResource(id = R.drawable.messages),
            text = stringResource(id = R.string.chat_support),
            modifier = Modifier.clickable(onClick = onChatSupportScreenClicked)
        )
        SettingItem(
            startPainterIcon = painterResource(id = R.drawable.large_lock),
            text = stringResource(id = R.string.change_password),
            modifier = Modifier.clickable(onClick = onChangePasswordScreenClicked)
        )

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color(0x406F6F6F)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.notification), contentDescription = "")
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.notifications),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            Switcher(
                enabled = isNotificationEnabled,
                onSwitch = onSwitchNotification,
                enableIcon = painterResource(id = R.drawable.outline_notifications_off_24),
                disableIcon = painterResource(id = R.drawable.notification),
                enableColor = Color.Green,
                disableColor = Color.Red
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.sun), contentDescription = "")
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = if (isDarkModeEnabled) R.string.dark_mode else R.string.light_mode),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            Switcher(
                enabled = isDarkModeEnabled,
                onSwitch = onSwitchTheme,
                enableIcon = painterResource(id = R.drawable.moon_half),
                disableIcon = painterResource(id = R.drawable.sun_full),
            )
        }

    }
}


@Composable
private fun SettingItem(
    startPainterIcon: Painter,
    text: String,
    endPainterIcon: Painter = painterResource(id = R.drawable.arraw_right),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .fillMaxWidth(),
    ) {
        Icon(painter = startPainterIcon, contentDescription = "")
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(painter = endPainterIcon, contentDescription = "")
    }
}


@Composable
private fun Switcher(
    enabled: Boolean,
    size: Dp = 32.dp,
    padding: Dp = 2.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 100),
    onSwitch: (Boolean) -> Unit,
    enableIcon: Painter,
    disableIcon: Painter,
    enableColor: Color = MaterialTheme.colorScheme.onBackground,
    disableColor: Color = MaterialTheme.colorScheme.onBackground
) {
    var isEnable by remember {
        mutableStateOf(enabled)
    }
    val offsetThumb by animateDpAsState(
        targetValue = if (enabled) 0.dp else size,
        animationSpec = animationSpec, label = ""
    )

    val offsetIcon by animateDpAsState(
        targetValue = if (enabled) (-120).dp else 0.dp,
        animationSpec = animationSpec, label = ""
    )

    Box(modifier = Modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable {
            isEnable = !isEnable
            onSwitch(isEnable)
        }
        .background(if (enabled) enableColor else disableColor)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offsetThumb)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.background)
        )
        Row {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = offsetIcon),
                    painter = enableIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = offsetThumb + offsetThumb),
                    painter = disableIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                )
            }
        }
    }
}
