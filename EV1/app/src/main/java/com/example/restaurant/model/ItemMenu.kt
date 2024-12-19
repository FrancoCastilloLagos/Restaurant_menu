package com.example.restaurant.model

class ItemMenu(val nombre: String, val precio: Int) {
    init {
        require(precio > 0) { "El precio debe ser mayor a 0" }
    }
}
