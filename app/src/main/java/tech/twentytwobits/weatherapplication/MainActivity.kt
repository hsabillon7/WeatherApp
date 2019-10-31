package tech.twentytwobits.weatherapplication

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import tech.twentytwobits.weatherapplication.ApiResponse.ApiResponse
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verificar la conectividad a Internet
        verifyAndConnect()

        // Listener para obtener una nueva ciudad
        buttonBuscar.setOnClickListener {
            verifyAndConnect(editTextBuscar.text.toString())
        }
    }

    /***
     * Verifica la conexión del dispositivo a Internet y obtiene
     * la respuesta JSON desde la API.
     */
    private fun verifyAndConnect(city: String = "siguatepeque") {
        if (Network.verifyConnection(this)) {
            httpVolley(getUrlApi(city))
        } else {
            Toast.makeText(this, "¡No tienes conexión a Internet!", Toast.LENGTH_SHORT).show()
        }
    }

    /***
     * https://developer.android.com/training/volley/simple
     * Realiza una petición http implementando Volley
     */
    private fun httpVolley(url: String) {
        // Instanciar la cola de peticiones
        val queue = Volley.newRequestQueue(this)

        // Obtener un string de respuesta desde la URL enviada
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("HTTPVolley",  response)
                Toast.makeText(this, "Conexión establecida", Toast.LENGTH_LONG).show()
                jsonToObject(response)
            },
            Response.ErrorListener {
                Log.d("HTTPVolley", "Error en la URL $url")
                Toast.makeText(this, "¡Ha ocurrido un error en la conexión!", Toast.LENGTH_SHORT).show()
            })

        // Agregar la peticion a la cola de peticiones
        queue.add(stringRequest)
    }

    private fun getUrlApi(city: String): String {

        return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=10bc1fcd8615b888c89f52126f7e90c7&units=metric&lang=es"
    }

    /***
     * Pasa un objeto de tipo JSON a objetos de Kotlin.
     * params: response el objeto de tipo JSON
     */
    private fun jsonToObject(response: String) {
        // Inicializar los valores de tipo Gson
        val gson = Gson()
        val apiResponse = gson.fromJson(response, ApiResponse::class.java)

        // Verificar si la solicitud HTTP a la API es correcta (code 200)
        if (apiResponse.cod == 200) {
            textViewCiudad.text = getString(R.string.text_view_ciudad, apiResponse.name, apiResponse.sys?.country)
            textViewDescripcion.text = getDateAndIme()
            textViewGrados.text = getString(R.string.text_view_grados, apiResponse.main?.temp?.toInt())
            textViewEstado.text = apiResponse.weather?.get(0)?.description?.capitalize()
            textViewMinima.text = getString(R.string.text_view_minima, apiResponse.main?.temp_min?.toInt())
            textViewMaxima.text = getString(R.string.text_view_maxima, apiResponse.main?.temp_max?.toInt())
            // TODO: Buscar la manera de mostrar el simbolo de % sin concatenar
            textViewHumedadValor.text = getString(R.string.text_view_humedad_valor, apiResponse.main?.humidity?.toInt()) + '%'
            textViewVientoValor.text = getString(R.string.text_view_viento_valor, apiResponse.wind?.speed?.toInt())
            textViewPresionValor.text = getString(R.string.text_view_presion_valor, apiResponse.main?.pressure?.toInt())
            Toast.makeText(this, "Datos obtenidos correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDateAndIme():String {
        // Comprobar el nivel de SDK utilizado
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val locale = Locale("es", "hn")
            val formatter = DateTimeFormatter.ofPattern("hh:mm a | EEEE, dd LLLL yyyy", locale)
            val answer: String = current.format(formatter)
            answer
        } else {
            val date = Date()
            val locale = Locale("es", "hn")
            val formatter = SimpleDateFormat("hh:mm a | EEEE, dd LLLL yyyy", locale)
            val answer: String = formatter.format(date)
            answer
        }
    }
}
