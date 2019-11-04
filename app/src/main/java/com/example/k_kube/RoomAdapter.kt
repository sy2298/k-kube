package com.example.k_kube

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kcube.Data.Cube
import com.prolificinteractive.materialcalendarview.CalendarDay

class RoomAdapter(fm: FragmentManager, roomNumber:Int,cube:ArrayList<Cube>,day:CalendarDay): FragmentPagerAdapter(fm) {
    var num = roomNumber
    var cube= cube
    var day = day
    override fun getCount(): Int {
        if (num > 6) return 3
        else return 2
    }

    override fun getItem(position: Int): Fragment {

        var args = Bundle(3)
        args.putParcelable("day",day)
        args.putParcelableArrayList("user",cube)
        var num1 = num %3
        if(num1 == 0){
            num1 =3
        }
        args.putInt("roomNum", num1)
        var args2 = Bundle(3)
        args2.putInt("roomNum",3)
        args2.putParcelableArrayList("user",cube!!)
        args2.putParcelable("day",day)

        if (num > 6) {
            Log.d("프래그먼트 크기6",num.toString())
            when (position) {
                0 -> {
                    var fragment1 = RoomFragment1()
                    fragment1.arguments = args2
                    return fragment1
                }
                1 -> {
                    var fragment2 =  RoomFragment2()
                    fragment2.arguments = args2
                    return fragment2
                }
                else -> {
                    var fragment3 = RoomFragment3()
                    fragment3.arguments = args
                    return fragment3
                }
            }
        } else {
            Log.d("프래그먼트 크기0",num.toString())
            when (position) {
                0 ->{
                   var fragment1 = RoomFragment1()
                    fragment1.arguments = args2
                    return fragment1
                }
                else -> {
                     var fragment2 =  RoomFragment2()
                    fragment2.arguments = args
                    return fragment2
                }
            }
        }
    }
}






