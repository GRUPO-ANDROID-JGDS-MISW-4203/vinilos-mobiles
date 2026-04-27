package com.tsdc.vinilos.viewmodel

import androidx.lifecycle.*
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.repository.AlbumRepository
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AlbumViewModel : ViewModel() {

    private val repository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> get() = _album

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _albums.value = repository.getAlbums()
            } catch (e: Exception) {
                _error.value = "Error cargando álbumes"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAlbum(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _album.value = repository.getAlbum(id)
            } catch (e: SocketTimeoutException) {
                _error.value = "Servidor iniciando, intenta nuevamente"
            } catch (e: UnknownHostException) {
                _error.value = "Sin conexión a internet"
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}