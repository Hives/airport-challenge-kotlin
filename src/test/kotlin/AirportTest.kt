import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class AirportTest {

    private val airport = Airport()
    private val plane1 = mockk<Plane>()
    private val plane2 = mockk<Plane>()

    @Test
    fun `an airport can land a plane`() {
        airport.clearForLanding(plane1)
        assert(airport.contains(plane1))
    }

    @Test
    fun `Airport#clearForLanding returns the list of planes at the airport`() {
        airport.clearForLanding(plane1)
        assertEquals(listOf(plane1, plane2), airport.clearForLanding(plane2))
    }

    @Test
    fun `an airport can tell a plane to take off`() {
        airport.clearForLanding(plane1)
        airport.clearForTakeOff(plane1)
        assertFalse(airport.contains(plane1))
    }

    @Test
    fun `Airport#clearForTakeOff returns the list of planes at the airport`() {
        airport.clearForLanding(plane1)
        airport.clearForLanding(plane2)
        assertEquals(listOf(plane2), airport.clearForTakeOff(plane1))
    }
}