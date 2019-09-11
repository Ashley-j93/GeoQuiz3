package com.bignerdranch.andriod.geoquiz

import androidx.annotation.StringRes

//class Question { }
data class Question(@StringRes val textResId: Int, val answer: Boolean)