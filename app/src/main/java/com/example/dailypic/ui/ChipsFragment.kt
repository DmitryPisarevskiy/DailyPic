package com.example.dailypic.ui

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dailypic.R
import com.example.dailypic.databinding.FragmentChipsBinding
import com.example.dailypic.databinding.PictureFragmentBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipsFragment : Fragment() {

    lateinit var vb: FragmentChipsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentChipsBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                val themedContext = ContextThemeWrapper (context, R.style.Theme_DailyPic_Black)
                when (position) {
                    1 -> {
                        ThemeHolder.theme = R.style.Theme_DailyPic
                    }
                    2 -> {
                        ThemeHolder.theme = R.style.Theme_DailyPic_Black
                    }
                    3 -> {
                        ThemeHolder.theme = R.style.Theme_DailyPic_Gray
                    }
                    4 -> {
                        ThemeHolder.theme = R.style.Theme_DailyPic_Blue
                    }
                    5 -> {
                        ThemeHolder.theme = R.style.Theme_DailyPic_Pink
                    }
                    6 -> {
                        ThemeHolder.theme = R.style.Theme_DailyPic_Violet
                    }
                }
                requireActivity().recreate()
            }
        }
    }
}
