package monge.eliana.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var currentInput = ""  // Guarda la entrada actual (números)
    private var previousInput = "" // Guarda el primer número
    private var operator = "" // Guarda el operador actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pantalla: TextView = findViewById(R.id.textView)

        // Lista de botones para asociar las acciones
        val botones = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnMas, R.id.btnMenos, R.id.btnPor, R.id.btnDel,
            R.id.btnIgual, R.id.btnEntre3
        )

        for (id in botones) {
            val boton = findViewById<Button>(id)
            boton.setOnClickListener { manejarEntrada(boton.text.toString(), pantalla) }
        }
    }

    private fun manejarEntrada(entrada: String, pantalla: TextView) {
        when (entrada) {
            "+", "-", "*", "/" -> {  // Cuando presionas un operador
                if (currentInput.isNotEmpty()) {  // Si ya hay un número en la entrada
                    previousInput = currentInput  // Guardamos el primer número
                    currentInput = ""  // Limpiamos la entrada para el siguiente número
                    operator = entrada  // Guardamos el operador
                }
            }
            "=" -> {  // Cuando presionas "=" para obtener el resultado
                if (previousInput.isNotEmpty() && currentInput.isNotEmpty() && operator.isNotEmpty()) {
                    val resultado = calcular(previousInput.toFloat(), currentInput.toFloat(), operator)
                    pantalla.text = resultado.toString()  // Mostramos el resultado
                    previousInput = ""  // Limpiamos el primer número
                    currentInput = resultado.toString()  // El resultado se convierte en el nuevo primer número
                    operator = ""  // Limpiamos el operador
                }
            }
            "DEL" -> {  // Cuando presionas "DEL" para borrar todo
                currentInput = ""
                previousInput = ""
                operator = ""
                pantalla.text = "0"
            }
            else -> {  // Si presionas un número
                currentInput += entrada
                pantalla.text = currentInput  // Actualizamos la pantalla con el número ingresado
            }
        }
    }

    private fun calcular(n1: Float, n2: Float, op: String): Float {
        return when (op) {
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> if (n2 != 0f) n1 / n2 else 0f  // Previene la división por cero
            else -> 0f
        }
    }
}
