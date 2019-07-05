class Airport {

    var hanger: MutableList<Plane> = mutableListOf()

    fun clearForLanding(plane: Plane): MutableList<Plane> {
        hanger.add(plane)
        return hanger
    }

    fun contains(plane: Plane) = hanger.contains(plane)

}
