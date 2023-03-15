package com.example.component.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@kotlinx.parcelize.Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeSettings: Int
    ):  Parcelable {

}