import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class FeatureTest {

    @Test
    fun `planes are not allowed to land when it's stormy`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        every { weatherReporter.stormy() } returns true
        assertThrows(Exception::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }

    @Test
    fun `planes are not allowed to take off when it's stormy`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        val plane = Plane()
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(plane)
        every { weatherReporter.stormy() } returns true
        assertThrows(Exception::class.java) {
            airport.clearForTakeOff(plane)
        }
    }

    @Test
    fun `planes cannot land if the airport is full`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        val plane = Plane()
        every { weatherReporter.stormy() } returns false
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
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter, capacity = 5)
        val plane = Plane()
        every { weatherReporter.stormy() } returns false
        repeat(5) {
            airport.clearForLanding(plane)
        }
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(plane)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }
}