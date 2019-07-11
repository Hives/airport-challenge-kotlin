class WeatherReporter {

    fun stormy() = randomWeather() > 8

    fun randomWeather() = (1..10).random()

}