package tech.twentytwobits.weatherapplication.ApiResponse

class Weather(description: String, icon: String) {
    var description: String = ""
    var icon: String = ""

    init {
        this.description = description
        this.icon = icon
    }
}