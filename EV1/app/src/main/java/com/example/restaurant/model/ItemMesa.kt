package com.example.restaurant.model

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        return cantidad * itemMenu.precio
    }
}
