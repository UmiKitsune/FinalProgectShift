package com.example.finalprogect_shift.ui.view_options

import android.view.View

class Options {

    companion object {
        fun View.visible(isVisible: Boolean){
            visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

}

