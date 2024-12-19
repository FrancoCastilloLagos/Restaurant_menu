package com.example.restaurant

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurant.model.CuentaMesa
import com.example.restaurant.model.ItemMenu

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)
        val cazuela = ItemMenu("Cazuela", 10000)

        val cuentaMesa = CuentaMesa()
        cuentaMesa.agregarItem(pastelDeChoclo, 0)
        cuentaMesa.agregarItem(cazuela, 0)

        val cantidadPastelDeChoclo = findViewById<EditText>(R.id.cantidadPastelDeChoclo)
        val cantidadCazuela = findViewById<EditText>(R.id.cantidadCazuela)
        val precioPastelDeChoclo = findViewById<TextView>(R.id.precioPastelDeChoclo)
        val precioCazuela = findViewById<TextView>(R.id.precioCazuela)
        val comidaTextView = findViewById<TextView>(R.id.comidaTotal)
        val totalTextView = findViewById<TextView>(R.id.totalConPropina)
        val switchPropina = findViewById<Switch>(R.id.switchPropina)

        val actualizarTotales = {
            val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
            val propina = if (switchPropina.isChecked) totalSinPropina * 0.10 else 0.0
            val totalConPropina = totalSinPropina + propina

            comidaTextView.text = "Comida: $$totalSinPropina"
            totalTextView.text = "Total: $$totalConPropina"

            switchPropina.text = if (switchPropina.isChecked) {
                "Propina (10%): $${propina.toInt()}"
            } else {
                "Propina (10%)"
            }
        }

        val actualizarPrecios = {
            val subtotalPastel = pastelDeChoclo.precio * cuentaMesa.items[0].cantidad
            val subtotalCazuela = cazuela.precio * cuentaMesa.items[1].cantidad

            precioPastelDeChoclo.text = "Precio: $$subtotalPastel"
            precioCazuela.text = "Precio: $$subtotalCazuela"
        }

        cantidadPastelDeChoclo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.items[0].cantidad = cantidad
                actualizarPrecios()
                actualizarTotales()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        cantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.items[1].cantidad = cantidad
                actualizarPrecios()
                actualizarTotales()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        switchPropina.setOnCheckedChangeListener { _, _ -> actualizarTotales() }

        cantidadPastelDeChoclo.setText("0")
        cantidadCazuela.setText("0")
        actualizarPrecios()
        actualizarTotales()
    }
}
