package cl.mcsalazar.examen_p2.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.mcsalazar.examen_p2.data.Aplicacion
import cl.mcsalazar.examen_p2.data.Cobro
import cl.mcsalazar.examen_p2.data.CobroDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CobrosViewModel(private val cobrosDao: CobroDao): ViewModel() {

    var cobros by mutableStateOf(listOf<Cobro>())

    fun insertarCobro(cobro: Cobro){
        viewModelScope.launch(Dispatchers.IO) {
            cobrosDao.addCobro(cobro)
            obtenerCobros()
        }
    }

    fun obtenerCobros(){
        viewModelScope.launch(Dispatchers.IO) {
            val res = cobrosDao.getCobro()
            cobros = res
        }
    }

    fun eliminarCobro(cobro: Cobro){
        viewModelScope.launch(Dispatchers.IO){
            cobrosDao.deleteCobro(cobro)
            obtenerCobros()
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                CobrosViewModel(aplicacion.cobroDao)
            }
        }
    }
}