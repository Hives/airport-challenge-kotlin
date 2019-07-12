import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class FeatureTest {

    @Test
    fun `planes are not allowed to land when it's stormy`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        every { weatherReporter.randomWeather() } returns 10
        assertThrows(Exception::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }

    @Test
    fun `planes are not allowed to take off when it's stormy`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        val plane = Plane()
        every { weatherReporter.randomWeather() } returns 1
        airport.clearForLanding(plane)
        every { weatherReporter.randomWeather() } returns 10
        assertThrows(Exception::class.java) {
            airport.clearForTakeOff(plane)
        }
    }

    @Test
    fun `planes cannot land if the airport is full`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        val plane = Plane()
        every { weatherReporter.randomWeather() } returns 1
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
        every { weatherReporter.randomWeather() } returns 1
        repeat(5) {
            airport.clearForLanding(plane)
        }
        val thrown = assertThrows(Exception::class.java) {
            airport.clearForLanding(plane)
        }
        assert(thrown.message!!.contains("Plane could not land; airport was full"))
    }

    @Test
    fun `planes can only take off from the airport where they are`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport1 = Airport(weatherReporter = weatherReporter)
        val airport2 = Airport(weatherReporter = weatherReporter)
        val plane = Plane()
        every { weatherReporter.randomWeather() } returns 1
        airport1.clearForLanding(plane)
        val thrown = assertThrows(Exception::class.java) {
            airport2.clearForTakeOff(plane)
        }
        assert(thrown.message!!.contains("Plane could not take off; plane is not at this airport"))
    }
}