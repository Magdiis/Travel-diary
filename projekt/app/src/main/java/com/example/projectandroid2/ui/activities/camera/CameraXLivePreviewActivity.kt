package com.example.projectandroid2.ui.activities.camera


import android.content.Intent

import android.os.Build.VERSION_CODES
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.util.Log

import android.widget.ImageButton
import android.widget.TextView

import android.widget.Toast

import androidx.annotation.RequiresApi
import androidx.camera.core.Camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView

import androidx.core.content.ContextCompat

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.projectandroid2.R

import com.example.projectandroid2.mlkit.GraphicOverlay
import com.example.projectandroid2.mlkit.VisionImageProcessor

import com.example.projectandroid2.ui.activities.main.MainActivity
import com.google.android.gms.common.annotation.KeepName
import com.google.mlkit.common.MlKitException

import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

import java.lang.Exception
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Public


/** Live preview demo app for ML Kit APIs using CameraX.
 *
 *  MLKIT on samples
 * */
@KeepName
@RequiresApi(VERSION_CODES.LOLLIPOP)
class CameraXLivePreviewActivity :
    AppCompatActivity(),
    OnTextFoundListener /*,OnItemSelectedListener, CompoundButton.OnCheckedChangeListener */ {

    private var previewView: PreviewView? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null
    private var previewUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null
    private var imageProcessor: VisionImageProcessor? = null
    private var needUpdateGraphicOverlayImageSourceInfo = false
    private var selectedModel = OBJECT_DETECTION
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private var cameraSelector: CameraSelector? = null

    private lateinit var backButton : ImageButton
    val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")
        if (savedInstanceState != null) {
            selectedModel = savedInstanceState.getString(STATE_SELECTED_MODEL, OBJECT_DETECTION)
        }
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        setContentView(R.layout.activity_vision_camerax_live_preview)
        previewView = findViewById(R.id.preview_view)
        if (previewView == null) {
            Log.d(TAG, "previewView is null")
        }
        graphicOverlay = findViewById(R.id.graphic_overlay)
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null")
        }
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener{
            startActivity(Intent(this@CameraXLivePreviewActivity,MainActivity::class.java))
            finish()
        }

        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(CameraXViewModel::class.java)
            .processCameraProvider
            .observe(
                this,
                Observer { provider: ProcessCameraProvider? ->
                    cameraProvider = provider
                    bindAllCameraUseCases()
                }
            )
    }


    public override fun onResume() {
        super.onResume()
        bindAllCameraUseCases()
    }

    override fun onPause() {
        super.onPause()

        imageProcessor?.run { this.stop() }
    }

    public override fun onDestroy() {
        super.onDestroy()
        imageProcessor?.run { this.stop() }
    }

    private fun bindAllCameraUseCases() {
        if (cameraProvider != null) {
            // As required by CameraX API, unbinds all use cases before trying to re-bind any of them.
            cameraProvider!!.unbindAll()
            bindPreviewUseCase()
            bindAnalysisUseCase()
        }
    }

    private fun bindPreviewUseCase() {

        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider!!.unbind(previewUseCase)
        }

        val builder = Preview.Builder()

        previewUseCase = builder.build()
        previewUseCase!!.setSurfaceProvider(previewView!!.getSurfaceProvider())
        camera =
            cameraProvider!!.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector!!, previewUseCase)
    }

    private fun bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return
        }
        if (analysisUseCase != null) {
            cameraProvider!!.unbind(analysisUseCase)
        }
        if (imageProcessor != null) {
            imageProcessor!!.stop()
        }
        imageProcessor = TextRecognitionProcessor(this, this, TextRecognizerOptions.Builder().build())

        val builder = ImageAnalysis.Builder()

        analysisUseCase = builder.build()

        needUpdateGraphicOverlayImageSourceInfo = true

        analysisUseCase?.setAnalyzer(
            // imageProcessor.processImageProxy will use another thread to run the detection underneath,
            // thus we can just runs the analyzer itself on main thread.
            ContextCompat.getMainExecutor(this),
            ImageAnalysis.Analyzer { imageProxy: ImageProxy ->
                if (needUpdateGraphicOverlayImageSourceInfo) {
                    val isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT
                    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                    if (rotationDegrees == 0 || rotationDegrees == 180) {
                        graphicOverlay!!.setImageSourceInfo(imageProxy.width, imageProxy.height, isImageFlipped)
                    } else {
                        graphicOverlay!!.setImageSourceInfo(imageProxy.height, imageProxy.width, isImageFlipped)
                    }
                    needUpdateGraphicOverlayImageSourceInfo = false
                }
                try {
                    imageProcessor!!.processImageProxy(imageProxy, graphicOverlay)
                } catch (e: MlKitException) {
                    Log.e(TAG, "Failed to process image. Error: " + e.localizedMessage)
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
        cameraProvider!!.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector!!, analysisUseCase)
    }

    companion object {
        private const val TAG = "CameraXLivePreview"
        private const val OBJECT_DETECTION = "Object Detection"
        private const val OBJECT_DETECTION_CUSTOM = "Custom Object Detection"
        private const val CUSTOM_AUTOML_OBJECT_DETECTION = "Custom AutoML Object Detection (Flower)"
        private const val FACE_DETECTION = "Face Detection"
        private const val TEXT_RECOGNITION_LATIN = "Text Recognition Latin"
        private const val TEXT_RECOGNITION_CHINESE = "Text Recognition Chinese"
        private const val TEXT_RECOGNITION_DEVANAGARI = "Text Recognition Devanagari"
        private const val TEXT_RECOGNITION_JAPANESE = "Text Recognition Japanese"
        private const val TEXT_RECOGNITION_KOREAN = "Text Recognition Korean"
        private const val BARCODE_SCANNING = "Barcode Scanning"
        private const val IMAGE_LABELING = "Image Labeling"
        private const val IMAGE_LABELING_CUSTOM = "Custom Image Labeling (Birds)"
        private const val CUSTOM_AUTOML_LABELING = "Custom AutoML Image Labeling (Flower)"
        private const val POSE_DETECTION = "Pose Detection"
        private const val SELFIE_SEGMENTATION = "Selfie Segmentation"
        private const val FACE_MESH_DETECTION = "Face Mesh Detection (Beta)"

        private const val STATE_SELECTED_MODEL = "selected_model"
    }

    override fun onTextFound(text: Text) {

        if (text.textBlocks.size > 8){
            val mendelBlockLeft = text.textBlocks[7]
            val nameBlockLeft = text.textBlocks[6]
            val idBlockLeft = text.textBlocks[4]

            val mendelBlockRight = text.textBlocks[3]
            val nameBlockRight = text.textBlocks[4]
            val idBlockRight = text.textBlocks[5]



             //pokud je navigace vlevo
            if(mendelBlockLeft.text.contains("Mendelova univerzita v Brně")
                and nameBlockLeft.text.contains("Name | Nom | Jméno")
                and idBlockLeft.text.contains("ID Number ") ){
                if(nameBlockLeft.lines.size>1) {
                    if(!nameBlockLeft.lines[0].text.contains("Born")
                        and !nameBlockLeft.lines[0].text.contains("[0-9]+".toRegex())){

                        bundle.putString("success","success")
                        bundle.putString("name",nameBlockLeft.lines[0].text)
                        bundle.putString("id",idBlockLeft.lines[0].text)
                        val intent = Intent(this@CameraXLivePreviewActivity, MainActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }
                }
            }


            //pokud je navigace napravo
            if(mendelBlockRight.text.contains("Mendelova univerzita v Brně")
                and nameBlockRight.text.contains("Name | Nom | Jméno" )
                and idBlockRight.text.contains("ID Number")){
                if(nameBlockRight.lines.size>1){
                    if(!nameBlockRight.lines[1].text.contains("Born")
                        and !nameBlockRight.lines[1].text.contains("[0-9]+".toRegex())){
                        bundle.putString("success","success")
                        bundle.putString("name",nameBlockRight.lines[1].text)
                        bundle.putString("id",idBlockRight.lines[1].text)
                        val intent = Intent(this@CameraXLivePreviewActivity, MainActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

    }

    override fun onFailure(exception: Exception) {
        TODO("Not yet implemented")
    }


}


