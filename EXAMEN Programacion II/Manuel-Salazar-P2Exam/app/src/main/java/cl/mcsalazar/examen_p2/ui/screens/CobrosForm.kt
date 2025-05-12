package cl.mcsalazar.examen_p2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.mcsalazar.examen_p2.R
import cl.mcsalazar.examen_p2.data.Cobro
import cl.mcsalazar.examen_p2.data.TipoCobro
import cl.mcsalazar.examen_p2.ui.CobrosViewModel

@Composable
fun CobrosFormUI(
    onClick: () -> Unit = {},
    vm: CobrosViewModel = viewModel(factory = CobrosViewModel.Factory)
) {
    val contexto = LocalContext.current

    var monto by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var tipoCobroSeleccionado by rememberSaveable { mutableStateOf(TipoCobro.AGUA) }

    var montoError by remember { mutableStateOf<String?>(null) }
    var fechaError by remember { mutableStateOf<String?>(null) }

    fun validarMonto(input: String): String? {
        if (input.isBlank()) return contexto.getString(R.string.error_monto_obligatorio)
        return try {
            input.replace(".", "").replace(",", "").toLong()
            null
        } catch (e: NumberFormatException) {
            contexto.getString(R.string.error_monto_numerico)
        }
    }

    fun validarFecha(input: String): String? {
        if (input.isBlank()) return contexto.getString(R.string.error_fecha_obligatoria)
        val regex = Regex("\\d{4}-\\d{2}-\\d{2}")
        return if (!regex.matches(input)) contexto.getString(R.string.error_fecha_invalida) else null
    }

    val esFormValido = montoError == null && fechaError == null &&
            monto.isNotBlank() && fecha.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Volver a Inicio",
                    tint = Color.Black
                )
            }

            Text(
                text = contexto.getString(R.string.titulo_formulario),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            )
        }

        TextField(
            value = monto,
            onValueChange = {
                monto = it
                montoError = validarMonto(it)
            },
            label = { Text(contexto.getString(R.string.text_field_medidor)) },
            isError = montoError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        if (montoError != null) {
            Text(text = montoError!!, color = Red)
        }

        TextField(
            value = fecha,
            onValueChange = {
                fecha = it
                fechaError = validarFecha(it)
            },
            label = { Text(contexto.getString(R.string.text_field_fecha)) },
            isError = fechaError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        if (fechaError != null) {
            Text(text = fechaError!!, color = Red)
        }

        Text(
            text = contexto.getString(R.string.radio_btn_title),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )

        TipoCobro.values().forEach { tipo ->
            val tipoCobrotxt = when (tipo) {
                TipoCobro.AGUA -> stringResource(R.string.tipo_agua)
                TipoCobro.LUZ -> stringResource(R.string.tipo_luz)
                TipoCobro.GAS -> stringResource(R.string.tipo_gas)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                RadioButton(
                    selected = tipoCobroSeleccionado == tipo,
                    onClick = { tipoCobroSeleccionado = tipo }
                )
                Text(text = tipoCobrotxt)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                try {
                    val montoLong = monto.replace(".", "").replace(",", "").toLong()
                    vm.insertarCobro(
                        Cobro(
                            monto = montoLong,
                            fecha = fecha,
                            tipo = tipoCobroSeleccionado
                        )
                    )
                    onClick()
                } catch (e: NumberFormatException) {
                    montoError = contexto.getString(R.string.error_monto_numerico)
                }

            },
            enabled = esFormValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(contexto.getString(R.string.btn_registrar_medicion))
        }
    }
}
