package com.example.greentrip.ui.main.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentEditProfileBinding
import com.example.greentrip.utils.getFilePathFromUri
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var imgBitmap: Bitmap? = null
    private var imgUri: Uri? = null
    private lateinit var loadFileGallery: ActivityResultLauncher<String>
    private lateinit var loadFileCamera: ActivityResultLauncher<Intent>
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        checkPermission()
        collectStates()
        collectStatesEditProfile()

        loadFileGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                imgUri = it
                Glide.with(requireContext()).load(it).into(binding.imgProfile)
            }

        }
        loadFileCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    imgBitmap = data?.extras?.get("data") as Bitmap
                    imgUri = bitmapToUri(imgBitmap!!)
                    Glide.with(requireContext()).load(imgUri).into(binding.imgProfile)
                }
            }

        binding.btnCamera.setOnClickListener {
            handelPermission()
        }
        binding.btnSave.setOnClickListener {
            callApiEditProfile()
            Log.e( "onViewCreated: ",imgUri.toString() )
        }

    }

    private fun checkPermission() {

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val isCameraPermissionGranted = permissions[Manifest.permission.CAMERA] ?: false
            val isGalleryPermissionGranted =
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

            if (isCameraPermissionGranted && isGalleryPermissionGranted) {
                // Both permissions are granted, do something
                setUpBottomSheet()
            } else {
                // One or both permissions are not granted, show a message or take some other action
                Toast.makeText(
                    requireContext(),
                    "Camera and gallery permissions are required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handelPermission() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Both permissions are already granted, open the camera or gallery
            setUpBottomSheet()
        } else {
            // One or both permissions are not granted, request them
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            permissionLauncher.launch(permissions)
        }
    }

    private fun bitmapToUri(bitmap: Bitmap): Uri {
        val imageFile = File(requireContext().cacheDir, "temp_image.jpeg")
        val os = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.flush()
        os.close()
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            imageFile
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        loadFileCamera.launch(intent)
    }

    private fun openGalley() {
//        getImage().launch("image/*")
        loadFileGallery.launch("image/*")
    }

    private fun setUpBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val camera = view.findViewById<LinearLayout>(R.id.linearCamera)
        val gallery = view.findViewById<LinearLayout>(R.id.linearGallery)
        camera.setOnClickListener {
            openCamera()
            dialog.dismiss()
        }
        gallery.setOnClickListener {
            openGalley()
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }


    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.state
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {

                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading
                    if (!it.isLoading && it.status == "success"){
                        val image = "${Constants.BASEURL}/img/avatars/${it.profile?.data?.data?.avatar}"
                        Log.e( "collectStates: ", image.toString())



                        binding.editTextName.setText(it.profile?.data?.data?.name)
                        binding.txtName.text = it.profile?.data?.data?.name

                        binding.editTextPhone.setText(it.profile?.data?.data?.phone)
                        binding.editTextEmail.setText(it.profile?.data?.data?.email)
                        Glide.with(requireContext()).load(image).into(binding.imgProfile)
                    }


                }

        }
    }

    private fun callApiEditProfile() {
        lifecycleScope.launch {
            if (imgUri != null) {
                viewModel.editProfile(
                    fileUri = imgUri,
                    name = binding.editTextName.text.toString(),
                    phone = binding.editTextPhone.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    ctx = requireContext(),
                    fileRealPath = getFilePathFromUri(
                        requireContext(),
                        imgUri!!,
                        viewModel
                    ).toString()
                )
            } else {
                viewModel.editProfile(
                    name = binding.editTextName.text.toString(),
                    phone = binding.editTextPhone.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    ctx = requireContext(),
                )
            }

        }
    }

    private fun collectStatesEditProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state1
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {

                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading &&it.status != null) {
                        Toast.makeText(requireContext(), it.status, Toast.LENGTH_SHORT).show()
                    }
                    if (!it.isLoading && it.status == "success") {
                        findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)

                    }
                }
        }
    }

}