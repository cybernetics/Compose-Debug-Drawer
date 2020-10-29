package com.alorma.navigation_module

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerColors
import com.alorma.drawer_base.IconType

@Composable
fun NavigationModule(navController: NavController) = object : DebugModule {
    override val icon: IconType = IconType.Vector(drawableRes = R.drawable.ic_compose_drawer_route)
    override val title: String = "Navigation"

    @Composable
    override fun build() {
        Column {
            navController.graph.mapNotNull { navDestination ->
                navDestination.arguments["android-support-nav:controller:route"]
            }.map { it.defaultValue as String }.forEach { argument: String ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonConstants.defaultButtonColors(
                        backgroundColor = DrawerColors.current.primary,
                        contentColor = DrawerColors.current.onPrimary,
                    ),
                    onClick = { }) {
                    Text(text = argument)
                }
            }
        }
    }

}