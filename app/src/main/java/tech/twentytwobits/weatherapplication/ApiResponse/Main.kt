package tech.twentytwobits.weatherapplication.ApiResponse

class Main(temp: Double, humidity: Double, temp_min: Double, temp_max: Double, pressure: Double) {
    var temp: Double = 0.0
    var humidity: Double = 0.0
    var temp_min: Double = 0.0
    var temp_max: Double = 0.0
    var pressure: Double = 0.0

    init {
        this.temp = temp
        this.humidity = humidity
        this.temp_min = temp_min
        this.temp_max = temp_max
        this.pressure = pressure
    }
}