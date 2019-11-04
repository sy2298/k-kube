package com.example.kcube.Data

import android.os.Parcel
import android.os.Parcelable

/*data class User(var name:String, var id:String,var cubeList:ArrayList<Cube>):Parcelable {
    constructor(parcel: Parcel) /*: this(
        parcel.readString()!!,
        parcel.readString()!!,
        //TODO("cubeList")
        (parcel.readArrayList(Cube::class.java.classLoader) as ArrayList<Cube>?)!!
        //parcel.readTypedList(cubeList,Parcelable.Creator<Cube>)
    ) */{
        this()
        id = parcel.readString()!!

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeTypedList(cubeList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}*/
class User() :Parcelable{
    lateinit var name:String
    lateinit var id:String
    lateinit var cubeList:ArrayList<Cube>

    constructor(name:String, id:String, cubeList:ArrayList<Cube>) : this() {
        this.name = name
        this.id = id
        this.cubeList = cubeList
    }

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()!!
        id = parcel.readString()!!
        cubeList = arrayListOf()
        parcel.readTypedList(cubeList,Cube.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeTypedList(cubeList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}