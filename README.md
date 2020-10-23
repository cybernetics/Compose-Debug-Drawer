# ComposeDrawer

Composable Debug Drawer for Jetpack Compose apps

<img width="250" src="art/drawer-v0.0.1-alpha-02.png" />

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
implementation 'com.github.alorma.ComposeDrawer:drawer-base:0.0.1-alpha-02'
implementation 'com.github.alorma.ComposeDrawer:drawer-modules:0.0.1-alpha-02'
```

## Setup

Wrap your content with `DebugDrawerLayout`:

```kotlin
DebugDrawerLayout(
  debug = { BuildConfig.DEBUG },
  drawerModules = {
    ...
  }
)
```

## Modules

> This library does provides only DeviceModule yet.

```kotlin
DebugDrawerLayout(
  debug = { BuildConfig.DEBUG },
  drawerModules = {
    listOf(DeviceModule())
  }
)
```

You can create your own module by creating a class that extends: `DrawerModule`

```kotlin
class DeviceModule : DebugModule {
    override val icon: IconType
    override val title: String = 

    @Composable
    override fun build() {
        ...
    }
}
```

## Theming && Customization

Update module UI:

```kotlin
DebugDrawerLayout(
    moduleModifier = Modifier
        .padding()
        .clip()
        .border(),
)
```
