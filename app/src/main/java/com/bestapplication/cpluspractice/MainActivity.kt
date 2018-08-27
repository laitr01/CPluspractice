/*
 * Copyright (c) 2018 Yahoo! JAPAN Corporation. All rights reserved.
 */

package com.bestapplication.cpluspractice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_main.*
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = naGetTitle()

        val src = BitmapFactory.decodeResource(resources, R.drawable.tulips,
                BitmapFactory.Options()) as Bitmap
        iv_original.setImageBitmap(src)

        val b1 = Single.defer({
            Single.create<Bitmap>({ singleSubscriber ->
                val output = src.copy(Bitmap.Config.ARGB_8888, true)
                naToGrayscale(output)
                singleSubscriber.onSuccess(output)
            }).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { bitmap -> iv_out1.setImageBitmap(bitmap) }
        })

        val b2 = Single.defer({
            Single.create<Bitmap>({ singleSubscriber ->
                val output = src.copy(Bitmap.Config.ARGB_8888, true)
                naToBlackWhite(output)
                singleSubscriber.onSuccess(output)
            } )
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { bitmap -> iv_out2.setImageBitmap(bitmap) }
        })

        val b3 = Single.defer({
            Single.create<Bitmap>({ singleSubscriber ->
                val output = src.copy(Bitmap.Config.ARGB_8888, true)
                naSetBrightness(output, 2f)
                singleSubscriber.onSuccess(output)
            } )
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { bitmap -> iv_out3.setImageBitmap(bitmap) }
        })

        val b4 = Single.defer({
            Single.create<Bitmap>({ singleSubscriber ->
                val output = src.copy(Bitmap.Config.ARGB_8888, true)
                naSetGaussianBlur(output, output)
                singleSubscriber.onSuccess(output)
            } )
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { bitmap -> iv_out4.setImageBitmap(bitmap) }
        })

        Single.merge(b1, b2, b3, b4)
                .doOnSubscribe { showMessage(getString(R.string.processing_started_message))  }
                .doAfterTerminate {  showMessage(getString(R.string.processing_finished_message)) }
                .subscribe()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    /**
     * Native methods that are implemented by the native library, which are packaged with the
     * application.
     */
    external fun naGetTitle(): String

    external fun naSetGaussianBlur(bitmap: Bitmap, output: Bitmap)
    external fun naSetBrightness(bitmap: Bitmap, brightnessValue: Float)
    external fun naRemoveBlueColor(bitmap: Bitmap)
    external fun naToBlackWhite(bitmap: Bitmap)
    external fun naToGrayscale(bitmap: Bitmap)
}
