import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AirportTest {

    val weatherReporter = mockk<WeatherReporter>()
    val airport = Airport(weatherReporter = weatherReporter)
    val plane1 = mockk<Plane>()
    val plane2 = mockk<Plane>()

    @Test
    fun `Airport#clearForLanding lands a plane`() {
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(plane1)
        assert(airport.contains(plane1))
    }

    @Test
    fun `Airport#clearForLanding returns the list of planes at the airport`() {
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(plane1)
        assertEquals(listOf(plane1, plane2), airport.clearForLanding(plane2))
    }

    @Test
    fun `Airport#ClearForTakeOff tells a plane to take off`() {
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(plane1)
        airport.clearForTakeOff(plane1)
        assertFalse(airport.contains(plane1))
    }

    @Test
    fun `Airport#clearForTakeOff returns the list of planes at the airport`() {
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(plane1)
        airport.clearForLanding(plane2)
        assertEquals(listOf(plane2), airport.clearForTakeOff(plane1))
    }


    @Test
    fun `planes can't land in bad weather`() {
        every { weatherReporter.stormy() } returns true
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(plane1)
        }
        assert(thrown.message!!.contains("Plane could not land; weather was stormy."))
    }

    @Test
    fun `planes can't take off in bad weather`() {
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(plane1)
        every { weatherReporter.stormy() } returns true
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForTakeOff(plane1)
        }
        assert(thrown.message!!.contains("Plane could not take off; weather was stormy."))
    }

    @Test
    fun `planes cannot land if the airport is full`() {
        every { weatherReporter.stormy() } returns false
        repeat(20) {
            airport.clearForLanding(plane1)
        }
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(plane1)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }
}