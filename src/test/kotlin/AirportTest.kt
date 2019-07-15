import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AirportTest {

    private val weatherReporter = mockk<WeatherReporter>()
    private val airport = Airport(weatherReporter = weatherReporter)
    private val plane = mockk<Plane>(relaxed = true)
    private val plane2 = mockk<Plane>(relaxed = true)

    init {
        every { weatherReporter.stormy() } returns false
    }

    @Test
    fun `Airport#clearForLanding lands a plane`() {
        airport.clearForLanding(plane)
        assert(airport.contains(plane))
        verify { plane.land() }
    }

    @Test
    fun `Airport#clearForLanding returns the list of planes at the airport`() {
        airport.clearForLanding(plane)
        assertEquals(listOf(plane, plane2), airport.clearForLanding(plane2))
    }

    @Test
    fun `Airport#ClearForTakeOff tells a plane to take off`() {
        airport.clearForLanding(plane)
        airport.clearForTakeOff(plane)
        assertFalse(airport.contains(plane))
        verify { plane.takeOff() }
    }

    @Test
    fun `Airport#clearForTakeOff returns the list of planes at the airport`() {
        airport.clearForLanding(plane)
        airport.clearForLanding(plane2)
        assertEquals(listOf(plane2), airport.clearForTakeOff(plane))
    }

    @Test
    fun `Airport#clearForTakeOff throws an error if plane not at airport`() {
        val airport2 = Airport()
        airport.clearForLanding(plane)
        val thrown = assertThrows(Exception::class.java) {
            airport2.clearForTakeOff(plane)
        }
        assert(thrown.message!!.contains("Plane could not take off; plane is not at this airport."))
    }

    @Test
    fun `planes can't land in bad weather`() {
        every { weatherReporter.stormy() } returns true
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(plane)
        }
        assert(thrown.message!!.contains("Plane could not land; weather was stormy."))
    }

    @Test
    fun `planes can't take off in bad weather`() {
        airport.clearForLanding(plane)
        every { weatherReporter.stormy() } returns true
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForTakeOff(plane)
        }
        assert(thrown.message!!.contains("Plane could not take off; weather was stormy."))
    }

    @Test
    fun `planes cannot land if the airport is full`() {
        repeat(20) {
            airport.clearForLanding(plane)
        }
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(plane)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }

    @Test
    fun `airport capacity can be set on initialisation`() {
       val customCapacityAirport = Airport(weatherReporter = weatherReporter, capacity = 5)
        repeat(5) {
            customCapacityAirport.clearForLanding(plane)
        }
        val thrown = assertThrows(Exception::class.java) {
            customCapacityAirport.clearForLanding(plane)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }
}