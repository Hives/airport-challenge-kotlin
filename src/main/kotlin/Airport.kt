class Airport {

    var hanger: MutableList<Plane> = mutableListOf()

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

    fun stormy(): Boolean = (0..10).random() > 8

}
