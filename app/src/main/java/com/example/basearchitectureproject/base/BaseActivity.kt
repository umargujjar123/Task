package com.example.basearchitectureproject.base

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.ContactsContract
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider.getUriForFile
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.basearchitectureproject.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_dialogue_new_design.*
import kotlinx.coroutines.*
import java.io.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*


abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    var dataBinding: DB? = null

    val SIGNATURE_STORAGE_PERMISSION = 144

    var context: Context? = null
    var MY_REQUEST_CODE_READ_CONTACTS = 15

    var MY_REQUEST_CODE = 12
    var MY_REQUEST_CODE_STORAGE = 13
    var MY_READ_PERMISSION_REQUEST_CODE = 1
    private var mDialog: Dialog? = null
    private var mUpdateDialog: Dialog? = null
    var imageToUploadUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        context = this

        mDialog = Dialog(context!!)
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog!!.setContentView(R.layout.custom_dialogue_new_design)
        mDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataBinding = DataBindingUtil.setContentView(this, getResLayout())
    }


    fun isConnectedToInternet(): Boolean {
        val connectivity =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnectedOrConnecting) {

        } else {
            showErrorSnakbar("Check your Internet connection")
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting

    }


    @LayoutRes
    abstract fun getResLayout(): Int

    fun roundValue(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    fun hideKeyboard() {
        try {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: java.lang.Exception) {
            // TODO: handle exception
        }
    }

    fun showErrorSnakbar(Error: String) {
        if (!mDialog!!.isShowing && !this.isFinishing) {
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content), Error,
                Snackbar.LENGTH_LONG
            ).setAction("Action", null)
            snackbar.setActionTextColor(Color.RED)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.WHITE)
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.RED)
            textView.textSize = 15f
            snackbar.show()
        }
    }


    fun showSucessSnakbar(Error: String) {
        if (!mDialog!!.isShowing && !this.isFinishing) {
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content), Error,
                Snackbar.LENGTH_LONG
            ).setAction("Action", null)
            snackbar.setActionTextColor(Color.RED)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(resources.getColor(R.color.quantum_teal))
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 15f
            snackbar.show()
        }
    }

    fun IsEmailValid(email: String): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_REQUEST_CODE_READ_CONTACTS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val contactPickerIntent = Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
            )
            startActivityForResult(contactPickerIntent, 1)
        }

        if (requestCode == MY_READ_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }


        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_REQUEST_CODE
                )
            } else {
                val f = File(context!!.cacheDir, "images")
                f.mkdir()
                val newFile = File(f, "default_image.jpg")
                newFile.createNewFile()
                imageToUploadUri = getUriForFile(
                    Objects.requireNonNull(applicationContext),
                    "g5.consultency.cuitalibilam.fileprovider", newFile
                );
//                    val myDate =
//                        DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
//                    val dateArray = myDate.split(Pattern.quote(" ").toRegex()).toTypedArray()
//                    val photo = "Photo_" + dateArray[0] + ".jpeg"
                val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                chooserIntent.putExtra(
                    MediaStore.EXTRA_SCREEN_ORIENTATION,
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                )
//                imageToUploadUri = getContentResolver()
//                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
                chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri)
//                chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                startActivityForResult(Intent.createChooser(chooserIntent, "Select Picture"), 0)

            }
        }


    }


    companion object {
        open fun printLog(tag: String, message: String) {
        }

    }


    open fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    fun chooseFromCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_REQUEST_CODE
            )
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_REQUEST_CODE_STORAGE
                )
            } else {
                try {

                    val f = File(context!!.cacheDir, "images")
                    f.mkdir()
                    val newFile = File(f, "default_image.jpg")
                    newFile.createNewFile()
                    imageToUploadUri = getUriForFile(
                        Objects.requireNonNull(applicationContext),
                        "g5.consultency.cuitalibilam.fileprovider", newFile
                    );
//                    val imageToUploadUri: Uri =
//                        getUriForFile(context!!, ".fileProvider", newFile)
//                    val myDate =
//                        DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
//                    val dateArray = myDate.split(Pattern.quote(" ").toRegex()).toTypedArray()
//                    val photo = "Photo_" + dateArray[0] + ".jpeg"
                    Log.e("TAG", "chooseFromCamera: Uri = $imageToUploadUri")
                    val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    chooserIntent.putExtra(
                        MediaStore.EXTRA_SCREEN_ORIENTATION,
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    )
//                    imageToUploadUri = getContentResolver()
//                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
                    chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri)
//                    chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                    startActivityForResult(Intent.createChooser(chooserIntent, "Select Picture"), 0)
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = managedQuery(contentUri, proj, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } catch (e: Exception) {
            contentUri.path
        }
    }


    fun compressImage(filePath: String?): String? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return filePath
        }


        val file = File(filePath)
        val file_size = (file.length() / 1024).toString().toInt()
        var scaledBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)
        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        val maxHeight = 512.0f
        val maxWidth = 512.0f
        var imgRatio = (actualWidth / actualHeight).toFloat()
        val maxRatio = maxWidth / maxHeight
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }
        options.inSampleSize = calculateSampleSize(
            options, actualWidth,
            actualHeight
        )


        //      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false


        //      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)
        try {

            //          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        try {
            scaledBitmap = Bitmap.createBitmap(
                actualWidth,
                actualHeight, Bitmap.Config.ARGB_8888
            )
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bmp,
            middleX - bmp.width / 2,
            middleY - bmp.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )


        //      check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0
            )
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
            } else if (orientation == 3) {
                matrix.postRotate(180f)
            } else if (orientation == 8) {
                matrix.postRotate(270f)
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap!!, 0, 0,
                scaledBitmap!!.width, scaledBitmap!!.height, matrix,
                true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var out: FileOutputStream? = null
        val filename: String = getFilename()!!
        try {
            out = FileOutputStream(filename)


            //          write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return filename
    }

    fun getFilename(): String? {
        val file = File(Environment.getExternalStorageDirectory().path, "Foldername/Images")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
    }


    fun calculateSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
    }



    fun chooseFromGallery() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_READ_PERMISSION_REQUEST_CODE
                )
            }
        }

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun getPath(context: Context?, uri: Uri?): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        if (uri != null) {
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)


                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(
                        this,
                        contentUri,
                        null,
                        null
                    )
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs: Array<String?>? = arrayOf(
                        split[1]
                    )
                    return getDataColumn(
                        this,
                        contentUri,
                        selection,
                        selectionArgs
                    )
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {

                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                    this,
                    uri,
                    null,
                    null
                )
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
        }
        return null
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            val cw = ContextWrapper(applicationContext)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            file = File(directory, "UniqueFileName" + ".jpg")
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String?>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }


    abstract class BaseActivityOnSwipeTouchListener(ctx: Context?) : View.OnTouchListener {
        private val gestureDetector: GestureDetector
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                var result = false
                try {
                    val diffY = e2.y - e1.y
                    val diffX = e2.x - e1.x
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                            result = true
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                        result = true
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
                return result
            }

            private val SWIPE_THRESHOLD = 50
            private val SWIPE_VELOCITY_THRESHOLD = 50

        }

        abstract fun onSwipeRight()
        abstract fun onSwipeLeft()
        fun onSwipeTop() {}
        fun onSwipeBottom() {}

        init {
            gestureDetector = GestureDetector(ctx, GestureListener())
        }
    }


    override fun onPause() {
        super.onPause()

        if (mDialog != null) {
            if (mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
        }
        if (mUpdateDialog != null) {
            if (mUpdateDialog!!.isShowing) {
                mUpdateDialog!!.dismiss()
            }
        }
    }


    fun showCustomDialogueNewDesign(msg: String) {
        if (mDialog!!.isShowing) {
            UpdateCustomDialogueNewDesign(msg)
        } else {
            mDialog!!.messagetv!!.setText(msg)
            mDialog!!.setCancelable(false)
            mDialog!!.show()
        }
    }

    fun UpdateCustomDialogueNewDesign(msg: String) {
        mDialog!!.messagetv!!.setText(msg)
    }

    fun hideCustomDialogueNewDesign() {
        mDialog!!.dismiss()
    }


}
