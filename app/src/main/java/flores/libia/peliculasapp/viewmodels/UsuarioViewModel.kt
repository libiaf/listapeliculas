package flores.libia.peliculasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import flores.libia.peliculasapp.R
import flores.libia.peliculasapp.modelos.Repositorio
import flores.libia.peliculasapp.modelos.Usuario

class UsuarioViewModel(val repo: Repositorio) : ViewModel() {

    private val _usuarios = mutableStateOf<List<Usuario>>(emptyList())
    val usuarios: State<List<Usuario>> = _usuarios


    init {
        getUsuarios()
    }
    private fun getUsuarios(){
        _usuarios.value = repo.getUsuarios()

    }

    fun agregarUsuario(nombre: String, correo: String, edad: Int, fotoUri: String?){
        val nuevoId = usuarios.value.size + 1
        val usu = Usuario(nuevoId, nombre, correo, edad, R.drawable.mujer, fotoUri)
        repo.agregarUsuario(usu)

        _usuarios.value = repo.getUsuarios()

    }

    fun eliminarUsuario(usuario: Usuario){
        repo.eliminarUsuario(usuario)
        _usuarios.value = repo.getUsuarios()
    }

    fun editarUsuario(id: Int, nombre: String, correo: String, edad: Int, fotoUri: String?){
        val usu = Usuario(id, nombre, correo, edad, R.drawable.mujer, fotoUri)
        repo.editarUsuario(usu)
        _usuarios.value = repo.getUsuarios()
    }


}