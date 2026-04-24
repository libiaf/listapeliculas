package flores.libia.peliculasapp.modelos

import flores.libia.peliculasapp.R
import kotlin.Int
import kotlin.String

class PeliculaRepositorio {
    private val peliculas = mutableListOf(
        Pelicula(1,"Clueless","Comedia","1h 37min", "Cher y Dionne son dos de las chicas más populares de una escuela secundaria de Beverly Hills. Su máxima preocupación es llevar la ropa más atractiva y ser tan populares como sea posible. Pero a pesar de su apariencia, Cher tiene un gran corazón.", R.drawable.clueless),
        Pelicula(2,"En llamas","Acción","2h 26min", "Katniss Everdeen volvió a casa a salvo después de ganar la 74.ª edición de los Juegos del Hambre con su compañero Peeta. Sin embargo, ahora el Capitolio les obliga a abandonar de nuevo a su familia y amigos para participar en el Tour de la Victoria por todos los distritos. Mientras, el presidente Snow está preparando una nueva edición de los Juegos, que transformarán Panem para siempre.", R.drawable.enllamas),
        Pelicula(3,"Interestelar","Ciencia ficción","2h 49min", "Un grupo de científicos y exploradores, encabezados por Cooper, se embarcan en un viaje espacial para encontrar un lugar con las condiciones necesarias para reemplazar a la Tierra y comenzar una nueva vida allí. La Tierra está llegando a su fin y este grupo necesita encontrar un planeta más allá de nuestra galaxia que garantice el futuro de la raza humana.", R.drawable.interestelar),
        Pelicula(4,"KPop Demon Hunters","Animación","1h 39min", "Un supergrupo de k-pop usa sus poderes secretos para proteger a sus fans de amenazas sobrenaturales y de una banda rival de chicos decididos a robar corazones y mentes.", R.drawable.kpopdemonhunters),
        Pelicula(5,"Destino final","Terror","1h 38min", "Un joven estudiante tiene una premonición al subirse al avión en el que se dispone a ir a París con sus compañeros. Presintiendo que algo grave va a pasar, él y sus amigos desembarcan antes del despegue. Efectivamente, el avión sufre un trágico accidente, y los jóvenes piensan que se han librado de una muerte segura gracias a la premonición de su amigo. Pero el destino no ha sido vencido todavía.", R.drawable.finaldestination),
        )

    fun getPeliculas(): List<Pelicula>{
        return peliculas.toList()
    }

    fun agregarPelicula(pelicula: Pelicula){
        peliculas.add(pelicula)
    }

    fun eliminarPelicula(pelicula: Pelicula){
        peliculas.remove(pelicula)
    }

    fun editarPelicula(pelicula: Pelicula){
        val indice = peliculas.indexOfFirst { it.id == pelicula.id }

        if (indice != -1) {
            peliculas[indice] = pelicula
        }
    }
}

