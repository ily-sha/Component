package com.example.component.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAnswers: Int,
    val gameSettings: GameSettings
) : Parcelable {
    @IgnoredOnParcel
    val percentOfRightAnswer =
        if (countOfAnswers == 0) 0 else ((countOfRightAnswers / countOfAnswers.toDouble()) * 100).toInt()
}

