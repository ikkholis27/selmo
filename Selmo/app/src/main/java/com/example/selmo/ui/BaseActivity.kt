package com.example.selmo.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {


    protected abstract fun getLayoutView(): Int
    protected abstract fun initComponents(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())
        initComponents(savedInstanceState)
    }

    fun setLog(message: String) {
        Log.d("aas", "setLog: " + message)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setToast(message: String) {

        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}