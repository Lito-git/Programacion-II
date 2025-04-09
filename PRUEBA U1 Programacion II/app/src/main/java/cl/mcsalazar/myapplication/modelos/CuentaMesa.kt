package cl.mcsalazar.myapplication.modelos

class CuentaMesa(val mesa: Int) {

    private val _items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int){
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    fun agregarItem(itemMesa: ItemMesa){
        _items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int{
        return _items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int{
        return if(aceptaPropina){ (calcularTotalSinPropina() * 0.1).toInt() }else{ 0 }
    }

    fun calcularTotalConPropina(): Int{
        return calcularTotalSinPropina() + calcularPropina()
    }

}