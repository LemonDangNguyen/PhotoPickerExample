package com.example.pickerphoto


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var imageAdapter: ImageAdapter
    private val selectedImagesUris = mutableListOf<Uri>()

    private val pickMultipleImagesLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        // Xử lý danh sách URI của các ảnh được chọn
        if (uris.isNotEmpty()) {
            selectedImagesUris.clear()
            selectedImagesUris.addAll(uris)
            imageAdapter.notifyDataSetChanged()
        } else {
            Log.d("ImagePicker", "No images selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectImages: Button = findViewById(R.id.btnSelectImages)
        val recyclerView: RecyclerView = findViewById(R.id.all_images_recycler_view)

        // Thiết lập RecyclerView với Adapter
        imageAdapter = ImageAdapter(selectedImagesUris)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = imageAdapter

        // Gọi trình chọn ảnh khi nhấn nút
        btnSelectImages.setOnClickListener {
            pickMultipleImagesLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

    }
}
