package com.capstone.jadi.ml.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.jadi.R
import com.capstone.jadi.data.remote.ApiResponse
import com.capstone.jadi.ml.Classifier
import com.capstone.jadi.ml.ClassifierViewModel
import com.capstone.jadi.ml.upload.ImageClassifierActivity
import com.capstone.jadi.presentation.camera.CameraActivity
import com.capstone.jadi.presentation.history.HistoryActivity
import com.capstone.jadi.utils.ConstVal
import com.capstone.jadi.utils.SessionManager
import com.capstone.jadi.utils.ext.gone
import com.capstone.jadi.utils.ext.show
import com.capstone.jadi.utils.ext.showOKDialog
import com.capstone.jadi.utils.ext.showToast
import com.capstone.jadi.utils.reduceFileImage
import com.capstone.jadi.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class ScanDiseaseActivity : AppCompatActivity(), View.OnClickListener {

    private val classifierViewModel: ClassifierViewModel by viewModels()

    private val mInputSize = 224
    private val mModelPath = "converted_model.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: Classifier

    lateinit var bitmap: Bitmap

    private var uploadFile: File? = null
    private var token: String? = null

    private lateinit var pref: SessionManager

    lateinit var img_view : ImageView
    lateinit var textHasil: TextView
    private lateinit var tvOutput : TextView
    lateinit var upload: TextView
    lateinit var btnBack: Button
    lateinit var progressBar: ProgressBar

    companion object{
        fun start(context: Context){
            val intent = Intent(context, ScanDiseaseActivity::class.java)
            context.startActivity(intent)
        }
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_disease)

        pref = SessionManager(this)
        token = pref.getToken

        tvOutput = findViewById(R.id.hasil)
        img_view = findViewById(R.id.iv_1)
        upload = findViewById(R.id.Upload)
        btnBack = findViewById(R.id.btnBack)
        textHasil = findViewById(R.id.hasil)
        progressBar = findViewById(R.id.progress_bar)

        //camera and gallery permission
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                ScanDiseaseActivity.REQUIRED_PERMISSIONS,
                ConstVal.REQUEST_CODE_PERMISSIONS
            )
        }

        //open camera
        val intent = Intent(this, CameraActivity::class.java)
        launchIntentCamera.launch(intent)

        initClassifier()
        initViews()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!allPermissionsGranted()) {
            showToast(getString(R.string.message_not_permitted))
        }
    }

    private fun allPermissionsGranted() = ScanDiseaseActivity.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun initClassifier() {
        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
    }

    private fun initViews() {

        findViewById<ImageView>(R.id.iv_1).setOnClickListener(this)

        upload.setOnClickListener {
            uploadImage()
        }

        btnBack.setOnClickListener {
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 250)
        }

    }

    override fun onClick(view: View?) {
        val bitmap = ((view as ImageView).drawable as BitmapDrawable).bitmap

        val result = classifier.recognizeImage(bitmap)

        tvOutput.text = result.get(0).title

        runOnUiThread { Toast.makeText(this, result.get(0).title, Toast.LENGTH_SHORT).show() }
    }

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == ConstVal.CAMERA_X_RESULT) {

            val file = it?.data?.getSerializableExtra(ConstVal.KEY_PICTURE) as File

            uploadFile = file

            val result = BitmapFactory.decodeFile(file.path)

            img_view.setImageBitmap(result)
        }
    }

    private fun uploadImage() {
        if (uploadFile != null) {
            val file = reduceFileImage(uploadFile as File)
            val name = textHasil.text
            if (name == getString(R.string.message_detection)) {
                textHasil.requestFocus()
                textHasil.error = getString(R.string.error_desc_same)
            } else {
                val nameDisease= name.toString().toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart = MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    requestImageFile
                )
                classifierViewModel.addNewHistory("Bearer $token",  nameDisease, imageMultipart).observe(this){ response ->
                    when(response){
                        is ApiResponse.Loading -> {
                            showLoading(true)
                        }
                        is ApiResponse.Success -> {
                            showLoading(false)
                            showToast(getString(R.string.message_success_upload))
                            finish()
                            HistoryActivity.start(this)
                        }
                        is ApiResponse.Error -> {
                            showLoading(false)
                            showOKDialog(getString(R.string.title_upload_info), response.errorMessage)
                        }
                        else -> {
                            showLoading(false)
                            showToast(getString(R.string.message_unknown_state))
                        }
                    }
                }

            }
        } else {
            showOKDialog(getString(R.string.title_message), getString(R.string.message_pick_image))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.show() else progressBar.gone()
        apply {
            btnBack.isClickable = !isLoading
        }
    }
}