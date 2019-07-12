class Airport(val capacity: Int = 20, val weatherReporter: WeatherReporter = WeatherReporter()) {

    var hanger: MutableList<Plane> = mutableListOf()

    fun clearForLanding(plane: Plane): MutableList<Plane> {
        if (hanger.size >= capacity) throw Exception("Plane could not land; airport was full.")
        if (stormy()) throw Exception("Plane could not land; weather was stormy.")
        hanger.add(plane)
        return hanger
    }

    fun clearForTakeOff(plane: Plane): MutableList<Plane> {
        if (!contains(plane)) throw Exception("Plane could not take off; plane is not at this airport.")
        if (stormy()) throw Exception("Plane could not take off; weather was stormy.")
        hanger.remove(plane)
        return hanger
    }

    fun contains(plane: Plane) = hanger.contains(plane)

    fun stormy(): Boolean = weatherReporter.stormy()

}
