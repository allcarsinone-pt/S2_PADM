package com.allcarsinone.allcarsinone.fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.allcarsinone.allcarsinone.AuthUtils
import com.allcarsinone.allcarsinone.activities.EditProfileActivity
import com.allcarsinone.allcarsinone.activities.EditStandProfileActivity
import com.allcarsinone.allcarsinone.activities.FavoritesActivity
import com.allcarsinone.allcarsinone.activities.InsertEditVehicleActivity
import com.allcarsinone.allcarsinone.activities.LoginActivity
import com.allcarsinone.allcarsinone.activities.NotificationsActivity
import com.allcarsinone.allcarsinone.activities.NotificationsViewActivity
import com.allcarsinone.allcarsinone.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _viewBinding: FragmentMenuBinding? = null

    private val viewBinding get() = _viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        viewBinding?.fragmentMenuEditProfileBTN?.setOnClickListener {
            finishFragment()
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        viewBinding?.fragmentMenuNotificationsBTN?.setOnClickListener {
            finishFragment()
            val intent = Intent(context, NotificationsViewActivity::class.java)
            startActivity(intent)
        }

        viewBinding?.fragmentMenuFavoritesBTN?.setOnClickListener {
            finishFragment()
            val intent = Intent(context, FavoritesActivity::class.java)
            startActivity(intent)
        }

        viewBinding?.fragmentMenuLogoutBTN?.setOnClickListener {
            finishFragment()
            AuthUtils.logoutUser(requireContext())
        }

        viewBinding?.fragmentMenuUserPreferencesBTN?.setOnClickListener {
            finishFragment()
            val intent = Intent(context, NotificationsActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    fun finishFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}