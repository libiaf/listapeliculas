package flores.libia.peliculasapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import flores.libia.peliculasapp.modelos.PeliculaRepositorio

class PeliculaViewModelFactory(private val repo: PeliculaRepositorio): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        if (modelClass.isAssignableFrom(PeliculaViewModel::class.java)){
            return PeliculaViewModel(repo) as T
        }
        throw IllegalArgumentException("Desconocida")
    }
}
