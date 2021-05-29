package com.example.dailypic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dailypic.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.navigation_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<NavigationView>(R.id.navigation_view).setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_fav -> {
//                TODO
//                    toast("Favourite")
                    true
                }
                R.id.app_bar_settings -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.container, ChipsFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.app_bar_search -> {
//                TODO
//                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN || bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
//                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                    } else {
//                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//                    }
                    true
                }
                else -> false
            }
        }
    }
}
