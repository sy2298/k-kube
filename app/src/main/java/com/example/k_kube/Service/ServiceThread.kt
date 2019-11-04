package com.example.kcube.Service

import android.os.Handler
import android.util.Log


class ServiceThread:Thread {

    var handler : Handler?=null
    var isRun = true


    constructor(handler:Handler){
        this.handler = handler
    }

    fun stopForever(){
        synchronized(this){
            this.isRun = false
        }
    }

    override fun run() {
        super.run()
        while(isRun){
            //반복적으로 수행할 작업
            Log.d("스레드","핸들러 메세지")

            handler?.sendEmptyMessage(0)//메세지를 보냄
            Thread.sleep(86400000L)//하루를 기다린다
            /*
            var today = CalendarDay.today()
            var check = true
            for(i in user!!.cubeList){
                if(today == i.dateList[0].date){
                    //오늘날짜에 예약 내역이 있는 경우

                    Log.d("스레드","쉼")
                    check = false
                    var waitTime = 86400000L- System.currentTimeMillis()
                    Thread.sleep(waitTime)//하루를 기다린다
                }
            }
            if(check){
                //예약 내역이 없는 경우

            }*/




        }
    }



}