package com.tomcan.quickuimate.view

import android.util.Log
import android.widget.Toast
import com.tomcan.quickui.mate.FragmentMate
import com.tomcan.frame.v.BaseActivity
import com.tomcan.frame.v.QuickBaseFragment_V1_0
import com.tomcan.quickui.view.QuickClickEditText
import com.tomcan.frame.vm.QuickViewModel
import com.tomcan.quickuimate.R
import com.tomcan.quickuimate.databinding.ActivityMainBinding
import com.tomcan.quickuimate.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    QuickBaseFragment_V1_0.BackHandlerInterface {

    override fun layout() = R.layout.activity_main

    override fun onStarted() {
        binding.searchEdit.apply {
            setDrawablesLeft(com.google.android.material.R.drawable.ic_clock_black_24dp)
            setDrawablesRight(com.google.android.material.R.drawable.ic_clock_black_24dp)
            setOnDrawablesClickListener(object :
                QuickClickEditText.OnDrawablesClickListener {
                override fun onClickEditView() {
                    Toast.makeText(this@MainActivity, "onClickEditView", Toast.LENGTH_LONG).show()
                }

                override fun leftDrawablesClick() {
                    Log.e("tomcan", "leftDrawablesClick")
                    Toast.makeText(this@MainActivity, "leftDrawablesClick", Toast.LENGTH_LONG)
                        .show()
                }

                override fun rightDrawablesClick() {
                    Toast.makeText(this@MainActivity, "rightDrawablesClick", Toast.LENGTH_LONG)
                        .show()
                }

            })
        }

        FragmentMate.getInstance().apply {
            setAttach(this@MainActivity)
            setContainer(R.id.container)
            add(HomeFragment())
        }
    }

    override fun onReStart() {

    }

    override fun backPressed() {
        finish()
        super.backPressed()
    }

    override fun backPressedEnabled(): Boolean {
        return true
    }

    override fun stackFragment(stackFragment: QuickBaseFragment_V1_0<*, out QuickViewModel<*>>?) {
    }
}