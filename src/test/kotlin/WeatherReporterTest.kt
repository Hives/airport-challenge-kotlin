import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class WeatherReporterTest {
    val weatherReporter = spyk(WeatherReporter())

    @Test
    fun `Weather can be non-stormy`() {
        every { weatherReporter.randomWeather() } returns 1
        assertFalse(weatherReporter.stormy())
    }

    @Test
    fun `Weather can be stormy`() {
        every { weatherReporter.randomWeather() } returns 10
        assertTrue(weatherReporter.stormy())
    }

}