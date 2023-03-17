package com.example.basearchitectureproject.login


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.databinding.ActivityLoginBinding
import com.example.basearchitectureproject.login.model.LoginDataModel
import com.example.basearchitectureproject.login.viewModel.LoginViewModel
import com.example.basearchitectureproject.util.Resource
import com.example.basearchitectureproject.util.SnacksBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val TAG = "MainActivity"


    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var date = "Mar 10, 2016 6:30:00 PM"
        var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
        val newDate: Date = spf.parse(date)
        spf = SimpleDateFormat("dd MMM yyyy")
        date = spf.format(newDate)
        println(date)
        initiateBinding()
        observeLoginNavigate()


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initiateBinding() {
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        with(binding) {
            lifecycleOwner = this@LoginActivity
            viewModelM = this@LoginActivity.viewModel
            loginDataModel = this@LoginActivity.viewModel.loginDataModel

        }
    }


    private fun observeLoginNavigate() {
        this@LoginActivity.viewModel.loinUiModel.observe(this) {

            Log.e(TAG, "handleLoginNavigate: " + it?.data?.code)


            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressbar.visibility = View.INVISIBLE
                    when (it.data?.code) {
                        "200" -> {
                            Log.e(TAG, "handleLoginNavigate: " + it.data.message)
                            binding.password.setText("")
                            binding.email.setText("")
                            viewModel.loginDataModel = LoginDataModel("", "")

                        }
                        "201" -> {
                            SnacksBar.errorSnackBar(binding.root, it.data.message, this)


                        }
                    }

                    // Handel Accordingly
                }
                Resource.Status.ERROR -> {
                    binding.progressbar.visibility = View.INVISIBLE
                    it.message?.let { it1 -> SnacksBar.errorSnackBar(binding.root, it1, this) }

                    // Hide Loader
                    // Show appropriate message
                }
                Resource.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                    //Show Loader
                }
                Resource.Status.FAILURE -> {
                    binding.progressbar.visibility = View.INVISIBLE
                    it.message?.let { it1 -> SnacksBar.errorSnackBar(binding.root, it1, this) }
                }

            }


        }
    }


}