class Airport(weatherReporter: WeatherReporter = WeatherReporter()) {

    var hanger: MutableList<Plane> = mutableListOf()
    val weatherReporter = weatherReporter

    fun clearForLanding(plane: Plane): MutableList<Plane> {
        if (stormy()) throw BadWeatherException("Plane could not land; weather was stormy.")
        hanger.add(plane)
        return hanger
    }

    fun clearForTakeOff(plane: Plane): MutableList<Plane> {
        if (stormy()) throw BadWeatherException("Plane could not take off; weather was stormy.")
        hanger.remove(plane)
        return hanger
    }

    fun contains(plane: Plane) = hanger.contains(plane)

    fun stormy(): Boolean = weatherReporter.stormy()

}
