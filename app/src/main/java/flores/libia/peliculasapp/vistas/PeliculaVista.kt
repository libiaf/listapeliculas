package flores.libia.peliculasapp.vistas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import flores.libia.peliculasapp.modelos.Pelicula
import flores.libia.peliculasapp.viewmodels.PeliculaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeliculaScreen(viewModel: PeliculaViewModel){

    val peliculas = viewModel.peliculas.value

    var mostrarDialogo by remember {mutableStateOf(false)  }
    var peliculaEditar by remember { mutableStateOf<Pelicula?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Peliculas")})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mostrarDialogo = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            items(peliculas){ pelicula ->
                PeliculaCard(
                    pelicula = pelicula,
                    onDelete = {
                        viewModel.eliminarPelicula(pelicula)
                    },
                    onLongClick = {
                        peliculaEditar = pelicula
                    })
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        if(mostrarDialogo){
            AgregarPeliculaDialog(
                onDismiss = { mostrarDialogo = false },
                onConfirm = { titulo, categoria, duracion, sinopsis, fotoUri ->
                    viewModel.agregarPelicula(titulo, categoria, duracion, sinopsis, fotoUri)
                    mostrarDialogo = false
                }
            )
        }

        peliculaEditar?.let {pelicula ->
            EditarPeliculaDialog(
                pelicula = pelicula,
                onDismiss = {peliculaEditar = null},
                onConfirm = {id, titulo, categoria, duracion, sinopsis, fotoUri ->
                    viewModel.editarPelicula(id, titulo, categoria, duracion, sinopsis, fotoUri)
                    peliculaEditar = null
                }
            )
//            Toast.makeText(context, "${usuario.nombre}", Toast.LENGTH_SHORT).show()
        }

    }


}

@Composable
fun PeliculaCard(
    pelicula: Pelicula,
    onDelete: (Pelicula) -> Unit,
    onLongClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                if (pelicula.fotoUri != null) {
                    AsyncImage(
                        model = pelicula.fotoUri,
                        contentDescription = "Pelicula",
                        modifier = Modifier.size(40.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(pelicula.foto),
                        contentDescription = "Pelicula",
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(
                    onClick = {
                        onDelete(pelicula)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }

            Text(text = pelicula.titulo)
            Text(text = pelicula.categoria)
            Text(text = pelicula.duracion)
            Text(text = pelicula.sinopsis)
        }
    }
}

@Composable
fun AgregarPeliculaDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String?) -> Unit
){
    var foto by remember { mutableStateOf<Uri?>(null) }
    var titulo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        foto = uri

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nueva Pelicula")},
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable{
                            launcher.launch("image/*")
                        }
                ){
                    if (foto != null){
                        AsyncImage(
                            model = foto,
                            contentDescription = "Pelicula",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Pelicula",
                            modifier = Modifier.size(48.dp)

                        )
                    }
                }
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it},
                    label = { Text("Titulo") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = duracion,
                    onValueChange = { duracion = it },
                    label = { Text("Duración") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = sinopsis,
                    onValueChange = { sinopsis = it },
                    label = { Text("Sinopsis") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (titulo.isNotBlank() && categoria.isNotBlank() && duracion.isNotBlank() && sinopsis.isNotBlank()) {
                        onConfirm(titulo, categoria, duracion, sinopsis, foto?.toString())
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }


    )
}


@Composable
fun EditarPeliculaDialog(
    pelicula: Pelicula,
    onDismiss: () -> Unit,
    onConfirm: (Int, String, String, String, String, String?) -> Unit
){
    var foto by remember { mutableStateOf<Uri?>(pelicula.fotoUri?.let {Uri.parse(it)}) }
    var titulo by remember { mutableStateOf(pelicula.titulo) }
    var categoria by remember { mutableStateOf(pelicula.categoria) }
    var duracion by remember { mutableStateOf(pelicula.duracion) }
    var sinopsis by remember { mutableStateOf(pelicula.sinopsis) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        foto = uri

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Editar Pelicula")},
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable{
                            launcher.launch("image/*")
                        }
                ){
                    if (foto != null){
                        AsyncImage(
                            model = foto,
                            contentDescription = "Pelicula",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Pelicula",
                            modifier = Modifier.size(48.dp)

                        )
                    }
                }
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it},
                    label = { Text("Titulo") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = duracion,
                    onValueChange = { duracion = it },
                    label = { Text("Duración") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = sinopsis,
                    onValueChange = { sinopsis = it },
                    label = { Text("Sinopsis") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (titulo.isNotBlank() && categoria.isNotBlank() && duracion.isNotBlank() && sinopsis.isNotBlank()) {
                        onConfirm(pelicula.id, titulo, categoria, duracion, sinopsis, foto?.toString())
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }


    )
}


