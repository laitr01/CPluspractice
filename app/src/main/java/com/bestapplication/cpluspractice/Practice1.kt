package com.bestapplication.cpluspractice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.practice_1_activity.*
import rx.Single
import rx.SingleSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Practice1 : AppCompatActivity() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_1_activity)
        textView.text = ""

        val b1 = Single.defer({
            Single.create<String>({ singleSubscriber ->
                singleSubscriber.onSuccess(
                        sizeOfType()
                )
            }).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { typeOfSize ->
                        val text = textView.text.toString()
                        textView.text = text.plus("\n").plus(typeOfSize)
                    }
        })

        val b2 = Single.defer({
            Single.create<String>({ t ->
                t.onSuccess(usingFunctionsAndClasses())
            })
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { result ->

                    val text = textView.text.toString()
                    textView.text = text.plus("\n").plus(result)

                }

        Single.merge(b1, b2)
                .doOnSubscribe { showMessage(getString(R.string.processing_started_message)) }
                .doAfterTerminate { showMessage(getString(R.string.processing_finished_message)) }
                .subscribe()

    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    external fun sizeOfType(): String
    external fun usingFunctionsAndClasses(): String

}