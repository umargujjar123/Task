package com.example.basearchitectureproject.common.extentions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.basearchitectureproject.R

fun Fragment.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    if (mayNavigate(directions)) findNavController().navigate(directions, navOptions)
}

fun Fragment.mayNavigate(directions: NavDirections? = null): Boolean {

    val navController = findNavController()
    val destinationIdInNavController = navController.currentDestination?.id

    // add tag_navigation_destination_id to your ids.xml so that it's unique:
    val destinationIdOfThisFragment =
        view?.getTag(R.id.tag_navigation_destination_id) ?: destinationIdInNavController

    // check that the navigation graph is still in 'this' fragment, if not then the app already navigated:
    if (destinationIdInNavController == destinationIdOfThisFragment) {
        view?.setTag(R.id.tag_navigation_destination_id, destinationIdOfThisFragment)

        return if (directions != null) {
            navController.currentDestination?.getAction(directions.actionId) != null
        } else {
            true
        }
    } else {
        Log.d(
            "FragmentExtensions",
            "May not navigate: current destination is not the current fragment."
        )

        return false
    }
}