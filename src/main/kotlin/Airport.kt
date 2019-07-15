class Airport(val customCapacity: Int? = null, val weatherReporter: WeatherReporter = WeatherReporter()) {

    var hanger: MutableList<Plane> = mutableListOf()
    val defaultCapacity = 20
    val capacity = if (customCapacity == null) defaultCapacity else customCapacity

    fun clearForLanding(plane: Plane): MutableList<Plane> {
        if (hanger.size >= capacity) throw Exception("Plane could not land; airport was full.")
        if (stormy()) throw Exception("Plane could not land; weather was stormy.")
        plane.land()
        hanger.add(plane)
        return hanger
    }

    fun clearForTakeOff(plane: Plane): MutableList<Plane> {
        if (!contains(plane)) throw Exception("Plane could not take off; plane is not at this airport.")
        if (stormy()) throw Exception("Plane could not take off; weather was stormy.")
        plane.takeOff()
        hanger.remove(plane)
        return hanger
    }

    fun contains(plane: Plane) = hanger.contains(plane)

    fun stormy(): Boolean = weatherReporter.stormy()

}
