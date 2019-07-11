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
        assertThrows(BadWeatherException::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }

    @Test
    fun `planes are not allowed to take off when it's stormy`() {
        val weatherReporter = spyk(WeatherReporter())
        val airport = Airport(weatherReporter = weatherReporter)
        every { weatherReporter.stormy() } returns false
        airport.clearForLanding(Plane())
        every { weatherReporter.stormy() } returns true
        assertThrows(BadWeatherException::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }

//    @Test
//    fun `planes cannot land if the airport is full`() {
//        val airport = Airport(20)
//        repeat(20) {
//            airport.clearForLanding(Plane())
//        }
//        assertThrows(FullAirportException::class.java) {
//            airport.clearForLanding(Plane())
//        }
//    }
}