package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.FavoritesRecyclerViewAdapter
import com.allcarsinone.allcarsinone.databinding.ActivityFavoritesBinding
import com.allcarsinone.allcarsinone.models.FavoriteUserCar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesActivity : AppCompatActivity() {
    private val viewBinding: ActivityFavoritesBinding by lazy { ActivityFavoritesBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val vehicleAPI = Globals.vehicleAPI

        viewBinding.favoritesListFavoritesRl.layoutManager = LinearLayoutManager(this@FavoritesActivity).apply {
            this.orientation = LinearLayoutManager.VERTICAL
        }
        val favorites = vehicleAPI.getUserFavorites(1)
        favorites.enqueue(object : Callback<List<FavoriteUserCar>> {
            override fun onResponse(
                p0: Call<List<FavoriteUserCar>>,
                p1: Response<List<FavoriteUserCar>>
            ) {
                when(p1.code()) {
                    200 -> {

                        Log.e("Debug", "${p1.body()!!.size}")
                        viewBinding.favoritesListFavoritesRl.adapter = FavoritesRecyclerViewAdapter(
                            p1.body()!!.toMutableList())


                    }

                    400 -> {
                        Toast.makeText(this@FavoritesActivity, "User not found", Toast.LENGTH_LONG).show()
                    }
                    500 -> {
                        Toast.makeText(this@FavoritesActivity, "Internal server error", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(p0: Call<List<FavoriteUserCar>>, p1: Throwable) {
                Toast.makeText(this@FavoritesActivity, p1.message, Toast.LENGTH_LONG).show()
            }

        })

    }


}