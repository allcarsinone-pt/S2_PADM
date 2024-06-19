package com.allcarsinone.allcarsinone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.adapters.ListItem
import com.allcarsinone.allcarsinone.adapters.ListViewAdapter
import java.util.Date

class CommentsActivity : AppCompatActivity() {

    lateinit var commentsRv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        commentsRv = findViewById(R.id.recyclerViewComments)

        commentsRv.layoutManager = LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.VERTICAL
        }

        val adapter = ListViewAdapter(mutableListOf(ListItem.Layout2(R.drawable.ferrari, "Carro do aço!!", Date()),ListItem.Layout2(R.drawable.ferrari, "Bom carro!!", Date()),ListItem.Layout2(R.drawable.ferrari, "Podia ser melhor", Date())))
        commentsRv.adapter = adapter

    }
}