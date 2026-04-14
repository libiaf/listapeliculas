package flores.libia.peliculasapp.modelos

class Repositorio {

    fun getUsuarios(): List<Usuario>{
        return listOf(
            Usuario(1, "Tomas", "tomasflores@gmail.com", 20),
            Usuario(2, "Sofia", "sofiaflores@gmail.com", 15),
            Usuario(3, "Diana", "dianaesc@gmail.com", 21),
            Usuario(4, "Kimberly", "kimberlymar@gmail.com", 20)

        )
    }
}