package com.broliveira.rx

import android.app.AlertDialog
import android.content.Context
import android.support.annotation.StringRes
import io.reactivex.Observable

class RxDialog {
  companion object {
    fun dialog(
        context: Context?,
        @StringRes titleResId: Int,
        @StringRes messageResId: Int,
        @StringRes positiveButtonMessageResId: Int?,
        @StringRes negativeButtonMessageResId: Int?
    ): Observable<Boolean>
        = dialog(
        context = context,
        title = context?.getString(titleResId) ?: "",
        message = context?.getString(messageResId) ?: "",
        positiveButtonMessage = positiveButtonMessageResId?.let { context?.getString(positiveButtonMessageResId) },
        negativeButtonMessage = negativeButtonMessageResId?.let { context?.getString(negativeButtonMessageResId) }
    )

    fun dialog(
        context: Context?,
        title: String,
        message: String,
        positiveButtonMessage: String? = null,
        negativeButtonMessage: String? = null
    ): Observable<Boolean>
        = Observable.create { subscriber ->
      AlertDialog.Builder(context)
          .setTitle(title)
          .setMessage(message)
          .apply {
            if (positiveButtonMessage != null) {
              setPositiveButton(positiveButtonMessage, { _, _ ->
                subscriber.onNext(true)
                subscriber.onComplete()
              })
            }
          }
          .apply {
            if (negativeButtonMessage != null) {
              setNegativeButton(negativeButtonMessage, { _, _ ->
                subscriber.onNext(false)
                subscriber.onComplete()
              })
            }
          }
          .create()
          .also {
            it.setOnDismissListener {
              subscriber.onNext(false)
              subscriber.onComplete()
            }
            it.show()
          }
    }
  }
}
