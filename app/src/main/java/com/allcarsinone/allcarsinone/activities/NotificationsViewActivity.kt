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
import java.util.Date

class NotificationsViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notifications_view)

        runNotifList()
    }

    fun runNotifList() {
        val arraylist = ArrayList<Notifications>()
        arraylist.add( Notifications( R.drawable.ferrari, "Brand1", "Small comment", Date()))
        arraylist.add( Notifications( R.drawable.carkms, "Brand2", "Tiny comment", Date()))
        arraylist.add( Notifications(R.drawable.ferrari, "Brand3", "This is a comment that needs ate least two lines to be shown", Date()))
        arraylist.add( Notifications( R.drawable.testdrive, "Brand4", "This is a huge comment that needs too much more space ans says that the lazy brown fox jumps over the lazy dog ", Date()))
        arraylist.add( Notifications( R.drawable.ferrari, "Brand5", "This is another small comment", Date()))
        arraylist.add( Notifications(R.drawable.ferrari, "Brand6", "This is a comment that needs ate least two lines to be shown", Date()))
        arraylist.add( Notifications( R.drawable.testdrive, "Brand7", "This is a huge comment that needs too much more space ans says that the lazy brown fox jumps over the lazy dog ", Date()))
        arraylist.add( Notifications( R.drawable.ferrari, "Brand8", "This is another small comment", Date()))
        arraylist.add( Notifications(R.drawable.ferrari, "Brand9", "This is a comment that needs ate least two lines to be shown", Date()))

        val recyclerView = findViewById<RecyclerView>( R.id.NotificationsView_RecycleView)
        val adapter = ListViewAdapter( arraylist );

        recyclerView.layoutManager = LinearLayoutManager( this )
        recyclerView.adapter = adapter
    }
}