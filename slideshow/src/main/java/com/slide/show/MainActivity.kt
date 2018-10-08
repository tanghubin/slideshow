package com.slide.show

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
        setContentView(R.layout.activity_main)
        requestPermission(object : PermissionCallBack() {
            override fun onGranted() {
                var imageFile = FileUtil.getFilePath(Constant.IMAGE_NAME)
                var listFiles = imageFile.listFiles()
                if (listFiles.isEmpty()) {
                    FileUtil.copyAssetsToSD("slideshow", imageFile.absolutePath).setFileOperateCallback(object : FileOperateCallback {
                        override fun onFailed(error: String) {
                            Snackbar.make(xb_slide_show, "Error $error", Snackbar.LENGTH_INDEFINITE).show()
                        }

                        override fun onSuccess() {
                            listFiles = imageFile.listFiles()
                            loadImageForDisk(listFiles)
                        }
                    })
                } else {
                    loadImageForDisk(listFiles)
                }
            }

            override fun onDenied() {
                super.onDenied()
                loadImageForAssets()
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        var view = null as View?
        xb_slide_show.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 1) {
                    Log.d("tang", "textView == null")
                    view = View(this@MainActivity)
                    view!!.setBackgroundColor(Color.RED)
                    view!!.setOnClickListener {
                        Log.d("tang", "点击")
                        if (count == 0) {
                            xb_slide_show.postDelayed({
                                count = 0
                            }, 3000)
                        }
                        count++
                        if (count >= 5) {
                            startActivity(Intent(this@MainActivity, ImportImageActivity::class.java))
                            count = 0
                        }
                    }
                    xb_slide_show.addView(view,400,300)
                } else {
                    xb_slide_show.removeView(view)
                    view = null
                }
                Log.d("tang", "点击:${xb_slide_show.childCount}")
            }
        })
    }

    var count: Int = 0

    fun loadImageForDisk(listFiles: Array<File>) {
        val imageList = listFiles
                .filter {
                    !it.isDirectory
                }
                .sortedBy {
                    it.name.length
                }
        //添加轮播图片数据（图片数据不局限于网络图片、本地资源文件、View 都可以）,刷新数据也是调用该方法
        showImage(imageList)
    }

    private fun showImage(imageList: List<File>) {
        xb_slide_show.setAutoPlayAble(imageList.size > 1)
        xb_slide_show.setData(R.layout.item_image, imageList, null)//第二个参数为提示文字资源集合
        xb_slide_show.loadImage { _, _, view, position ->
            view as SimpleDraweeView
            view.setImageURI(Uri.fromFile(imageList[position]))
        }
    }

    fun loadImageForAssets() {
        val imageList = assets.list("slideshow")
                .toList()
                .sortedBy {
                    it.length
                }
        xb_slide_show.setAutoPlayAble(imageList.size > 1)
        xb_slide_show.setData(R.layout.item_image, imageList, null)//第二个参数为提示文字资源集合
        xb_slide_show.loadImage { _, _, view, position ->
            view as SimpleDraweeView
            view.setImageURI("asset:///slideshow/${imageList[position]}")
        }
    }

    override fun onResume() {
        super.onResume()
        xb_slide_show.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        xb_slide_show.stopAutoPlay()
    }
}
