import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AirportTest {

    private val airport = spyk(Airport())
    private val plane1 = mockk<Plane>()
    private val plane2 = mockk<Plane>()

    @Test
    fun `Airport#clearForLanding lands a plane`() {
        every { airport.stormy() } returns false
        airport.clearForLanding(plane1)
        assert(airport.contains(plane1))
    }

    @Test
    fun `Airport#clearForLanding returns the list of planes at the airport`() {
        every { airport.stormy() } returns false
        airport.clearForLanding(plane1)
        assertEquals(listOf(plane1, plane2), airport.clearForLanding(plane2))
    }

    @Test
    fun `Airport#ClearForTakeOff tells a plane to take off`() {
        every { airport.stormy() } returns false
        airport.clearForLanding(plane1)
        airport.clearForTakeOff(plane1)
        assertFalse(airport.contains(plane1))
    }

    @Test
    fun `Airport#clearForTakeOff returns the list of planes at the airport`() {
        every { airport.stormy() } returns false
        airport.clearForLanding(plane1)
        airport.clearForLanding(plane2)
        assertEquals(listOf(plane2), airport.clearForTakeOff(plane1))
    }


    @Test
    fun `planes can't land in bad weather`() {
        every { airport.stormy() } returns true
        assertThrows(BadWeatherException::class.java) {
            airport.clearForLanding(plane1)
        }
    }

    @Test
    fun `planes can't take off in bad weather`() {
        every { airport.stormy() } returns false
        airport.clearForLanding(plane1)
        every { airport.stormy() } returns true
        assertThrows(BadWeatherException::class.java) {
            airport.clearForTakeOff(plane1)
        }
    }
}