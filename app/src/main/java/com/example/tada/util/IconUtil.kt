package com.example.tada.util

import com.example.tada.ui.theme.icons
import kotlin.math.abs

fun getMatchingIcon(categoryId: String): Int = icons[abs(categoryId.hashCode() % icons.size)]
