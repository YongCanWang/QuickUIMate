package com.tomcan.quickuimate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tomcan.quickuimate.view.QuickClickEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val findViewById = findViewById<QuickClickEditText>(R.id.search_edit)
        findViewById.setDrawablesLeft(com.google.android.material.R.drawable.ic_clock_black_24dp)
        findViewById.setDrawablesRight(com.google.android.material.R.drawable.ic_clock_black_24dp)
        findViewById.setOnDrawablesClickListener(object : QuickClickEditText.OnDrawablesClickListener{
            override fun onClickEditView() {
                Toast.makeText(findViewById.context, "onClickEditView", Toast.LENGTH_LONG).show()
            }

            override fun leftDrawablesClick() {
                Log.e("tomcan", "leftDrawablesClick")
                Toast.makeText(findViewById.context, "leftDrawablesClick", Toast.LENGTH_LONG).show()
            }

            override fun rightDrawablesClick() {
                Toast.makeText(findViewById.context, "rightDrawablesClick", Toast.LENGTH_LONG).show()
            }

        })
    }
}