package com.example.photoplan.ui.location

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface LocationView: MvpView {
    fun updateUi()
    fun notifyItemInserted()
    fun makeToast(message: String)
}
