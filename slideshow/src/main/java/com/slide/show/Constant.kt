package com.slide.show

import android.os.Environment

/**
 * Created by d on 2016/5/11.
 * 全局变量
 */
class Constant {
    companion object {

        val SD_PATH = Environment.getExternalStorageDirectory().path!!

        const val DEFAULT_FILE_NAME = "slideshow"

        const val IMAGE_NAME = "images"
    }
}
