package com.example.kcube

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.k_kube.R
import com.example.k_kube.select_building
import com.example.kcube.Adapter.ReserveListAdapter
import com.example.kcube.Data.Cube
import com.example.kcube.Data.User
import com.example.kcube.Service.MyService
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.cancel_dialog.view.*
import java.util.*
import java.util.concurrent.Executors

//달력 겸 메인화면
class CalendarActivity : AppCompatActivity(){


    var t_month = 10
    var t_year = 2019
    lateinit var  user:User
    var cancelData:Cube ?= null
    //val my = User("김익명","abc123")
    //var rdate = arrayListOf<MyDate>(MyDate(11,2019,14,3,30,true),MyDate(11,2019,26,12,0,true))
    var cdate = arrayListOf<CalendarDay>(CalendarDay(2019,10,14), CalendarDay(2019,10,26))
    lateinit var adapter: ReserveListAdapter
    lateinit var c_view:com.prolificinteractive.materialcalendarview.MaterialCalendarView
    lateinit var tmp:ArrayList<Cube>
    var select_day:CalendarDay ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        //Log.d("스레드",Calendar.getInstance().time.toString())
        Log.d("스레드",System.currentTimeMillis().toString())
        makeCalendar()
        makePushNotification()
    }


    fun clickReserve(view:View){
        //예약하기를 눌렀을 떄
        if(select_day != null){
            makeTmp(select_day!!)
            var time = 0
            for(i in tmp){
                var hour =i.dateList[i.dateList.size-1].time.hour_end- i.dateList[0].time.hour_start
                var minute =  i.dateList[i.dateList.size-1].time.minute_end-i.dateList[0].time.minute_start
                time += (hour*60)+minute
            }
            if(time>= 180){
                //3시간이 지났으면
                Toast.makeText(this,"오늘 하루는 더이상 예약할 수 없어요",Toast.LENGTH_SHORT).show()
            }else{
                val selectBuilding = Intent(this, select_building::class.java)
//            selectBuilding.putExtra("date",select_day!!.day)
                selectBuilding.putExtra("DAY",select_day)
                selectBuilding.putExtra("USER",user)
                startActivityForResult(selectBuilding,7777)

            }

            
        }else{
            Toast.makeText(this,"날짜를 선택해 주세요",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==7777 && resultCode == Activity.RESULT_OK){
            Log.d("크기",tmp.size.toString())
            var t = data!!.getParcelableExtra("register") as Cube
            Log.d("크기",tmp.size.toString())
            user.cubeList.add(t)
            makeTmp(select_day!!)
            Log.d("크기",tmp.size.toString())
            initLayout(tmp)
            MarkThread(user.cubeList!!,calendarView).executeOnExecutor(Executors.newSingleThreadExecutor())
        }
    }
    fun makeCalendar(){
        if(intent != null){
            user = intent.getParcelableExtra("USER") as User
        }
        select_day = CalendarDay.today()
        makeTmp(select_day!!)
        initLayout(tmp)
        //Log.d("스레드",user.cubeList.size.toString())
        c_view = calendarView

        calendarView.addDecorators(
            SundayDecorator(),
            SaturdayDecorator(),
            OneDayDecorator()
           // EventDecorator(Color.RED,user.cubeList)
        )

        calendarView.setOnDateChangedListener { widget, date, selected ->
            //클릭했을 때
            //click_date.text = (date.month+1).toString()
            select_day = date
            makeTmp(date)
            initLayout(tmp)
        }

       calendarView.setOnMonthChangedListener { widget, date ->
           select_day = null
           tmp = arrayListOf()
           initLayout(tmp)
       }
        //마커 달기
       MarkThread(user.cubeList!!,calendarView).executeOnExecutor(Executors.newSingleThreadExecutor())
    }

    fun makeTmp(date:CalendarDay){
        tmp = arrayListOf()
        for(i in 0 until user.cubeList.size){
            for(j in 0 until user.cubeList[i].dateList.size){
                if(user.cubeList[i].dateList[j].date == date){
                    //만약 선택한 날짜가 있고, 예약이 되어있으면
                    var check = true
                    for(k in 0 until tmp.size){
                        if(tmp[k].name == user.cubeList[i].name){
                            check = false
                            break
                        }
                    }
                    if(check){
                        tmp.add(user.cubeList[i])
                    }
                }
            }
        }
    }

    fun initLayout(cube:ArrayList<Cube>){
        adapter = ReserveListAdapter(cube,this)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        reserve_list.layoutManager = layoutManager
        reserve_list.adapter = adapter
        adapter.itemClickListener = object:ReserveListAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: ReserveListAdapter.ViewHolder,
                view: View,
                data: Cube,
                position: Int
            ) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                makeDialog(data)
            }
        }
    }

    fun makeDialog(data:Cube){
        val builder = AlertDialog.Builder(this)
        val dialog = layoutInflater.inflate(R.layout.cancel_dialog,null)
        dialog.check_name.text = data.name
        dialog.check_time.text = data.dateList[0].time.hour_start.toString()+" : "+data.dateList[0].time.minute_start.toString()+" - "+data.dateList[data.dateList.size-1].time.hour_end+" : "+data.dateList[data.dateList.size-1].time.minute_end
        dialog.check_cancel_btn.setOnClickListener {
            user.cubeList.remove(data)
            tmp.remove(data)
            initLayout(tmp)
        }
        builder.setView(dialog)
        builder.create().show()
    }

    fun makePushNotification(){
        var intent = Intent(this,MyService::class.java)
        intent.putExtra("USER",user)
        startService(intent)
    }



    class SundayDecorator:DayViewDecorator{

        var calendar = Calendar.getInstance()

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            day!!.copyTo(calendar)
            var weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return weekDay == Calendar.SUNDAY
        }

        override fun decorate(view: DayViewFacade?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            view!!.addSpan(ForegroundColorSpan(Color.RED))
        }
    }

    class SaturdayDecorator:DayViewDecorator{

        var calendar = Calendar.getInstance()

        override fun shouldDecorate(day: CalendarDay?): Boolean {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            day!!.copyTo(calendar)
            var weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return weekDay == Calendar.SATURDAY
        }

        override fun decorate(view: DayViewFacade?) {
         //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            view!!.addSpan(ForegroundColorSpan(Color.RED))
        }

    }

    class OneDayDecorator:DayViewDecorator{

        var date:CalendarDay

        init{
            date = CalendarDay.today()
        }

        override fun shouldDecorate(day: CalendarDay?): Boolean {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return date != null && day!!.equals(date)
        }


        override fun decorate(view: DayViewFacade?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            view!!.addSpan(ForegroundColorSpan(Color.BLUE))
        }

        fun setDate(date:Date){
            this.date = CalendarDay.from(date)
        }

    }

    class EventDecorator:DayViewDecorator{

        var color:Int
        var dates:HashSet<CalendarDay>

        constructor(color:Int,tdates:ArrayList<Cube>){
            this.color = color
            this.dates = HashSet<CalendarDay>()
            //this.dates = hashSetOf()
            for(i in 0 until tdates.size){
                for(j in 0 until tdates[i].dateList.size){
                    Log.d("날짜",tdates[i].dateList[j].date.day.toString())
                    this.dates.add(tdates[i].dateList[j].date)
                }
            }
        }

        override fun shouldDecorate(day: CalendarDay?): Boolean {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade?) {
          //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            view!!.addSpan(DotSpan(3f,color))
        }
    }

    class MarkThread : AsyncTask<Void,Void,ArrayList<Cube>> {

        var dates:ArrayList<Cube>
        var view:com.prolificinteractive.materialcalendarview.MaterialCalendarView

        constructor(dates:ArrayList<Cube>,view:com.prolificinteractive.materialcalendarview.MaterialCalendarView){
            this.dates = dates
            this.view = view
        }

        override fun doInBackground(vararg p0: Void?): ArrayList<Cube> {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            try
            {
                Thread.sleep(500)
            }catch(e:InterruptedException){
                e.printStackTrace()
            }
            Log.d("스레드","돈다")
            var cal = Calendar.getInstance()
            cal.add(Calendar.MONTH,0)
            var tmpdates = arrayListOf<Cube>()
            for(i in 0 until dates.size){
                tmpdates.add(dates[i])//큐브하나 추가
                for(j in 0 until dates.get(i).dateList.size){
                    Log.d("스레드",dates[i].dateList[j].date.year.toString())
                    cal.set(dates[i].dateList[j].date.year,dates[i].dateList[j].date.month,dates[i].dateList[j].date.day)
                }
               // cal.set(dates[i].dateList,dates[i].month,dates[i].day)
            }

            return tmpdates
        }

        override fun onPostExecute(result: ArrayList<Cube>?) {
            super.onPostExecute(result)

            view.addDecorator(EventDecorator(Color.RED,result!!))
        }
    }
}
