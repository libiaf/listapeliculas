package flores.libia.peliculasapp.modelos

import flores.libia.peliculasapp.R

class Repositorio {

    private val usuarios = mutableListOf(
        Usuario(1, "Tomas", "tomasflores@gmail.com", 20, R.drawable.bootstrap_person_circle),
        Usuario(2, "Sofia", "sofiaflores@gmail.com", 15, R.drawable.mujer),
        Usuario(3, "Diana", "dianaesc@gmail.com", 21, R.drawable.mujer),
        Usuario(4, "Kimberly", "kimberlymar@gmail.com", 20, R.drawable.mujer)
    )

    fun getUsuarios(): List<Usuario>{
        return usuarios.toList()
    }

    fun agregarUsuario(usuario: Usuario){
        usuarios.add(usuario)
    }

    fun eliminarUsuario(usuario: Usuario){
        usuarios.remove(usuario)
    }

    fun editarUsuario(usuario: Usuario){
        val indice = usuarios.indexOfFirst { it.id == usuario.id }

        if (indice != -1) {
            usuarios[indice] = usuario
        }
    }
}