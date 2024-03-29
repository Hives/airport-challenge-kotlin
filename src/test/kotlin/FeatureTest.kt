import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class FeatureTest {

    val weatherReporter = spyk(WeatherReporter())
    val airport = Airport(weatherReporter = weatherReporter)
    val plane = Plane()

    init {
        every { weatherReporter.randomWeather() } returns 1
    }

    @Test
    fun `an airport can tell a flying plane to land`() {
        airport.clearForLanding(plane)
        assertTrue(airport.contains(plane))
        assertTrue(plane.landed)
    }

    @Test
    fun `an airport can tell a landed plane to take off`() {
        airport.clearForLanding(plane)
        airport.clearForTakeOff(plane)
        assertFalse(airport.contains(plane))
        assertTrue(plane.flying)
    }

    @Test
    fun `planes are not allowed to land when it's stormy`() {
        every { weatherReporter.randomWeather() } returns 10
        assertThrows(Exception::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }

    @Test
    fun `planes are not allowed to take off when it's stormy`() {
        airport.clearForLanding(plane)
        every { weatherReporter.randomWeather() } returns 10
        assertThrows(Exception::class.java) {
            airport.clearForTakeOff(plane)
        }
    }

    @Test
    fun `planes cannot land if the airport is full`() {
        val capacity = airport.defaultCapacity
        repeat(capacity) {
            airport.clearForLanding(Plane())
        }
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(Plane())
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }

    @Test
    fun `airport capacity can be set on initialisation`() {
        val customCapacityAirport = Airport(weatherReporter = weatherReporter, customCapacity = 5)
        repeat(5) {
            customCapacityAirport.clearForLanding(Plane())
        }
        val thrown = assertThrows(Exception::class.java) {
            customCapacityAirport.clearForLanding(Plane())
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }

    @Test
    fun `planes can't take off from a different airport`() {
        val airport2 = Airport(weatherReporter = weatherReporter)
        airport.clearForLanding(plane)
        val thrown = assertThrows(Exception::class.java) {
            airport2.clearForTakeOff(plane)
        }
        assert(thrown.message!!.contains("Plane could not take off; plane is not at this airport"))
    }

    @Test
    fun `landed planes cannot land again`() {
        airport.clearForLanding(plane)
        val thrown = assertThrows(Exception::class.java) {
            plane.land()
        }
        assert(thrown.message!!.contains("Plane could not land; plane already landed."))
    }

    @Test
    fun `flying planes cannot take off again`() {
        airport.clearForLanding(plane)
        airport.clearForTakeOff(plane)
        val thrown = assertThrows(Exception::class.java) {
            plane.takeOff()
        }
        assert(thrown.message!!.contains("Plane could not take off; plane already flying."))
    }
}