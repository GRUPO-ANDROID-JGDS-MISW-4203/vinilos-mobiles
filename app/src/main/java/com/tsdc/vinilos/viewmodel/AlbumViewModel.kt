package com.tsdc.vinilos.viewmodel

import androidx.lifecycle.*
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.AlbumRequest
import com.tsdc.vinilos.model.TrackRequest
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

    private val _success = MutableLiveData<String?>()
    val success: LiveData<String?> get() = _success

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

    fun createAlbum(req: AlbumRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _success.value = null
            try {
                repository.createAlbum(req)
                _success.value = "Álbum creado exitosamente"
            } catch (e: Exception) {
                _error.value = "Error al crear álbum: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addTrack(albumId: Int, req: TrackRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _success.value = null
            try {
                repository.addTrack(albumId, req)
                _success.value = "Track agregado exitosamente"
            } catch (e: Exception) {
                _error.value = "Error al agregar track: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}