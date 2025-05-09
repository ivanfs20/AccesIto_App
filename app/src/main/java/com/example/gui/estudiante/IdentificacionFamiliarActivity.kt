package com.example.gui.estudiante

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import com.example.gui.R
import com.example.gui.data.actions.PhotoBytesAl

class IdentificacionFamiliarActivity : ComponentActivity() {

    private lateinit var imageCaptureLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.identificacionfamiliar)

        imageView = findViewById(R.id.imageView)

        // Pedir permisos si no están dados
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }

        imageCaptureLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let {
                    imageView.setImageBitmap(it)
                    saveImageToGallery(it)
                    PhotoBytesAl.photoFamiliarIdentificacion = convertirFotoBytes(it)
                }
            }
        }

        imageView.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                imageCaptureLauncher.launch(intent)
            } else {
                Toast.makeText(this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.botonGuardar).setOnClickListener {
            finish()
        }
    }

    private fun convertirFotoBytes(it: Bitmap): ByteArray {
        val stream = java.io.ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val valorFotoRandom = (Math.random() * 10)

        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "foto_" + valorFotoRandom + System.currentTimeMillis(),
            "Foto tomada desde AccesITO"
        )

        if (savedImageURL != null) {
            Toast.makeText(this, "IDENTIFICACION GUARDADA", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "IDENTIFICACION NO GUARDADA", Toast.LENGTH_SHORT).show()
        }
    }
}
