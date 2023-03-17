package com.example.basearchitectureproject.person.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseFragment
import com.example.basearchitectureproject.base.Resource
import com.example.basearchitectureproject.databinding.UserDetailsLayoutBinding
import com.example.basearchitectureproject.person.viewmodel.AddPersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class AddPersonFragment : BaseFragment<UserDetailsLayoutBinding>() {

    private val addPersonsViewModel by viewModels<AddPersonViewModel>()


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding?.let {
            with(it) {
                lifecycleOwner = this@AddPersonFragment
                viewModel = addPersonsViewModel
            }
        }

//
//        addPersonsViewModel.addPersonAction.observe(viewLifecycleOwner) {
//            Log.e("TAG", "OnCreateView --> addPersonAction: called")
//            addPersonsViewModel.addPerson(Person(name = dataBinding!!.pName.text.toString(), age = dataBinding!!.pAge.text.toString().toDouble()))
//        }

        addPersonsViewModel.uploadPersonHandlerLD.observe(viewLifecycleOwner) {
            Log.e("TAG", "onViewCreated: $it")
            if (it.status == g5.consultency.cuitalibilam.base.util.Resource.Status.SUCCESS) {
                activity?.onBackPressed()
            }
        }

        addPersonsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) showLoader() else hideLoader()
        }

        addPersonsViewModel.backBtn.observe(viewLifecycleOwner)
        {
            activity?.onBackPressed()

        }

    }

    override fun getlayout() = R.layout.user_details_layout
    override fun OnCreateView(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    }

}