package com.example.kcube

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.k_kube.R
import com.example.kcube.Data.Cube
import com.example.kcube.Data.MyDate
import com.example.kcube.Data.MyTime
import com.example.kcube.Data.User
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_main.*

//로그인
class MainActivity : AppCompatActivity() {

    val list = arrayListOf<Cube>(Cube("공학관 1호실", arrayListOf(MyDate(CalendarDay (2019,10,4),
        MyTime(9,0,9,30),true),MyDate(CalendarDay(2019,10,4),MyTime(10,0,10,30),true))),
        Cube("상허기념도서관 4호실", arrayListOf(MyDate(CalendarDay(2019,10,4),MyTime(11,0,11,30),true))))
    val defaultUser = User("김익명","abc123",list)
    val defaultPW = "5555"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickLogin(view: View?){
        //로그인 버튼 눌렀을 때
        var id = user_id.text.toString()
        var pw = user_pw.text.toString()
        if(id == defaultUser.id && pw == defaultPW){
            Toast.makeText(this,"환영합니다",Toast.LENGTH_SHORT).show()
            Thread.sleep(1000)//1초 sleep
            var intent = Intent(this, ExplanationActivity::class.java)
            intent.putExtra("USER",defaultUser)
            startActivity(intent)

        }else{
            Toast.makeText(this,"로그인 정보가 틀렸습니다",Toast.LENGTH_SHORT).show()
            user_id.text.clear()
            user_pw.text.clear()
        }
    }
}
