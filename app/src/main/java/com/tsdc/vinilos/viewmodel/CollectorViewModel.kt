package com.tsdc.vinilos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.repository.CollectorRepository
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CollectorViewModel(
    private val repository: CollectorRepository = CollectorRepository()
) : ViewModel() {

    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> get() = _collectors

    private val _collector = MutableLiveData<Collector>()
    val collector: LiveData<Collector> get() = _collector

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        fetchCollectors()
    }

    fun fetchCollectors() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _collectors.value = repository.getCollectors()
            } catch (e: SocketTimeoutException) {
                _error.value = "El servidor está iniciando. Reintenta en unos segundos."
            } catch (e: UnknownHostException) {
                _error.value = "Sin conexión a internet."
            } catch (e: Exception) {
                _error.value = "Error al cargar coleccionistas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchCollector(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _collector.value = repository.getCollector(id)
            } catch (e: Exception) {
                _error.value = "Error al cargar coleccionista: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        repository.clearCache()
        fetchCollectors()
    }
}