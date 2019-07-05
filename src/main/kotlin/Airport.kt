class Airport {

    var hanger: MutableList<Plane> = mutableListOf()

    fun clearForLanding(plane: Plane): MutableList<Plane> {
        hanger.add(plane)
        return hanger
    }

    fun clearForTakeOff(plane: Plane): MutableList<Plane> {
        hanger.remove(plane)
        return hanger
    }

    fun contains(plane: Plane) = hanger.contains(plane)

}
