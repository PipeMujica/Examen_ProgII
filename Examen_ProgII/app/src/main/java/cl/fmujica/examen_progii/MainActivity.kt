package cl.fmujica.examen_progii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.fmujica.examen_progii.data.Registro
import cl.fmujica.examen_progii.data.RegistroRepository
import cl.fmujica.examen_progii.data.TipoRegistro
import cl.fmujica.examen_progii.formato.FormatoFecha
import cl.fmujica.examen_progii.ui.theme.Examen_ProgIITheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var registroRepository:RegistroRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registroRepository = RegistroRepository.getInstance(this)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home"){
                composable("home"){
                    HomeUI( onButtonClick = {
                        navController.navigate("registro")
                    } )
                }
                composable("registro"){
                    registroMedidor()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeUI(
    onButtonClick: () ->Unit={}
){
    val contexto = LocalContext.current
    var registros by remember {
        mutableStateOf( emptyList<Registro>())
    }

    LaunchedEffect( Unit ) {
        withContext(Dispatchers.IO) {
            registros = RegistroRepository.getInstance(contexto).obtenerTodos()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onButtonClick() }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = innerPadding.calculateLeftPadding(LayoutDirection.Ltr))
        ) {
            ListaRegistros(registros)
        }
    }
}

@Composable
fun ListaRegistros(
    registros:List<Registro>)
{
    LazyColumn() {
        items(registros){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconoRegistro(it)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(it.categoria)
                    Spacer(modifier = Modifier.width(10.dp))
                    //Text(it.medidor)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = FormatoFecha()(it.fecha),
                        style = TextStyle(
                            fontSize = 10.sp
                        )
                    )
                }
            }
            Divider()
        }
    }
}


@Composable
fun IconoRegistro(registro: Registro) {
    when(TipoRegistro.valueOf(registro.categoria)) {
        TipoRegistro.Agua -> Icon(
            imageVector = Icons.Default.WaterDrop,
            contentDescription = TipoRegistro.Agua.toString())
        TipoRegistro.Luz -> Icon(
            imageVector = Icons.Default.Lightbulb,
            contentDescription = TipoRegistro.Luz.toString())
        TipoRegistro.Gas -> Icon(
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = TipoRegistro.Gas.toString())
    }
}