package com.kotlin.test

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class MyServices : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "服务已绑定", Toast.LENGTH_SHORT).show()
        return Binder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "服务已解绑", Toast.LENGTH_SHORT).show()
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "服务已启动", Toast.LENGTH_SHORT).show()
        stopService(Intent(this, MyServices1::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "服务已销毁", Toast.LENGTH_SHORT).show()
        startService(Intent(this, MyServices1::class.java))
    }
}