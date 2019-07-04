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
    fun `landing a plane returns the list of planes at the airport`() {
        val airport = Airport()
        val plane1 = Plane()
        val plane2 = Plane()
        airport.land(plane1)
        assertEquals(airport.land(plane2), listOf(plane1, plane2))
    }

    @Test
    fun `a new plane is not at an airport`() {
        val airport = Airport()
        val plane = Plane()
        assertFalse(airport.contains(plane))
    }
}