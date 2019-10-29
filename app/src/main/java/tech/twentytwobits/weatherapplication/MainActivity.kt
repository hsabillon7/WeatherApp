package tech.twentytwobits.weatherapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verificar la conectividad a Internet
        if (Network.verifyConnection(this)) {
            httpVolley(getUrlApi("siguatepeque"))
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
}
