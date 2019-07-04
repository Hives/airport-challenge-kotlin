import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AirportTest {

    @Test
    fun `an airport can land a plane`() {
        val airport = Airport()
        val plane = Plane()
        airport.land(plane)
        assert(airport.contains(plane))
    }

    @Test
    fun `a new plane is not at an airport`() {
        val airport = Airport()
        val plane = Plane()
        assertFalse(airport.contains(plane))
    }
}