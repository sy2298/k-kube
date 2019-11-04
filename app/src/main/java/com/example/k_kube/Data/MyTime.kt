package com.example.kcube.Data

import android.os.Parcel
import android.os.Parcelable

data class MyTime(var hour_start:Int, var minute_start:Int,var hour_end:Int, var minute_end:Int) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(hour_start)
        parcel.writeInt(minute_start)
        parcel.writeInt(hour_end)
        parcel.writeInt(minute_end)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyTime> {
        override fun createFromParcel(parcel: Parcel): MyTime {
            return MyTime(parcel)
        }

        override fun newArray(size: Int): Array<MyTime?> {
            return arrayOfNulls(size)
        }
    }

}