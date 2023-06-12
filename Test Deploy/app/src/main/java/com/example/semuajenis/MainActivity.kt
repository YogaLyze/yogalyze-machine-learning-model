package com.example.semuajenis

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.semuajenis.ml.Flexibility
import com.example.semuajenis.ml.BackPain
import com.example.semuajenis.ml.NeckPain
import com.example.semuajenis.ml.Anxiety
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {

    lateinit var labels: List<String>
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView
    lateinit var cameraDevice: CameraDevice
    lateinit var handler: Handler
    lateinit var cameraManager: CameraManager
    lateinit var textureView: TextureView
    lateinit var model1: BackPain
    lateinit var model2: Anxiety
    lateinit var model3: Flexibility
    lateinit var model4: NeckPain
    lateinit var text: TextView
    var outputFeature0: FloatArray? = null
    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPermission()
        bundle = intent.extras
        when (bundle?.get("id")) {
            "backPain" -> {
                labels = FileUtil.loadLabels(this, "labelBackPain.txt")
            }
            "anxiety" -> {
                labels = FileUtil.loadLabels(this, "labelAnxiety.txt")
            }
            "flexibility" -> {
                labels = FileUtil.loadLabels(this, "labelFlexibility.txt")
            }
            "neckPain" -> {
                labels = FileUtil.loadLabels(this, "labelNeckPain.txt")
            }
        }
        model1 = BackPain.newInstance(this)
        model2 = Anxiety.newInstance(this)
        model3 = Flexibility.newInstance(this)
        model4 = NeckPain.newInstance(this)
        val handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        imageView = findViewById(R.id.imageView)
        text = findViewById(R.id.textView)
        textureView = findViewById(R.id.textureView)
        textureView.surfaceTextureListener = object:TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                bitmap = textureView.bitmap!!
                val bitmap1 = Bitmap.createScaledBitmap(bitmap, 100, 100, false)

                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)
                val tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(bitmap1)
                val byteBuffer = tensorImage.buffer
                inputFeature0.loadBuffer(byteBuffer)
                when (bundle?.get("id")) {
                    "backPain" -> {
                        val outputs1 = model1.process(inputFeature0)
                        outputFeature0 = outputs1.outputFeature0AsTensorBuffer.floatArray
                    }
                    "anxiety" -> {
                        val outputs2 = model2.process(inputFeature0)
                        outputFeature0 = outputs2.outputFeature0AsTensorBuffer.floatArray
                    }
                    "flexibility" -> {
                        val outputs3 = model3.process(inputFeature0)
                        outputFeature0 = outputs3.outputFeature0AsTensorBuffer.floatArray
                    }
                    "neckPain" -> {
                        val outputs4 = model4.process(inputFeature0)
                        outputFeature0 = outputs4.outputFeature0AsTensorBuffer.floatArray
                    }
                }


                val threshold = 0.5f
                val detectedLabels = mutableListOf<String>()
                val detectedScores = mutableListOf<Float>()

                outputFeature0?.forEachIndexed { index, score ->
                    if (score > threshold) {
                        detectedLabels.add(labels[index])
                        detectedScores.add(score)
                    }
                }

                val labelString = detectedLabels.zip(detectedScores)
                    .joinToString(separator = "\n") { (label, score) -> "$label $score" }
                text.text = labelString

                imageView.setImageBitmap(bitmap)

            }

        }

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    override fun onStop() {
        super.onStop()
        when (bundle?.get("id")) {
            "backPain" -> {
                model1.close()
            }
            "anxiety" -> {
                model2.close()
            }
            "flexibility" -> {
                model3.close()
            }
            "neckPain" -> {
                model4.close()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun openCamera(){
        cameraManager.openCamera(cameraManager.cameraIdList[0], object:CameraDevice.StateCallback(){
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera

                val surfaceTexture = textureView.surfaceTexture
                val surface = Surface(surfaceTexture)

                val captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                captureRequest.addTarget(surface)

                cameraDevice.createCaptureSession(listOf(surface), object: CameraCaptureSession.StateCallback(){
                    override fun onConfigured(session: CameraCaptureSession) {
                        session.setRepeatingRequest(captureRequest.build(), null, null)
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {

                    }
                }, handler)
            }

            override fun onDisconnected(camera: CameraDevice) {

            }

            override fun onError(camera: CameraDevice, error: Int) {

            }

        }, handler)
    }

    fun getPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
            getPermission()
        }
    }
}