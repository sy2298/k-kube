package com.example.k_kube
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.kcube.Data.Cube
import com.example.kcube.Data.MyDate
import com.example.kcube.Data.MyTime
import com.example.kcube.Data.User
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_selection.*
import java.util.*
//
class selection : AppCompatActivity() {
    var number = ""
    var nroom=0
    var row = 28
    var column = 3
    var time = arrayListOf<MyTime>()
    val tableIdCode = 1234
    var registeration = hashMapOf<Int, Int>()
    val tableLayout by lazy { TableLayout(this) }
    val tz = TimeZone.getTimeZone("Asia/Seoul")
    val gc = GregorianCalendar(tz)
    var hour = gc.get(GregorianCalendar.HOUR_OF_DAY).toString()
    var min = gc.get(GregorianCalendar.MINUTE).toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        init()
    }
    fun init(){
        var day:CalendarDay?=null
        var user: User?=null
        if(intent.hasExtra("building")){
            bname.text=intent.getStringExtra("building")
        }
        if(intent.hasExtra("nroom")){
            nroom=intent.getIntExtra("nroom",0)
        }
        if(intent.hasExtra("DAY")){
            day = intent.getParcelableExtra("DAY") as CalendarDay
        }
        if(intent.hasExtra("USER")){
            user = intent.getParcelableExtra("USER") as User
        }
        var tmp:ArrayList<Cube>
        Log.d("프래그먼트 크기",nroom.toString())
        tmp= arrayListOf()
        for(i in user!!.cubeList){
            if(day == i.dateList[0].date && i.name.contains(bname.text)){
                tmp.add(i)
            }
        }
        val adapter = RoomAdapter(supportFragmentManager,nroom,tmp,day!!)
        val pager = findViewById<View>(R.id.roomPage) as ViewPager
        pager.adapter=adapter
        btn_register.setOnClickListener {
            //            var frag1 = supportFragmentManager.findFragmentById(R.id.roomPage)
//            Log.d("fefefefefef",frag1.toString())
            Toast.makeText(this.applicationContext,"예약이 완료되었습니다.",Toast.LENGTH_SHORT).show()
            val selection = Intent(this,select_building::class.java)
            selection.putExtra("register", Cube(bname.text.toString()+" "+number.toString(), arrayListOf(MyDate(day!!, time[0],true),MyDate(day!!, time[time.size-1],true))))
            setResult(Activity.RESULT_OK,selection)
            finish()
            //startActivity(selection)
        }
    }
}

