package com.example.component.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.BindingAdapter
import com.example.component.R
import com.example.component.domain.entity.GameResult

@BindingAdapter("requiredAnswer")
fun bindRequiredAnswer(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score), count
    )

}

@BindingAdapter("scoreAnswers")
fun bindingScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(
            R.string.score_answers
        ), count
    )
}

@BindingAdapter("requiredPercent")
fun bindingRequiredPercent(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(
            R.string.required_percentage
        ), count
    )
}

@BindingAdapter("scorePercentage")
fun bindingScorePercentage(textView: TextView, gameResult: GameResult) {
    val countOfAnswers = gameResult.countOfAnswers
    val countOfRightAnswers = gameResult.countOfRightAnswers
    val percentOfRightAnswer =
        if (countOfAnswers == 0) 0 else ((countOfRightAnswers / countOfAnswers.toDouble()) * 100)
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage), percentOfRightAnswer
    )
}

@BindingAdapter("imageStatus")
fun bindingImageStatus(imageView: ImageView, flag: Boolean) {
    imageView.setImageDrawable(
        if (flag) getDrawable(imageView.context, R.drawable.ic_smile) else getDrawable(
            imageView.context,
            R.drawable.ic_sad
        )
    )
}