package com.slide.show

abstract class PermissionCallBack {
    abstract fun onGranted()

    open fun onDenied() {

    }
}
