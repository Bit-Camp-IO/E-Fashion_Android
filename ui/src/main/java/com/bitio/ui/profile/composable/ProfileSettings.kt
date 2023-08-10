package com.bitio.ui.profile.composable

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import com.bitio.ui.shared.VerticalSpacer32Dp


@Composable
fun ProfileSettings(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    onSwitchTheme: () -> Unit,
    onClickMyProfile: () -> Unit,
) {
    ProfileUserContent(
        isDarkTheme,
        modifier,
        onSwitchTheme,
        onClickMyProfile
    )
}

@Composable
private fun ProfileUserContent(
    isDarkTheme: Boolean,
    modifier: Modifier,
    onSwitchTheme: () -> Unit,
    onClickMyProfile: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .drawBehind {},
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                tint = Porcelain
            )
        }

        Text(
            text = "Profile Settings",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).titleMedium,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        VerticalSpacer32Dp()

        ProfileSettingItem(
            startPainterIcon = painterResource(id = R.drawable.profile),
            text = "My Profile",
            modifier = Modifier.clickable(onClick = onClickMyProfile)
        )

        ProfileSettingItem(
            startPainterIcon = painterResource(id = R.drawable.location),
            text = "Location",
        )
        ProfileSettingItem(
            startPainterIcon = painterResource(id = R.drawable.bag),
            text = "Order status",
        )
        ProfileSettingItem(
            startPainterIcon = painterResource(id = R.drawable.messages),
            text = "Chat Support",
        )
        ProfileSettingItem(
            startPainterIcon = painterResource(id = R.drawable.notification),
            text = "Notifications",
        )

        ContainerThemeSwitcher(
            startPainterIcon = painterResource(id = R.drawable.sun),
            text = "Dark Theme",
            isDarkTheme,
            onSwitchTheme
        )
    }
}


@Composable
private fun ProfileSettingItem(
    startPainterIcon: Painter,
    text: String,
    endPainterIcon: Painter = painterResource(id = R.drawable.arraw_right),
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp),
    ) {
        Icon(painter = startPainterIcon, contentDescription = "")
        Spacer(modifier = Modifier.width(16.dp))
        Title(
            text = text,
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodySmall,
            modifier = Modifier.weight(1f)
        )
        Icon(painter = endPainterIcon, contentDescription = "")
    }
}


@Composable
private fun ContainerThemeSwitcher(
    startPainterIcon: Painter,
    text: String,
    isDarkTheme: Boolean,
    onSwitchTheme: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = startPainterIcon, contentDescription = "")
        Spacer(modifier = Modifier.width(16.dp))
        Title(
            text = text,
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodySmall,
            modifier = Modifier.weight(1f)
        )
        ThemeSwitcher(isDarkTheme = isDarkTheme, onSwitchTheme = onSwitchTheme)
    }
}

@Composable
private fun ThemeSwitcher(
    isDarkTheme: Boolean,
    size: Dp = 32.dp,
    padding: Dp = 2.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 100),
    onSwitchTheme: () -> Unit
) {
    val offsetThumb by animateDpAsState(
        targetValue = if (isDarkTheme) 0.dp else size,
        animationSpec = animationSpec, label = ""
    )

    val offsetIcon by animateDpAsState(
        targetValue = if (isDarkTheme) (-120).dp else 0.dp,
        animationSpec = animationSpec, label = ""
    )

    Box(modifier = Modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable { onSwitchTheme() }
        .background(MaterialTheme.colorScheme.onBackground)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offsetThumb)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.background)
        ) {}
        Row {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = offsetIcon),
                    painter = painterResource(id = R.drawable.moon_half),
                    contentDescription = "Dark theme",
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
                    painter = painterResource(id = R.drawable.sun_full),
                    contentDescription = "Light theme",
                    tint = MaterialTheme.colorScheme.background,
                )
            }
        }
    }
}

private fun DrawScope.drawHalfTriangle(screenHeight: Float) {
    val path = Path()
    path.moveTo(0f, 0f)
    path.lineTo(0f, size.height)
    path.lineTo(size.width, size.height)
    path.lineTo(size.width, size.height / 2f)
    path.close()

    drawPath(
        path = path,
        color = Color.Green,
        style = Stroke(
            width = 100f,
            pathEffect = PathEffect.cornerPathEffect(50f),
        ),
    )
}






