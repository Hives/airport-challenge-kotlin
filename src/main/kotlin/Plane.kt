class Plane {
    var flying = true

    fun takeOff() {
        if (flying) throw Exception("Plane could not take off; plane already flying.")
        flying = true
    }

    fun land() {
        if (!flying) throw Exception("Plane could not land; plane already landed.")
        flying = false
    }
}