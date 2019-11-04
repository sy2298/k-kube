package com.example.kcube.Data

import android.os.Parcel
import android.os.Parcelable
import com.prolificinteractive.materialcalendarview.CalendarDay

data class MyDate(var date:CalendarDay,var time:MyTime, var reserve:Boolean):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(CalendarDay::class.java.classLoader)!!,
        parcel.readParcelable(MyTime::class.java.classLoader)!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(date, flags)
        parcel.writeParcelable(time, flags)
        parcel.writeByte(if (reserve) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyDate> {
        override fun createFromParcel(parcel: Parcel): MyDate {
            return MyDate(parcel)
        }

        override fun newArray(size: Int): Array<MyDate?> {
            return arrayOfNulls(size)
        }
    }
}