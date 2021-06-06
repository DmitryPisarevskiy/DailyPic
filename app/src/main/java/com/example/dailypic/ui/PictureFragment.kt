package com.example.dailypic.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import coil.api.load
import com.example.dailypic.R
import com.example.dailypic.databinding.PictureFragmentBinding
import com.example.dailypic.viewmodel.picture.PictureData
import com.example.dailypic.viewmodel.picture.PictureViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PictureFragment : Fragment() {

    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }
    private var wikiIsShown: Boolean = false
    private lateinit var vb: PictureFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PictureFragmentBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner, { renderData(it) })
        view.findViewById<TextInputLayout>(R.id.input_layout).setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/${
                        view.findViewById<TextInputEditText>(R.id.input_edit_text).text.toString()
                    }"
                )
            })
        }
        setAppBar()
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
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureData) {
        when (data) {
            is PictureData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    view!!.findViewById<ImageView>(R.id.top_picture).load(url) {
                        lifecycle(this@PictureFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_foreground)
                    }
                    view!!.findViewById<TextView>(R.id.bottom_sheet_description).text =
                        serverResponseData.explanation
                    view!!.findViewById<TextView>(R.id.bottom_sheet_description_header).text =
                        serverResponseData.title
                }
            }
            is PictureData.Loading -> {
            }
            is PictureData.Error -> {
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
            activity?.let {
                BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
            }
        }
        vb.fab.setOnClickListener {
            val deltaVertical = vb.inputLayout.height.toFloat()
            if (wikiIsShown) {
                vb.inputLayout.animate().translationYBy(-deltaVertical)
                vb.bottomSheetLine2.animate().translationYBy(-deltaVertical)
                vb.bottomSheetDescriptionHeader.animate().translationYBy(-deltaVertical)
                vb.bottomSheetDescription.animate().translationYBy(-deltaVertical)
//                vb.motionContainer.transitionToStart()
                vb.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_wikipedia_svgrepo_com))
                vb.fab.animate()
                    .rotationBy(360f)
                    .setDuration(200).setInterpolator(FastOutSlowInInterpolator())
                wikiIsShown = false
            } else {
                vb.inputLayout.animate().translationYBy(deltaVertical)
                vb.bottomSheetLine2.animate().translationYBy(deltaVertical)
                vb.bottomSheetDescriptionHeader.animate().translationYBy(deltaVertical)
                vb.bottomSheetDescription.animate().translationYBy(deltaVertical)
//                vb.motionContainer.transitionToEnd()
                vb.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                ObjectAnimator.ofFloat(vb.fab, "rotation",360f,0f).start()
                wikiIsShown = true
            }

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            vb.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                vb.topToolbar.isSelected = vb.scrollView.canScrollVertically(-1)
                vb.fab.isSelected = vb.scrollView.canScrollVertically(-1)
            }
        }
    }


    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureFragment()
        private var isMain = true
    }
}
