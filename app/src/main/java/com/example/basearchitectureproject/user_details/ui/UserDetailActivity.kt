package com.example.basearchitectureproject.user_details.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseActivity
import com.example.basearchitectureproject.databinding.UserDetailsLayoutBinding
import com.example.basearchitectureproject.user_details.viewmodel.UploadUserDetailViewModel
import com.google.firebase.FirebaseApp
//import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*

@AndroidEntryPoint
class UserDetailActivity : BaseActivity<UserDetailsLayoutBinding>() {
    val uploadDetailViewModel: UploadUserDetailViewModel by viewModels()

    private var imagePath = ""
    private var compressedLogoFile: File? = null
    private var logoPath = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initObserver()
    }

    private fun initObserver() {

        uploadDetailViewModel.rootClick.observe(this@UserDetailActivity) {
            hideKeyboard()
        }

//        uploadDetailViewModel.selectedImageUri.observe(this@UserDetailActivity) {
//            Log.e("TAG", "initObserver: sleected URI is $it")
//            if(it != null) {
//                val bitmap = uriToBitmap(it)
//                uploadDetailViewModel.setProcessedBitmap(bitmap)
//                Glide.with(this)
//                    .load(bitmap)
//                    .into(dataBinding.userImage)
//
//            } else {
//                showErrorSnakbar("Failed to process image")
//            }
//
//        }

        uploadDetailViewModel.userDetails.observe(this@UserDetailActivity)
        {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showCustomDialogueNewDesign("Restoring data...")
                }
                Resource.Status.SUCCESS -> {
                    hideCustomDialogueNewDesign()
                    showSucessSnakbar(it.message ?: "")
                }
                Resource.Status.ERROR -> {
                    hideCustomDialogueNewDesign()
                    showErrorSnakbar(it.message ?: "")
                }
                Resource.Status.FAILURE -> {
                    hideCustomDialogueNewDesign()
                    showErrorSnakbar(it.message ?: "")
                }
            }
        }

        uploadDetailViewModel.backBtn.observe(this@UserDetailActivity)
        {
            finish()
        }

//        uploadDetailViewModel.initUploadDetailLD.observe(this@UserDetailActivity)
//        {
//            when (it.status) {
//                Resource.Status.LOADING -> {
//                    showCustomDialogueNewDesign("Updating Information...")
//                }
//                Resource.Status.SUCCESS -> {
//                    hideCustomDialogueNewDesign()
//                    it.message?.let { successMessage ->
//                        showSucessSnakbar(successMessage)
//                    }
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        withContext(Dispatchers.Main) {
//                            dataBinding?.btnSave?.isEnabled = false
//                        }
//                        delay(1500)
//                    }
//                }
//                Resource.Status.ERROR -> {
//                    hideCustomDialogueNewDesign()
//                    showErrorSnakbar(it.message ?: "")
//                }
//                Resource.Status.FAILURE -> {
//                    hideCustomDialogueNewDesign()
//                    showErrorSnakbar(it.message ?: "")
//                }
//            }
//        }
    }

    private fun initDataBinding() {
        with(dataBinding!!) {
            lifecycleOwner = this@UserDetailActivity
//            viewmodel = this@UserDetailActivity.uploadDetailViewModel
        }
    }

    override fun getResLayout(): Int {
        return R.layout.user_details_layout
    }

}