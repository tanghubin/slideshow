package com.slide.show

import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView

class ImportImageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
        super.onCreate(savedInstanceState)
        var tv = TextView(this)
        tv.text="测试数据"
        setContentView(tv)
    }
}
