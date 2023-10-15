package com.bitio.ui.product.cart.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bitio.ui.product.cart.CartItemUiState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.bitio.ui.R
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer40Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PaymentBottomSheet(
    isSheetOpen: Boolean,
    onOpenBottomSheetClick: (Boolean) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirmPaymentClick: () -> Unit,
    cart: CartItemUiState
) {
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onOpenBottomSheetClick(false)
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.checkout),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
                IconButton(onClick = {
                    onOpenBottomSheetClick(false)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.black_wallet),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.credit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.paypal),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.visa),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            TextFieldOfPayment(
                placeholder = stringResource(id = R.string.label_payment_method),
                value = value,
                onValueChange = onValueChange
            )
            VerticalSpacer16Dp()
            TextFieldOfPayment(
                placeholder = stringResource(id = R.string.label_delivery_location),
                value = value,
                onValueChange = onValueChange
            )
            VerticalSpacer40Dp()
            TextFieldOfPayment(
                placeholder = stringResource(id = R.string.label_apply_promo_code),
                value = value,
                onValueChange = onValueChange,
                imeAction = ImeAction.Done
            )
            VerticalSpacer40Dp()
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.sup_total),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = cart.subtotal.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.shipping),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$30.00",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Divider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.total_price),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = cart.total.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                TextButton(
                    onClick = onConfirmPaymentClick,
                    colors = textButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm_payment),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                VerticalSpacer32Dp()
            }
        }
    }
}

@Composable
private fun TextFieldOfPayment(
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder)
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
    )
}
