# Debug Drawer for Jetpack Compose

Composable Debug Drawer for Jetpack Compose apps

<img width="250" src="art/drawer-v0.1.0-beta-02.png" />

## Install

Configure jitpack:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add dependencies:

```gradle
implementation 'com.github.alorma:Compose-Debug-Drawer:drawer-base:0.1.0-beta-02'
implementation 'com.github.alorma:Compose-Debug-Drawer:drawer-modules:0.1.0-beta-02'
implementation 'com.github.alorma:Compose-Debug-Drawer:developer-shprtcuts:0.1.0-beta-02'
```

## Setup

Wrap your content with `DebugDrawerLayout`:

```kotlin
DebugDrawerLayout(
  debug = { BuildConfig.DEBUG },
  drawerModules = {
    TODO()
  }

) {
  // TODO Add your APP Content here
}
```

This library automatically handles the debug / release state, so no need to remove the drawer on Release builds

## Modules

Add modules as a list of `DebugModule`s

```kotlin
DebugDrawerLayout(
  debug = { BuildConfig.DEBUG },
  drawerModules = {
    listOf(DeviceModule(), BuildModule())
  }
) {
  // TODO Add your APP Content here
}
```

#### Actions Module

This module receive a `List<DebugDrawerAction>`

<img width="160" src="art/actions_module.png" />

*Actions*

* ButtonAction: Shows a `Button` with given text, and register a lambda to receive it's click

* SwitchAction: Shows a `Switch` and register a lambda to receive it's changes

> All `DebugDrawerAction` can modify it's default UI by pass a `modifier`  

#### Build Module

Shows information about the app: Version code, Version name and Package

<img width="160" src="art/build_module.png" />

#### Device Module

Shows information about device running the app such as Device, and manufacturer

<img width="160" src="art/device_module.png" />

#### Shortcuts Module

Some quick shortcuts to open common developer tools.

> Missing any? Open an issue [here](https://github.com/alorma/Compose-Debug-Drawer/issues/new)

<img width="160" src="art/shortcuts_module.png" />

#### Custom Module
You can create your own module by creating a class that extends: `DrawerModule`

```kotlin
class UserModule : DebugModule {
    override val icon: IconType
    override val title: String = 

    @Composable
    override fun build() {
        TODO()
    }
}
```

## Theming && Customization

Use `drawerColors` to customize drawer theme colors

```kotlin
DebugDrawerLayout(
     drawerColors = YourColorScheme,
)
```

#### Custom Modules

Any module has access to `DrawerColors` and must use it to match the colors used by modules from this library 

### Modules list UI

Update module UI by pass `Modifier`

```kotlin
DebugDrawerLayout(
    moduleModifier = Modifier
        .padding()
        .clip()
        .border(),
)
```
