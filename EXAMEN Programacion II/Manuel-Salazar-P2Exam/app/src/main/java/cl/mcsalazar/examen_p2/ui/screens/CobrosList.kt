package cl.mcsalazar.examen_p2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.NumberFormat
import java.util.Locale
import cl.mcsalazar.examen_p2.R
import cl.mcsalazar.examen_p2.data.Cobro
import cl.mcsalazar.examen_p2.data.TipoCobro
import cl.mcsalazar.examen_p2.ui.CobrosViewModel


@Composable
fun CobrosListUI(
    navController: NavController,
    vmListaCobros: CobrosViewModel = viewModel(factory = CobrosViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        vmListaCobros.obtenerCobros()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if (vmListaCobros.cobros.isEmpty()){
                Box(modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                    contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.msg_no_registros))
                }
            }else {

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(vmListaCobros.cobros) { cobro ->
                        CobroItem(cobro = cobro, onDelete = { vmListaCobros.eliminarCobro(cobro) })
                    }
                }
            }
        }
        // Botón flotante "+"
        FloatingActionButton(
            onClick = { navController.navigate("formulario") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, "Agregar Cobro")
        }
    }
}

@Composable
fun CobroItem(cobro: Cobro, onDelete: () -> Unit) {

    fun formatMonto(monto: Long): String{
        val locale = Locale("es", "CL")
        val currencyFormat = NumberFormat.getCurrencyInstance(locale)
        return currencyFormat.format(monto)
    }

    val formatedMonto = formatMonto(cobro.monto)

    val tipoCobroTxt = when(cobro.tipo){
        TipoCobro.AGUA -> stringResource(R.string.tipo_agua)
        TipoCobro.LUZ -> stringResource(R.string.tipo_luz)
        TipoCobro.GAS -> stringResource(R.string.tipo_gas)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = when (cobro.tipo) {
                    TipoCobro.AGUA -> Icons.Default.WaterDrop
                    TipoCobro.LUZ -> Icons.Default.Bolt
                    TipoCobro.GAS -> Icons.Default.Fireplace
                },
                contentDescription = tipoCobroTxt,
                modifier = Modifier.size(40.dp)
            )
            //Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = tipoCobroTxt)
                Text(text = "$formatedMonto")
                Text(text = "${cobro.fecha}")
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Eliminar")
            }
        }
    }
}
