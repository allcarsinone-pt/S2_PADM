package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.ListViewAdapter
import com.allcarsinone.allcarsinone.Notifications
import com.allcarsinone.allcarsinone.adapters.ListItem
import java.util.Date

class NotificationsViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notifications_view)

        runNotifList()
    }

    private fun runNotifList() {

        val list = listOf(
            ListItem.Layout1(R.drawable.ferrari, "Brand1", "Small comment", Date()),
            ListItem.Layout1(R.drawable.carkms, "Brand2", "Tiny comment", Date()),
            ListItem.Layout2(R.drawable.ferrari, "Happy Birthday Mr Zé", Date()),
            ListItem.Layout1(R.drawable.ferrari, "Brand3", "This is another small comment", Date()),
            ListItem.Layout2(R.drawable.testdrive, "License expiring in 3 days", Date()),
            ListItem.Layout1(R.drawable.ferrari, "Brand6", "This is a comment that needs ate least two lines to be shown", Date()),
            ListItem.Layout1(R.drawable.testdrive, "Brand7", "This is a huge comment that needs too much more space ans says that the lazy brown fox jumps over the lazy dog ", Date()),
            ListItem.Layout1(R.drawable.ferrari, "Brand8", "This is another small comment", Date()),
            ListItem.Layout1(R.drawable.ferrari, "Brand9", "This is a comment that needs ate least two lines to be shown", Date())
        )
        val adapter = ListViewAdapter(list)
        val recyclerView = findViewById<RecyclerView>( R.id.NotificationsView_RecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}