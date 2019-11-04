package com.example.kcube.Service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.example.k_kube.R
import com.example.kcube.CalendarActivity
import com.example.kcube.Data.User
import com.prolificinteractive.materialcalendarview.CalendarDay

class MyService : Service(){

     var thread:ServiceThread ?= null
    var Notifi_M:NotificationManager ?= null
    var Notify:Notification ?= null
    var user: User?=null


    override fun onBind(intent: Intent): IBinder? {
        //TODO("Return the communication channel to the service.")
        return null
    }

    override fun onCreate() {
        //최초호출
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //서비스가 호출 될 때 마다 실행
        Log.d("스레드","서비스 시작")
        Notifi_M = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        user = intent!!.getParcelableExtra("USER")
        var handler = myServiceHandler()
        thread = ServiceThread(handler)
        thread!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        thread!!.stopForever()
        thread = null
        //서비스 종료
    }

    inner class myServiceHandler : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d("스레드","핸들러")
            var intent = Intent(this@MyService,CalendarActivity::class.java)
                intent.putExtra("USER",user!!)
            var pendingIntent = PendingIntent.getActivity(this@MyService,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            var today = CalendarDay.today()
            var builer:Notification.Builder
            for(i in user!!.cubeList){
                if(today == i.dateList[0].date){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        builer = Notification.Builder(this@MyService)
                            .setContentTitle("오늘 케이큐브 예약이 있습니다")
                            .setContentText("예약 내용을 확인하세요")
                            .setTicker("알림")
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.drawable.smalllogo)
                    }else{
                        builer = Notification.Builder(this@MyService)
                            .setContentTitle("오늘 케이큐브 예약이 있습니다")
                            .setContentText("예약 내용을 확인하세요")
                            .setTicker("알림")
                            .setContentIntent(pendingIntent)

                    }
                    Notify = builer.build()
                    Notify!!.flags = Notification.FLAG_AUTO_CANCEL
                    Notifi_M!!.notify(777,Notify)
                }
            }
        }
    }
}
