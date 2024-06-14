package com.allcarsinone.allcarsinone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.allcarsinone.allcarsinone.AuthUtils
import com.allcarsinone.allcarsinone.activities.EditStandProfileActivity
import com.allcarsinone.allcarsinone.activities.InsertEditVehicleActivity
import com.allcarsinone.allcarsinone.activities.LoginActivity
import com.allcarsinone.allcarsinone.databinding.FragmentMenuStandBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MenuStandFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuStandFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _viewBinding: FragmentMenuStandBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    fun finishFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _viewBinding = FragmentMenuStandBinding.inflate(inflater, container, false)
        val view = viewBinding.root
        viewBinding.fragmentMenuStandLogoutBTN.setOnClickListener {
            // TODO: FALTA o LOGOUT (Desabilitar o token?)
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            finishFragment()
        }
        viewBinding.fragmentMenuStandInsertBTN.setOnClickListener {
            finishFragment()
            val intent = Intent(requireActivity(), InsertEditVehicleActivity::class.java)
            startActivity(intent)

        }
        viewBinding.fragmentMenuStandProfileBTN.setOnClickListener {
            finishFragment()
            val intent = Intent(requireActivity(), EditStandProfileActivity::class.java)
            startActivity(intent)
        }
        viewBinding.fragmentMenuStandLogoutBTN.setOnClickListener {
            finishFragment()
            AuthUtils.logoutUser(requireContext())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuStandFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuStandFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}