package com.alorma.composedrawer

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.composedrawer.modules.DemoActionsModule
import com.alorma.composedrawer.ui.ComposeDrawerTheme
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_base.ModuleExpandedState
import com.alorma.drawer_modules.BuildModule
import com.alorma.drawer_modules.DeviceModule
import com.alorma.firebase_modules.FirebaseModule
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen() {
    DebugDrawerLayout(
        isDebug = { BuildConfig.DEBUG },
        initialDrawerState = DrawerValue.Open,
        initialModulesState = ModuleExpandedState.COLLAPSED,
        drawerModules = {
            listOf(
                FirebaseModule(),
                DemoActionsModule(),
                BuildModule(),
                DeviceModule(),
            )
        }
    ) { drawerState -> AppContent(drawerState) }
}

@Composable
private fun AppContent(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = title) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
        ) {
            var user: FirebaseUser? by remember {
                mutableStateOf(Firebase.auth.currentUser)
            }
            onCommit {
                user = try {
                    Firebase.auth.currentUser
                } catch (e: Exception) {
                    null
                }
            }
            firebaseAuth(user)
            drawerButton(drawerState)
        }

    }
}

@Composable
private fun firebaseAuth(currentUser: FirebaseUser?) {
    if (currentUser == null) {
        val context = ContextAmbient.current
        Button(
            onClick = {
                val authProviders = listOf(
                    AuthUI.IdpConfig.EmailBuilder().build()
                )
                context.startActivity(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(authProviders)
                        .build()
                )
            },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Text("Add firebase user")
        }
        Spacer(modifier = Modifier.preferredHeight(16.dp))
    }
}

@Composable
private fun drawerButton(drawerState: DrawerState) {
    Button(
        onClick = {
            if (drawerState.isOpen) {
                drawerState.close()
            } else if (drawerState.isClosed) {
                drawerState.open()
            }
        }) {
        if (drawerState.isOpen) {
            Text(text = "Close DRAWER")
        } else {
            Text(text = "Open DRAWER")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ComposeDrawerTheme {
        HomeScreen()
    }
}