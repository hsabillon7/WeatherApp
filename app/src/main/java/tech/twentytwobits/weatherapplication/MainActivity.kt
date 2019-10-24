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
            Toast.makeText(this, "Conexión establecida", Toast.LENGTH_LONG).show()
            httpVolley()
        } else {
            Toast.makeText(this, "No tienes conexión a Internet", Toast.LENGTH_LONG).show()
        }
    }

    /***
     * https://developer.android.com/training/volley/simple
     * Realiza una petición http implementando Volley
     */
    private fun httpVolley() {
        // Instanciar la cola de peticiones
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.google.com"

        // Obtener un string de respuesta desde la url enviada
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {
                response -> Log.d("HTTPVolley", response)
            },
            Response.ErrorListener {
                Log.d("HTTPVolley", "¡Ha ocurrido un error en la conexión!")
            }
        )

        queue.add(stringRequest)
    }
}
