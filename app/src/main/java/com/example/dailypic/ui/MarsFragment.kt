package com.example.dailypic.ui

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.example.dailypic.R
import com.example.dailypic.databinding.MarsFragmentBinding
import com.example.dailypic.databinding.PictureFragmentBinding
import com.example.dailypic.model.marsModel.Photo
import com.example.dailypic.viewmodel.picture.MarsData
import com.example.dailypic.viewmodel.picture.MarsViewModel
import com.example.dailypic.viewmodel.picture.PictureData
import com.example.dailypic.viewmodel.picture.PictureViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MarsFragment : Fragment(), RVClickListener {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }
    lateinit var vb: MarsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MarsFragmentBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner, { renderData(it) })
        setAppBar()
        vb.rvMarsPhotos.layoutManager = GridLayoutManager(getContext(), 4)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top_navigation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, ChipsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: MarsData) {
        when (data) {
            is MarsData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.photos[0].imgSrc
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    vb.rvMarsPhotos.adapter = MarsAdapter(data.serverResponseData.photos, this)
                }
            }
            is MarsData.Loading -> {
            }
            is MarsData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun setAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(vb.topToolbar)
        context.getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
        vb.topToolbar.setNavigationOnClickListener {
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onClick(photo: Photo) {
        activity?.let {
            BottomNavigationDrawerFragment(photo).show(it.supportFragmentManager, "tag")
        }
    }

}