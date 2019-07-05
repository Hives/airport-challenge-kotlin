class Airport {

    var hanger: MutableList<Plane> = mutableListOf()

    fun clearForLanding(plane: Plane): MutableList<Plane> {
        if (stormy()) {
            throw BadWeatherException("Could not land plane; weather was stormy.")
        }
        hanger.add(plane)
        return hanger
    }

    fun clearForTakeOff(plane: Plane): MutableList<Plane> {
        hanger.remove(plane)
        return hanger
    }

    fun contains(plane: Plane) = hanger.contains(plane)

    fun stormy(): Boolean = false

}
