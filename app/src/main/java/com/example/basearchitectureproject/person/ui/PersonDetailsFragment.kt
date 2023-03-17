package com.example.basearchitectureproject.person.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseFragment
import com.example.basearchitectureproject.databinding.FragmentPersonDetailsBinding

class PersonDetailsFragment : BaseFragment<FragmentPersonDetailsBinding>() {

    val args: PersonDetailsFragmentArgs by navArgs()
    override fun OnCreateView(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        val person  = args.personarg
    }

    override fun getlayout(): Int {
        return R.layout.fragment_person_details
    }
}