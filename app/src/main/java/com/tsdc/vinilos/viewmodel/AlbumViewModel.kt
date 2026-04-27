package com.tsdc.vinilos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.repository.AlbumRepository
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AlbumViewModel : ViewModel() {

    private val repository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        fetchAlbums()
    }

    fun fetchAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _albums.value = repository.getAlbums()
            } catch (e: SocketTimeoutException) {
                _error.value = "El servidor está iniciando. Reintenta en unos segundos."
            } catch (e: UnknownHostException) {
                _error.value = "Sin conexión a internet."
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        repository.clearCache()
        fetchAlbums()
    }
}