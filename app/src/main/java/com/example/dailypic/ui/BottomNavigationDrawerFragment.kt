package com.example.dailypic.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.api.load
import com.example.dailypic.R
import com.example.dailypic.model.marsModel.Photo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView

class BottomNavigationDrawerFragment(p: Photo) : BottomSheetDialogFragment() {
    private val photo = p

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.img_bottom_mars)?.load(photo.imgSrc)
        view.findViewById<TextView>(R.id.tv_bottom_header)?.text = photo.rover.name
        view.findViewById<TextView>(R.id.tv_bottom_description)?.text = photo.camera.fullName
    }
}
