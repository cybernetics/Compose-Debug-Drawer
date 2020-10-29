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
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_base.DrawerColors
import com.alorma.drawer_base.IconType

@Composable
fun NavigationModule(
    navController: NavController,
    stringParam: (route: String, paramName: String) -> String = { _, paramName -> "{$paramName}" },
    intParam: (route: String, paramName: String) -> Int = { _, _ -> 0 },
) = object : DebugModule {
    override val icon: IconType = IconType.Vector(drawableRes = R.drawable.ic_compose_drawer_route)
    override val title: String = "Navigation"

    @Composable
    override fun build() {
        Column {
            navController.graph.mapNotNull { navDestination ->
                val routeName = navDestination.arguments["android-support-nav:controller:route"]
                    ?.defaultValue as? String

                if (routeName == null) {
                    null
                } else {
                    val arguments = navDestination.arguments.toMutableMap()
                    arguments.remove("android-support-nav:controller:route")

                    NavigationItem(
                        route = routeName,
                        arguments = arguments
                            .filter { !it.value.isNullable }
                            .map { NavigationArgument(it.key, it.value) },
                        optionalArguments = arguments
                            .filter { it.value.isNullable }
                            .map { NavigationArgument(it.key, it.value) }
                    )
                }
            }.forEach { item: NavigationItem ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonConstants.defaultButtonColors(
                        backgroundColor = DrawerColors.current.primary,
                        contentColor = DrawerColors.current.onPrimary,
                    ),
                    onClick = {
                        if (item.arguments.isEmpty()) {
                            navigateWithoutParams(item)
                        } else {
                            navigateWithParams(item)
                        }
                    }
                ) {
                    Text(text = item.route)
                }
            }
        }
    }

    private fun navigateWithoutParams(item: NavigationItem) {
        navController.navigate(route = item.route)
    }

    private fun navigateWithParams(item: NavigationItem) {
        var route = item.route
        item.arguments.forEach { argument: NavigationArgument ->
            route = route.replace(
                oldValue = "{${argument.name}}",
                newValue = mapArgumentValue(item.route, argument)
            )
        }
        navController.navigate(route = route)
    }

    private fun mapArgumentValue(
        routeName: String,
        argument: NavigationArgument,
    ): String = when (argument.argument.type.name) {
        "string" -> mapString(argument, routeName)
        "integer" -> mapInteger(argument, routeName)
        else -> ""
    }

    private fun mapString(
        argument: NavigationArgument,
        routeName: String
    ): String {
        return if (argument.argument.isDefaultValuePresent) {
            argument.argument.defaultValue as String
        } else {
            stringParam(routeName, argument.name)
        }
    }

    private fun mapInteger(
        argument: NavigationArgument,
        routeName: String
    ): String {
        return if (argument.argument.isDefaultValuePresent) {
            (argument.argument.defaultValue as Int).toString()
        } else {
            intParam(routeName, argument.name).toString()
        }
    }
}

private data class NavigationItem(
    val route: String,
    val arguments: List<NavigationArgument> = emptyList(),
    val optionalArguments: List<NavigationArgument> = emptyList()
)

private data class NavigationArgument(
    val name: String,
    val argument: NavArgument
)