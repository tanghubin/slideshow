package com.slide.show

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.io.File
import java.io.FileOutputStream

object FileUtil {

    private var callback: FileOperateCallback? = null
    private var isSuccess: Boolean = false
    private var errorStr: String? = null
    private const val SUCCESS = 1
    private const val FAILED = 0

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback!!.onSuccess()
                }
                if (msg.what == FAILED) {
                    callback!!.onFailed(msg.obj.toString())
                }
            }
        }
    }

    fun copyAssetsToSD(srcPath: String, sdPath: String): FileUtil {
        Thread(Runnable {
            copyAssetsToDst(SlideShowApp.getApplication(), srcPath, sdPath)
            if (isSuccess)
                handler.obtainMessage(SUCCESS).sendToTarget()
            else
                handler.obtainMessage(FAILED, errorStr).sendToTarget()
        }).start()
        return this
    }

    fun setFileOperateCallback(callback: FileOperateCallback) {
        this.callback = callback
    }

    private fun copyAssetsToDst(context: Context, srcPath: String, dstPath: String) {
        try {
            val fileNames = context.assets.list(srcPath)
            if (fileNames!!.isNotEmpty()) {
                val file = File(dstPath)
                if (!file.exists()) file.mkdirs()
                for (fileName in fileNames) {
                    if (srcPath != "") { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + File.separator + fileName)
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName)
                    }
                }
            } else {
                val outFile = File(dstPath)
                val inputStream = context.assets.open(srcPath)
                val fos = FileOutputStream(outFile)
                val buffer = ByteArray(1024)
                var byteCount = 0 as Int

                while ({ byteCount = inputStream.read(buffer);byteCount }() != -1) {
                    fos.write(buffer, 0, byteCount)
                }
                fos.flush()
                inputStream.close()
                fos.close()
            }
            isSuccess = true
        } catch (e: Exception) {
            e.printStackTrace()
            errorStr = e.message
            isSuccess = false
        }

    }

    fun getFilePath(fileName: String): File {
        val file = File(Constant.SD_PATH + File.separator + Constant.DEFAULT_FILE_NAME, fileName)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

}
