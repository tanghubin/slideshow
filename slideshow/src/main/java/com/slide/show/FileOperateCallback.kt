package com.slide.show

interface FileOperateCallback {
    fun onSuccess()

    fun onFailed(error: String)
}