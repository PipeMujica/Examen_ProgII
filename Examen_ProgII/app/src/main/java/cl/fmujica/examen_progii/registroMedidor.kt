package cl.fmujica.examen_progii

import android.text.BoringLayout
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.fmujica.examen_progii.data.TipoRegistro
import androidx.compose.foundation.layout.Row as Row1
import androidx.compose.material3.Checkbox as Checkbox


@Preview()
@Composable
fun registroMedidor() {
    val checked = remember {
        mutableStateMapOf<String, Boolean>().withDefault{false} }
    val items = listOf("Agua", "Luz", "Gas")
    val contexto = LocalContext.current
    val textTitulo = "titulo"
    val textMedidor = "medidor"
    val textFecha = "fecha"
    val textBtnRegistro = "btnRegistro"
    var medidor by remember {
        mutableStateOf("")
    }
    var fecha by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro Medidor",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box() {
            TextField(
                value = medidor,
                placeholder = { Text(textMedidor)},
                onValueChange = { medidor = it }
            )
        }
        Box() {
            TextField(
                value = fecha,
                placeholder = { Text(textFecha)},
                onValueChange = { fecha = it },
            )
        }
        Text(text = "Medida de:")
        Column (){
            items.forEach { item ->

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = item)
                    Checkbox(checked = checked.getValue(item) ,
                        onCheckedChange = {checked[item] = it})

                }
            }
        }


        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Registrar medición")
        }
    }
}
