package com.slide.show

import android.support.v7.app.AppCompatActivity
import com.yanzhenjie.permission.AndPermission

open class BaseActivity : AppCompatActivity() {

    /**
     * 申请权限
     *
     * @param permissions
     */
    fun requestPermission(callBack: PermissionCallBack?, vararg permissions: String) {
        AndPermission.with(this)
                .runtime()
                .permission(*permissions)
                .onGranted { callBack?.onGranted() }
                .onDenied { callBack?.onDenied() }
                .start()
    }

}