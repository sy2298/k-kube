package com.example.k_kube

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.kcube.CalendarActivity
import com.example.kcube.Data.Cube
import com.example.kcube.Data.User
import com.prolificinteractive.materialcalendarview.CalendarDay

class select_building : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_building)
        init()
    }

    fun init()
    {
        var day = intent.getParcelableExtra("DAY") as CalendarDay
        var user = intent.getParcelableExtra("USER") as User
        val radio = mutableListOf<RadioButton>()
        for(i in R.id.radiobutton01..R.id.radiobutton07){
            radio.add(findViewById(i))
        }
        for(i in R.id.radiobutton01..R.id.radiobutton07){
            val radio = findViewById<RadioButton>(i)
            var nroom = 0
            if(radio.text == "공학관") nroom = 8
            else if(radio.text == "생명과학관") nroom = 7
            if(radio.text == "상허연구관") nroom = 8
            if(radio.text == "상허기념도서관") nroom = 6
            if(radio.text == "상허기념도서관 C Space") nroom = 4
            if(radio.text == "동물생명과학관") nroom = 5
            radio.setOnCheckedChangeListener { buttonView, isChecked ->
                val selection = Intent(this,selection::class.java)
                selection.putExtra("building",radio.text)
                selection.putExtra("nroom",nroom)
                selection.putExtra("DAY",day)
                selection.putExtra("USER",user)
                startActivityForResult(selection,6666)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 6666 && resultCode== Activity.RESULT_OK){
            var intent = Intent(this,CalendarActivity::class.java)
            intent.putExtra("register",data!!.getParcelableExtra("register") as Cube)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}
