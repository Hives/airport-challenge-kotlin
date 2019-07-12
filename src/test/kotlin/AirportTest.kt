import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AirportTest {

    val weatherReporter = mockk<WeatherReporter>()
    val airport1 = Airport(weatherReporter = weatherReporter)
    val airport2 = Airport(weatherReporter = weatherReporter)
    val plane1 = mockk<Plane>()
    val plane2 = mockk<Plane>()

    @Test
    fun `Airport#clearForLanding lands a plane`() {
        every { weatherReporter.stormy() } returns false
        airport1.clearForLanding(plane1)
        assert(airport1.contains(plane1))
    }

    @Test
    fun `Airport#clearForLanding returns the list of planes at the airport`() {
        every { weatherReporter.stormy() } returns false
        airport1.clearForLanding(plane1)
        assertEquals(listOf(plane1, plane2), airport1.clearForLanding(plane2))
    }

    @Test
    fun `Airport#ClearForTakeOff tells a plane to take off`() {
        every { weatherReporter.stormy() } returns false
        airport1.clearForLanding(plane1)
        airport1.clearForTakeOff(plane1)
        assertFalse(airport1.contains(plane1))
    }

    @Test
    fun `Airport#clearForTakeOff returns the list of planes at the airport`() {
        every { weatherReporter.stormy() } returns false
        airport1.clearForLanding(plane1)
        airport1.clearForLanding(plane2)
        assertEquals(listOf(plane2), airport1.clearForTakeOff(plane1))
    }

    @Test
    fun `Airport#clearForTakeOff throws an error if plane not at airport`() {
        every { weatherReporter.stormy() } returns false
        airport1.clearForLanding(plane1)
        val thrown = assertThrows(Exception::class.java) {
            airport2.clearForTakeOff(plane1)
        }
        assert(thrown.message!!.contains("Plane could not take off; plane is not at this airport."))
    }

    @Test
    fun `planes can't land in bad weather`() {
        every { weatherReporter.stormy() } returns true
        val thrown = assertThrows(Exception::class.java) {
            airport1.clearForLanding(plane1)
        }
        assert(thrown.message!!.contains("Plane could not land; weather was stormy."))
    }

    @Test
    fun `planes can't take off in bad weather`() {
        every { weatherReporter.stormy() } returns false
        airport1.clearForLanding(plane1)
        every { weatherReporter.stormy() } returns true
        val thrown = assertThrows(Exception::class.java) {
            airport1.clearForTakeOff(plane1)
        }
        assert(thrown.message!!.contains("Plane could not take off; weather was stormy."))
    }

    @Test
    fun `planes cannot land if the airport is full`() {
        every { weatherReporter.stormy() } returns false
        repeat(20) {
            airport1.clearForLanding(plane1)
        }
        val thrown = assertThrows(Exception::class.java) {
            airport1.clearForLanding(plane1)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }

    @Test
    fun `airport capacity can be set on initialisation`() {
       val customCapacityAirport = Airport(weatherReporter = weatherReporter, capacity = 5)
        every { weatherReporter.stormy() } returns false
        repeat(5) {
            customCapacityAirport.clearForLanding(plane1)
        }
        val thrown = assertThrows(Exception::class.java) {
            customCapacityAirport.clearForLanding(plane1)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }
}