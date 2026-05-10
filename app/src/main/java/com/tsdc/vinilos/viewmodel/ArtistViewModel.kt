package com.tsdc.vinilos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Artist
import com.tsdc.vinilos.repository.ArtistRepository
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ArtistViewModel(
    private val repository: ArtistRepository = ArtistRepository()
) : ViewModel() {

    private val _artists = MutableLiveData<List<Artist>>(emptyList())
    val artists: LiveData<List<Artist>> get() = _artists

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _artist = MutableLiveData<Artist?>()
    val artist: LiveData<Artist?> get() = _artist

    private val _artistAlbums = MutableLiveData<List<Album>>(emptyList())
    val artistAlbums: LiveData<List<Album>> get() = _artistAlbums

    private val _albumsLoading = MutableLiveData<Boolean>(false)
    val albumsLoading: LiveData<Boolean> get() = _albumsLoading

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _artists.value = repository.getArtists()
            } catch (e: SocketTimeoutException) {
                _error.value = "Servidor iniciando, intenta nuevamente"
                _artists.value = emptyList()
            } catch (e: UnknownHostException) {
                _error.value = "Sin conexion a internet"
                _artists.value = emptyList()
            } catch (e: Exception) {
                _error.value = "Error cargando artistas"
                _artists.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchArtistDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _artist.value = repository.getArtist(id)
            } catch (e: SocketTimeoutException) {
                _error.value = "Servidor iniciando, intenta nuevamente"
            } catch (e: UnknownHostException) {
                _error.value = "Sin conexion a internet"
            } catch (e: Exception) {
                _error.value = "Error cargando informacion del artista"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchArtistAlbums(artistName: String) {
        viewModelScope.launch {
            _albumsLoading.value = true
            try {
                _artistAlbums.value = repository.getAlbumsByArtist(artistName)
            } catch (e: SocketTimeoutException) {
                _artistAlbums.value = emptyList()
            } catch (e: UnknownHostException) {
                _artistAlbums.value = emptyList()
            } catch (e: Exception) {
                _artistAlbums.value = emptyList()
            } finally {
                _albumsLoading.value = false
            }
        }
    }
}
