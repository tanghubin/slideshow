package com.kotlin.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

public class MyBarodcastRecver : BroadcastReceiver() {
    companion object{
        val CONTENT = "content"
    }
    override fun onReceive(context: Context?, intent: Intent) {
        val stringExtra = intent.getStringExtra(CONTENT)
        Toast.makeText(context, stringExtra, Toast.LENGTH_SHORT).show()
    }

}