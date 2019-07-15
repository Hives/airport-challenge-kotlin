import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class WeatherReporterTest {
    val weatherReporter = spyk(WeatherReporter())

    @Test
    fun `weather can be non-stormy`() {
        every { weatherReporter.randomWeather() } returns 1
        assertFalse(weatherReporter.stormy())
    }

    @Test
    fun `weather can be stormy`() {
        every { weatherReporter.randomWeather() } returns 10
        assertTrue(weatherReporter.stormy())
    }

}