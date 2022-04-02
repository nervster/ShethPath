package com.example.shethpath.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.shethpath.MainActivity
import com.example.shethpath.Post
import com.example.shethpath.R
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File


open class ComposeFragment : Fragment() {

    var photoFile: File? = null
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    val photoFileName = "photo.jpg"
    lateinit var ivPreview: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPreview = view.findViewById(R.id.ivImage)

        view.findViewById<Button>(R.id.postSubmitButton).setOnClickListener {
            // grab description that they have inputted
            val description = view.findViewById<EditText>(R.id.etDescription).text.toString()
            val user = ParseUser.getCurrentUser()
            if (photoFile != null) {
                submitPost(description, user, photoFile!!)
            } else {
                // Show error log message
            }

        }

        val takePicture: Button = view.findViewById(R.id.pictureButton)
        takePicture.setOnClickListener {
            onLaunchCamera()
        }



    }

    fun submitPost(description: String, user: ParseUser, file: File) {
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))


        post.saveInBackground {
                exception ->
            if (exception != null) {
                Log.e(MainActivity.TAG, "Error while saving post")
                exception.printStackTrace()
            } else {
                Log.i(MainActivity.TAG, "Successfully saved post")
                // reset description and image view to empty
//                    finish()
            }
        }

    }

    fun onLaunchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        photoFile = getPhotoFileUri(photoFileName)

        if (photoFile != null) {
            val fileProvider: Uri = FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        }

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        }
    }

    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), MainActivity.TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(MainActivity.TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPreview.setImageBitmap(takenImage)
            } else { // Result was a failure
                Toast.makeText(requireContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }



}