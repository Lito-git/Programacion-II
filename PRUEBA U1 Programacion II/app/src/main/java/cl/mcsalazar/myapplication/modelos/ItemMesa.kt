package cl.mcsalazar.myapplication.modelos

class ItemMesa(val itemMenu: ItemMenu, val cantidad: Int) {

    fun calcularSubtotal(): Int{
        return (itemMenu.precio.toIntOrNull() ?: 0) * cantidad
    }
}