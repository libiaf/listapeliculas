package flores.libia.peliculasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import flores.libia.peliculasapp.R
import flores.libia.peliculasapp.modelos.Pelicula
import flores.libia.peliculasapp.modelos.PeliculaRepositorio
import flores.libia.peliculasapp.modelos.Usuario


class PeliculaViewModel(val repo: PeliculaRepositorio) : ViewModel(){
    private val _peliculas = mutableStateOf<List<Pelicula>>(emptyList())
    val peliculas: State<List<Pelicula>> = _peliculas

    init {
        getPeliculas()
    }
    private fun getPeliculas(){
        _peliculas.value = repo.getPeliculas()

    }
    fun agregarPelicula(titulo: String, categoria: String, duracion: String, sinopsis: String, fotoUri: String?){
        val nuevoId = peliculas.value.size + 1
        val peli = Pelicula(nuevoId, titulo, categoria, duracion, sinopsis, R.drawable.movies, fotoUri)
        repo.agregarPelicula(peli)

        _peliculas.value = repo.getPeliculas()

    }

    fun eliminarPelicula(pelicula: Pelicula){
        repo.eliminarPelicula(pelicula)
        _peliculas.value = repo.getPeliculas()
    }

    fun editarPelicula(id: Int,titulo: String, categoria: String, duracion: String, sinopsis: String, fotoUri: String?){
        val peli = Pelicula(id, titulo, categoria, duracion, sinopsis, R.drawable.mujer, fotoUri)
        repo.editarPelicula(peli)
        _peliculas.value = repo.getPeliculas()
    }
}

