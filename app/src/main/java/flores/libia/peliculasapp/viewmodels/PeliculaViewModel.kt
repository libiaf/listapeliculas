package flores.libia.peliculasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import flores.libia.peliculasapp.modelos.Pelicula
import flores.libia.peliculasapp.modelos.PeliculaRepositorio


class PeliculaViewModel(val repo: PeliculaRepositorio) : ViewModel(){
    private val _peliculas = mutableStateOf<List<Pelicula>>(emptyList())
    val peliculas: State<List<Pelicula>> = _peliculas

    init {
        getPeliculas()
    }
    private fun getPeliculas(){
        _peliculas.value = repo.getPeliculas()

    }
}

