package com.tarif.dynamictheme.colortuple

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner

/*
@Composable
fun rememberAppColorTuple(
    defaultColorTuple: ColorTuple,
    dynamicColor: Boolean,
    darkTheme: Boolean
): ColorTuple {
    val context = LocalContext.current
    return remember(
        LocalLifecycleOwner.current.lifecycle.observeAsState().value,
        dynamicColor,
        darkTheme,
        defaultColorTuple
    ) {
        derivedStateOf {
            var colorTuple: ColorTuple = defaultColorTuple
            runCatching {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val wallColors =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                        wallpaperManager
                            .getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
                    } else null

                when {
                    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                        if (darkTheme) {
                            dynamicDarkColorScheme(context)
                        } else {
                            dynamicLightColorScheme(context)
                        }.run {
                            colorTuple = ColorTuple(
                                primary = primary,
                                secondary = secondary,
                                tertiary = tertiary,
                                surface = surface
                            )
                        }
                    }

                    dynamicColor && wallColors != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
                        colorTuple = ColorTuple(
                            primary = Color(wallColors.primaryColor.toArgb()),
                            secondary = wallColors.secondaryColor?.toArgb()?.let { Color(it) },
                            tertiary = wallColors.tertiaryColor?.toArgb()?.let { Color(it) }
                        )
                    }

                    dynamicColor && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        colorTuple = ColorTuple(
                            primary = (wallpaperManager.drawable as BitmapDrawable).bitmap.extractPrimaryColor()
                        )
                    }

                    else -> {
                        colorTuple = defaultColorTuple
                    }
                }
            }
            colorTuple
        }
    }.value
}*/
