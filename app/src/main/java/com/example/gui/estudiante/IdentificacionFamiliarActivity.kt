package com.example.gui.estudiante

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.gui.R
import java.util.Random

class IdentificacionFamiliarActivity : ComponentActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.identificacionfamiliar)

        imageView = findViewById(R.id.imageView)

        imageView.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.botonGuardar).setOnClickListener {
            finish() // Cierra esta actividad y regresa
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageBitmap?.let {
                imageView.setImageBitmap(it)
                saveImageToGallery(it)
                var imageToBytes = convertirFotoBytes(it);
                println(imageToBytes)
                Log.d("array", imageToBytes.toString())
            }
        }
    }

    private fun convertirFotoBytes(it: Bitmap): ByteArray {
        val stream = java.io.ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.PNG,100,stream)
        return stream.toByteArray();
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val valorFotoRandom = (Math.random()*10)

        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "foto_"+valorFotoRandom+ System.currentTimeMillis(),
            "Foto tomada desde AccesITO"
        )

        if (savedImageURL != null) {
            Toast.makeText(this, "IDENTIFICACION GUARDADA", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "IDENTIFICACION NO GUARDADA", Toast.LENGTH_SHORT).show()
        }
    }
}
