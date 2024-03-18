package com.tomcan.quickuimate

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tomcan.quickui.mate.FragmentMate
import com.tomcan.quickui.v.QuickBaseFragment
import com.tomcan.quickui.vi.QuickClickEditText
import com.tomcan.quickui.vm.QuickViewModel
import com.tomcan.quickuimate.view.HomeFragment

class MainActivity : AppCompatActivity(), QuickBaseFragment.BackHandlerInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val findViewById = findViewById<QuickClickEditText>(R.id.search_edit)
        findViewById.setDrawablesLeft(com.google.android.material.R.drawable.ic_clock_black_24dp)
        findViewById.setDrawablesRight(com.google.android.material.R.drawable.ic_clock_black_24dp)
        findViewById.setOnDrawablesClickListener(object :
            QuickClickEditText.OnDrawablesClickListener {
            override fun onClickEditView() {
                Toast.makeText(findViewById.context, "onClickEditView", Toast.LENGTH_LONG).show()
            }

            override fun leftDrawablesClick() {
                Log.e("tomcan", "leftDrawablesClick")
                Toast.makeText(findViewById.context, "leftDrawablesClick", Toast.LENGTH_LONG).show()
            }

            override fun rightDrawablesClick() {
                Toast.makeText(findViewById.context, "rightDrawablesClick", Toast.LENGTH_LONG)
                    .show()
            }

        })

        FragmentMate.getInstance().apply {
            setAttach(this@MainActivity)
            setContainer(R.id.container)
            add(HomeFragment())
        }
    }

    override fun stackFragment(stackFragment: QuickBaseFragment<*, out QuickViewModel<*>>?) {
    }
}