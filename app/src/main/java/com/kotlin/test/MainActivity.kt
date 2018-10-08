package com.kotlin.test

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var recver: MyBarodcastRecver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocalBroadcastManager.getInstance(this)
        bt_save.setOnClickListener {
            TestUtils.save(this, "测试", et_content.text.toString())
            val content = TestUtils.read(this, "测试")
            tv_show.text = content
        }

        bt_readcontact.setOnClickListener {
            val readContact = TestUtils.readContact(this)
            val sb = StringBuffer()
            readContact.keys.forEach {
                sb.append(it).append("——————").append(readContact[it]).append("\n")
            }
            tv_showcontact.text = sb.toString()
        }

        val countBits = TestUtils.countBits(1651)
        countBits.iterator().forEach {
            print("index:${countBits[it]}")

        }

        bt_startservices.setOnClickListener {
            val intent = Intent(this, MyServices::class.java)
            startService(intent)
        }
        bt_stopservices.setOnClickListener {
            val intent = Intent(this, MyServices::class.java)
            stopService(intent)
        }
        bt_bindservices.setOnClickListener {
            val intent = Intent(this, MyServices::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        bt_unbindservices.setOnClickListener {
            unbindService(connection)
        }

        bt_regist_recver.setOnClickListener {
            val intentFilter = IntentFilter()
            intentFilter.addAction("测试")
            recver = MyBarodcastRecver()
            registerReceiver(recver, intentFilter)
        }
        bt_send_msg.setOnClickListener {
            val intent = Intent("测试")
            intent.putExtra(MyBarodcastRecver.CONTENT, "我发送了一条消息")
            sendBroadcast(intent)
        }
        bt_unregist.setOnClickListener {
            unregisterReceiver(recver)
        }
//        var reslut = 0
//        addAll(5, reslut)
//        Log.d("tang", "结果:$reslut")

        var y = add()
        y()
        y()
        y()
        y()

        val i = listOf<Son>(Son(), Son())
        //最大
        val maxBy = i.maxBy {
            it.hashCode()
        }
        //最小
        val minBy = i.minBy {
            it.hashCode()
        }
        //过滤条件
        val filter = i.filter {
            it.hashCode() == 1 and it.hashCode()
        }
        //封装map集合
        val map = i.map {
            "${it.hashCode()}:${it.hashCode()}"
        }
        //包含符合某个条件
        val any = i.any {
            it.hashCode() == 1
        }
        //符合条件的计数
        i.count {
            it.hashCode() == 1
        }
        //查找符合条件的 一旦有符合直接返回
        i.find {
            it.hashCode() == 1
        }

        //查找符合条件的 返回最后一个
        i.findLast {
            it.hashCode() == 1
        }
        //按条件分组
        i.groupBy {
            it.hashCode() == 1
        }
        //扩展函数
        i.test(1)
        //中缀表达式，用空格替换了点的调用 dsl语法
        i test1 1
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            print("服务绑定成功$componentName")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            print("服务解绑成功$componentName")
        }
    }

    tailrec fun addAll(num: Int, reslut: Int): Int {//5,0  4+5  3+9 2+11 1
        if (num == 0) {
            return 1
        } else {
            return addAll(num - 1, reslut + num)
        }
    }

    /**
     * 闭包 方法中包含方法
     */
    fun add(): () -> Unit {
        var i = 3
        return fun() {
            i++
            println("结果:$i")
            Log.d("tang", "234234234")
        }
    }

    fun List<Son>.test(i: Int) {
        filter {
            it.hashCode() == 1
        }
    }

    infix fun List<Son>.test1(i: Int) {
        filter {
            it.hashCode() == 1
        }
    }
    val test = if (5 > 3) {
        println("yes")
    } else {
        println("no")
    }
}
