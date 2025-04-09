package cl.mcsalazar.myapplication
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import cl.mcsalazar.myapplication.modelos.CuentaMesa
import cl.mcsalazar.myapplication.modelos.ItemMenu
import cl.mcsalazar.myapplication.modelos.ItemMesa
import java.text.NumberFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Recuperacion de las referencias de las vistas a los editTexts, switch y textViews
        val etCantPastel = findViewById<EditText>(R.id.etCantPastelDeChoclo)
        val etCantCazuela = findViewById<EditText>(R.id.etCantCazuela)
        val switchPropina = findViewById<Switch>(R.id.switchPropina)

        val tvSubTotalPasteles = findViewById<TextView>(R.id.tvSubTotalPastelDeChoclo)
        val tvSubTotalCazuelas = findViewById<TextView>(R.id.tvSubTotalCazuela)

        val tvValorSinPropina = findViewById<TextView>(R.id.tvValorSinPropina)
        val tvValorPropina = findViewById<TextView>(R.id.tvValorPropina)
        val tvValorConPropina = findViewById<TextView>(R.id.tvValorConPropina)


        //Instanciacion de objetos ItemMenu para los platos del menu
        val pastel = ItemMenu("Pastel de Choclo", "12000")
        val cazuela = ItemMenu("Cazuela", "10000")


        //Instanciacion del objeto NumberFormat para el formateo de valores a pesos chilenos
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))


        fun calcularCuenta() {

            //Conversion de los valores ingresados en los et a numeros enteros
            val cantidadPastel = etCantPastel.text.toString().toIntOrNull() ?: 0
            val cantidadCazuela = etCantCazuela.text.toString().toIntOrNull() ?: 0

            //Instanciacion de objetos ItemMesa para el calculo de subtotales
            val itemPastel = ItemMesa(pastel, cantidadPastel)
            val itemCazuela = ItemMesa(cazuela, cantidadCazuela)


            //Actualizacion y visualizacion de los tv para mostrar el calculo del subtotal de cada tipo de plato con el metodo de la clase ItemMesa
            tvSubTotalPasteles.text = numberFormat.format(itemPastel.calcularSubtotal())
            tvSubTotalCazuelas.text = numberFormat.format(itemCazuela.calcularSubtotal())

            //instaciacion de CuentaMesa, adicion de los platos seleccionados y ajuste de la cuenta con o sin propina
            val cuenta = CuentaMesa(mesa = 1).apply {
                aceptaPropina = switchPropina.isChecked
                agregarItem(itemPastel)
                agregarItem(itemCazuela)
            }

            //Actualizacion y visualizacoin de los tv para mostrar los totales de la cuenta con los metodos de la clase CuentaMesa
            tvValorSinPropina.text = numberFormat.format(cuenta.calcularTotalSinPropina())
            tvValorPropina.text = numberFormat.format(cuenta.calcularPropina())
            tvValorConPropina.text = numberFormat.format(cuenta.calcularTotalConPropina())
        }


        //Listeners para el calculo automatico de totales segun los cambios en los inputs y el switch
        etCantPastel.addTextChangedListener { calcularCuenta() }
        etCantCazuela.addTextChangedListener { calcularCuenta() }
        switchPropina.setOnCheckedChangeListener { _, _ -> calcularCuenta() }

        //Calculo inicial de la cuenta
        calcularCuenta()
    }
}