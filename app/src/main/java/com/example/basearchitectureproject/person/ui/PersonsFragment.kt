package com.example.basearchitectureproject.person.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseFragment
import com.example.basearchitectureproject.common.EventObserver
import com.example.basearchitectureproject.common.extentions.disableShowHideAnimation
import com.example.basearchitectureproject.databinding.FragmentPersonsBinding
import com.example.basearchitectureproject.person.adopter.PersonsAdopter
import com.example.basearchitectureproject.person.viewmodel.PersonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import g5.consultency.cuitalibilam.helper.AppConstants
import g5.consultency.cuitalibilam.helper.SharedHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class PersonsFragment : BaseFragment<FragmentPersonsBinding>() {

    private val personsViewModel by viewModels<PersonsViewModel>()

    override fun OnCreateView(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding!!.viewModel = personsViewModel
        dataBinding!!.adapter = PersonsAdopter(personsViewModel)
        observeLiveDataObjects(view)
    }

    private fun observeLiveDataObjects(view: View) {
        personsViewModel.addPersonAction.observe(viewLifecycleOwner, EventObserver {

            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(
                PersonsFragmentDirections.actionPersonsFragmentToAddPersonFragment()
            )
//            addFragment(
//                android.R.id.content,
//                AddPersonFragment(),
//                AddPersonFragment::class.java.simpleName
//            )
        })

        personsViewModel.remoteSyncStateLD.observe(viewLifecycleOwner) {

        }

        personsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                showCustomDialogueNewDesign("Fetching user data")
            } else {
                hideCustomDialogueNewDesign()
                if (SharedHelper.getKey(
                        context = requireContext(),
                        AppConstants.IS_DATA_SYNCED
                    ) != "YES"
                ) {
                    if (isConnectedToInternet()) {
                        showDataNotSyncedDialogue(sycnData = {
                            personsViewModel.actionSyncData()
                        })
                    } else {
                        showErrorSnakbar("Data is not synchronized with remote server")
                    }

                }
            }
        }
//        personsViewModel.personDetailAction.observe(viewLifecycleOwner, EventObserver { person ->
//           Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(
////                PersonsFragmentDirections.actionPersonsFragmentToPersonDetailsFragment(person)
////            )
////        })


        personsViewModel.persons.observe(viewLifecycleOwner) {
            Log.e("PersonsFragment", "observeLiveDataObjects: $it")
        }


    }

    override fun onResume() {
        super.onResume()
        dataBinding?.executePendingBindings()
    }


    private fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String
    ) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(null)
            .commit()
    }

    override fun getlayout() = R.layout.fragment_persons

}