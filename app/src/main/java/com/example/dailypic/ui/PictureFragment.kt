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
import androidx.core.content.ContextCompat
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

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
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
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
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
            R.id.app_bar_search -> {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN || bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
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
                //showLoading()
            }
            is PictureData.Error -> {
                //showError(data.error.message)
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
        vb.topToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.app_bar_fav -> {
                    toast("Favourite")
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
                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN || bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    } else {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                    true
                }
                else -> false
            }
        }
        vb.fab.setOnClickListener {
            if (wikiIsShown) {
                vb.motionContainer.transitionToStart()
                vb.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_wikipedia_svgrepo_com))
                wikiIsShown = false
            } else {
                vb.motionContainer.transitionToEnd()
                vb.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                wikiIsShown = true
            }

        }
    }


    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
