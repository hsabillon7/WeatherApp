package tech.twentytwobits.weatherapplication

import android.app.Activity
import android.content.Intent
import android.widget.Toast

// Definir llamados a Toast
fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()

fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resourceId, duration).show()

// Intent
inline fun <reified T: Activity> Activity.goToActivity(noinline init:Intent.() -> Unit ={}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}