package com.example.kcube

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.k_kube.R
import com.example.kcube.Data.User

class ExplanationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation)
    }

    fun Calendar(view: View){
        var user = intent.getParcelableExtra("USER") as User
        var intent = Intent(this, CalendarActivity::class.java)
        intent.putExtra("USER", user)
        startActivity(intent)
    }
}
