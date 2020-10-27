package com.alorma.firebase_modules

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ClipboardManagerAmbient
import androidx.compose.ui.text.AnnotatedString
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerDivider
import com.alorma.drawer_base.IconType
import com.alorma.drawer_modules.DebugModuleInfoContent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun FirebaseModule() = object : DebugModule {
    override val icon: IconType =
        IconType.Vector(drawableRes = R.drawable.ic_compose_drawer_firebase)
    override val title: String = "Firebase User"

    var user: FirebaseUser? by remember { mutableStateOf(null) }

    @Composable
    override fun build() {
        onCommit {
            user = try {
                Firebase.auth.currentUser
            } catch (e: Exception) {
                null
            }
        }

        user?.let {
            composeLoggedUser(user = it)
        } ?: composeNoLoggedUser()
    }

    @Composable
    private fun composeLoggedUser(user: FirebaseUser) {
        val items = listOfNotNull(
            "uId" to user.uid,
            user.displayName?.takeUnless { it.isEmpty() }?.let { name -> "Name" to name },
            user.email?.takeUnless { it.isEmpty() }?.let { email -> "Email" to email },
        )
        val clipboard = ClipboardManagerAmbient.current
        Column {
            items.forEachIndexed { index, item ->
                Column {
                    DebugModuleInfoContent(item.first, item.second) { _, v ->
                        clipboard.setText(AnnotatedString(text = v))
                    }
                    if (index < items.size - 1) {
                        DrawerDivider()
                    }
                }
            }
        }
    }

    @Composable
    private fun composeNoLoggedUser() {
        Text(text = "Not logged")
    }
}