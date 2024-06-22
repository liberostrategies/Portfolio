package com.liberostrategies.pinkyportfolio.ui.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val KotlinLetterK: ImageVector
    get() {
        if (_keyboardArrowLeft != null) {
            return _keyboardArrowLeft!!
        }
        _keyboardArrowLeft = materialIcon(name = "Filled.KeyboardArrowLeft") {
            materialPath {
                moveTo(7.0f, 12.0f)
                verticalLineToRelative(14.0f)
                horizontalLineTo(23.0f)
                close()

                moveTo(7.0f, 12.0f)
                verticalLineToRelative(-14.0f)
                horizontalLineTo(23.0f)
                close()

                moveTo(7.0f, 0.0f)
                verticalLineTo(35.0f)
                horizontalLineTo(-35.0f)
                lineTo(-35.0f, 0.0f)
                close()
            }
        }
        return _keyboardArrowLeft!!
    }

private var _keyboardArrowLeft: ImageVector? = null
