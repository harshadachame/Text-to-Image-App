package com.example.texttoimageapp

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class TextToImageViewModel : ViewModel() {
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    fun generateImage(promptText: String) {
        viewModelScope.launch {
            try {
                val mediaType = "text/plain".toMediaType()
                val promptBody = promptText.toRequestBody(mediaType)
                val outputFormatBody = "webp".toRequestBody(mediaType)

                val responseBody = ApiClient.api.generateImage(promptBody, outputFormatBody)
                val imageBytes = responseBody.bytes()


                val base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                _imageUrl.value = "data:image/webp;base64,$base64"
            } catch (e: Exception) {

                Log.e("TextToImage", "Error: ${e.message} ")
            }
        }
    }


}
