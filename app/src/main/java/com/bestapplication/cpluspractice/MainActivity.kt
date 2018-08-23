/*
 * Copyright (c) 2018 Yahoo! JAPAN Corporation. All rights reserved.
 */

package com.bestapplication.cpluspractice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stringFromJNI()
        printf()
    }

    external fun stringFromJNI()
    external fun printf()
}
