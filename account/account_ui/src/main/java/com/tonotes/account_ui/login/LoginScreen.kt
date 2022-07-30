package com.tonotes.account_ui.login

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonotes.core_ui.R
import com.tonotes.core_ui.UIState
import com.tonotes.core_ui.component.FullSizeProgressBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val onEvent = loginViewModel::onEvent
    val loginState = loginViewModel.loginState
    val username = loginViewModel.username
    val password = loginViewModel.password
    val passwordVisibility = loginViewModel.passwordVisibility

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    (context as Activity).window
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 4.dp
                ),
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back icon"
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(60.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = { onEvent(LoginEvent.OnUsernameChanged(it)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Username icon"
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.username))
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Password icon"
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.password))
                    },
                    trailingIcon = {
                        IconButton(onClick = { onEvent(LoginEvent.OnPasswordVisibilityChanged) }) {
                            Icon(
                                imageVector = if (passwordVisibility) {
                                    Icons.Outlined.VisibilityOff
                                } else {
                                    Icons.Outlined.Visibility
                                },
                                contentDescription = "Password visibility icon"
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            onEvent(LoginEvent.LogIn)
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    context.resources.getString(R.string.fill_the_form)
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.have_no_account))
                        append(" ")

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append(stringResource(id = R.string.register_here))
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    onClick = { onNavigateToRegister() }
                )
            }
        }

        // Observe login state
        when (loginState) {
            is UIState.Loading -> {
                FullSizeProgressBar()
            }

            is UIState.Success -> {
                onNavigateToHome()
            }

            is UIState.Fail -> {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        loginState.message?.let {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        loginState.message?.let {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
            }

            else -> {}
        }
    }
}