package flores.libia.peliculasapp.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flores.libia.peliculasapp.modelos.Pelicula
import flores.libia.peliculasapp.modelos.Usuario
import flores.libia.peliculasapp.viewmodels.PeliculaViewModel
import flores.libia.peliculasapp.viewmodels.UsuarioViewModel

@Composable
fun PeliculaScreen(viewModel: PeliculaViewModel){
    val peliculas = viewModel.peliculas.value
    Scaffold() {
            padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(peliculas){ pelicula ->
                PeliculaCard(pelicula)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }

}
@Composable
fun PeliculaCard(pelicula: Pelicula){
    Card(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.padding(16.dp)

        ) {
            Text(text = pelicula.titulo)
            Text(text = pelicula.categoria)
            Text(text = pelicula.duracion)
            Text(text = pelicula.sinopsis)

        }
    }
}

