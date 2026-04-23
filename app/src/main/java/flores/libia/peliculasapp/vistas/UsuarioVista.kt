package flores.libia.peliculasapp.vistas

import android.graphics.drawable.Icon
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import flores.libia.peliculasapp.modelos.Usuario
import flores.libia.peliculasapp.viewmodels.UsuarioViewModel
import org.jetbrains.annotations.Async


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel){

    val usuarios = viewModel.usuarios.value

    var mostrarDialogo by remember {mutableStateOf(false)  }
    var usuarioEditar by remember { mutableStateOf<Usuario?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Usuarios")})
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

            items(usuarios){ usuario ->
                UsuarioCard(usuario, onLongClick = {
                    usuarioEditar = usuario
                } )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        if(mostrarDialogo){
            AgregarUsuarioDialog(
                onDismiss = { mostrarDialogo = false },
                onConfirm = { nombre, correo, edad, fotoUri->
                    viewModel.agregarUsuario(nombre, correo, edad, fotoUri)
                    mostrarDialogo = false
                }
            )
        }

        usuarioEditar?.let {usuario ->
            EditarUsuarioDialog(
                usuario = usuario,
                onDismiss = {usuarioEditar = null},
                onConfirm = {id, nombre, correo, edad, fotoUri ->
                    viewModel.editarUsuario(id, nombre, correo, edad, fotoUri)
                    usuarioEditar = null
                }
            )
//            Toast.makeText(context, "${usuario.nombre}", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun UsuarioCard(usuario: Usuario, onLongClick: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (usuario.fotoUri != null) {
                AsyncImage(
                    model = usuario.fotoUri,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Image(
                    painter = painterResource(usuario.foto),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(48.dp)
                )
            }

            Text(text = usuario.nombre)
            Text(text = usuario.correo)
            Text(text = usuario.edad.toString())
        }
    }
}

@Composable
fun AgregarUsuarioDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, String?) -> Unit
){
    var foto by remember { mutableStateOf<Uri?>(null) }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        foto = uri

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nuevo Usuario")},
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
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(48.dp)

                        )
                    }
                }
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it},
                    label = { Text("Nombre") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val edadInt = edad.toIntOrNull() ?: 0
                    if (nombre.isNotBlank() && correo.isNotBlank() && edadInt > 0) {
                        onConfirm(nombre, correo, edadInt, foto?.toString())
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
fun EditarUsuarioDialog(
    usuario: Usuario,
    onDismiss: () -> Unit,
    onConfirm: (Int, String, String, Int, String?) -> Unit
){
//    var img: Uri? = null
//    if (usuario.fotoUri != null) {
//        img = Uri.parse(usuario.fotoUri)
//    }
    var foto by remember { mutableStateOf<Uri?>(usuario.fotoUri?.let {Uri.parse(it)}) }
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var correo by remember { mutableStateOf(usuario.correo) }
    var edad by remember { mutableStateOf(usuario.edad.toString()) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        foto = uri

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Editar Usuario")},
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
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(48.dp)

                        )
                    }
                }
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it},
                    label = { Text("Nombre") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val edadInt = edad.toIntOrNull() ?: 0
                    if (nombre.isNotBlank() && correo.isNotBlank() && edadInt > 0) {
                        onConfirm(usuario.id, nombre, correo, edadInt, foto?.toString())
                    }
                }
            ) {
                Text("Editar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }


    )
}