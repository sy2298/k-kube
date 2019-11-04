package com.example.kcube.Adapter

import android.app.Dialog
import android.content.Context
import com.example.k_kube.R
import com.example.kcube.Data.Cube
import kotlinx.android.synthetic.main.cancel_dialog.*


class CustomDialog:Dialog  {

    //var check:Boolean = true
    var DialogListener:CustomDialogLstener?=null

    constructor(context: Context,data:Cube,cd:CustomDialogLstener) : super(context) {
        DialogListener = cd
        //requestWindowFeature(Window.FEATURE_NO_TITLE)//타이틀바를 없앰
        //window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//배경을 투명하게
        setContentView(R.layout.cancel_dialog)
        check_name.text = data.name
        check_time.text = data.dateList[0].time.hour_start.toString()+" : "+data.dateList[0].time.minute_start.toString()+" - "+data.dateList[data.dateList.size-1].time.hour_end+" : "+data.dateList[data.dateList.size-1].time.minute_end
        check_cancel_btn.setOnClickListener {
            DialogListener!!.clickCancel(data)
            dismiss()
        }
    }

    interface CustomDialogLstener{
        fun clickCancel(data:Cube)
    }
}