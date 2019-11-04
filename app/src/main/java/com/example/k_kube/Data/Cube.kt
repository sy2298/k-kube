package com.example.kcube.Data

import android.os.Parcel
import android.os.Parcelable

/*
class Cube(var name:String, var start:MyDate,var end:MyDate) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable(MyDate::class.java.classLoader)!!,
        parcel.readParcelable(MyDate::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(start, flags)
        parcel.writeParcelable(end, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cube> {
        override fun createFromParcel(parcel: Parcel): Cube {
            return Cube(parcel)
        }

        override fun newArray(size: Int): Array<Cube?> {
            return arrayOfNulls(size)
        }
    }
}
 */

class Cube() : Parcelable {

    lateinit var name:String
    lateinit var dateList:ArrayList<MyDate>

    constructor(name:String, dateList:ArrayList<MyDate>) : this() {
        this.name = name
        this.dateList = dateList
    }

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()!!
        dateList = arrayListOf()
        parcel.readTypedList(dateList,MyDate.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(dateList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cube> {
        override fun createFromParcel(parcel: Parcel): Cube {
            return Cube(parcel)
        }

        override fun newArray(size: Int): Array<Cube?> {
            return arrayOfNulls(size)
        }
    }

}