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
    fun `landing a plane returns the list of planes at the airport`() {
        airport.clearForLanding(plane1)
        assertEquals(airport.clearForLanding(plane2), listOf(plane1, plane2))
    }
}