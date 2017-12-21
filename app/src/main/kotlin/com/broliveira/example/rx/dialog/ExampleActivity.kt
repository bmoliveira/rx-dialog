package com.broliveira.example.rx.dialog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.broliveira.rx.RxDialog
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_example)

    clickMeOk
        .clicks()
        .flatMap { RxDialog.dialog(this@ExampleActivity, "Title", "Message", "Ok") }
        .doOnNext { if (it) { Toast.makeText(this@ExampleActivity, "Confirmed", Toast.LENGTH_LONG).show() } }
        .subscribe()

    clickMeOkCancel
        .clicks()
        .flatMap { RxDialog.dialog(this@ExampleActivity, "Title", "Message", "Ok", "Cancel") }
        .doOnNext { if (it) { Toast.makeText(this@ExampleActivity, "Confirmed", Toast.LENGTH_LONG).show() } }
        .doOnNext { if (!it) { Toast.makeText(this@ExampleActivity, "Canceled", Toast.LENGTH_LONG).show() } }
        .subscribe()
  }
}
