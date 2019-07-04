class Airport {
    private var hanger: MutableList<Plane> = mutableListOf()
    fun land(plane: Plane) {
        hanger.add(plane)
    }
    fun contains(plane: Plane) = hanger.contains(plane)
}