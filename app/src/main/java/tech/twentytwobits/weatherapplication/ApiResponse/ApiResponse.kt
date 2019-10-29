package tech.twentytwobits.weatherapplication.ApiResponse

class ApiResponse(name: String, cod: Int, weather: ArrayList<Weather>, main: Main, sys: Sys, wind: Wind) {
    var name: String = ""
    var cod: Int = 0
    var weather: ArrayList<Weather>? = null
    var main: Main? = null
    var sys: Sys? = null
    var wind: Wind? = null

    init {
        this.name = name
        this.cod = cod
        this.weather = weather
        this.sys = sys
        this.wind = wind
    }
}