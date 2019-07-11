import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class FeatureTest {

    @Test
    fun `planes are not allowed to land when it's stormy`() {
        val airport = spyk(Airport())
        every { airport.stormy() } returns true
        assertThrows(BadWeatherException::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }

    @Test
    fun `planes are not allowed to take off when it's stormy`() {
        val airport = spyk(Airport())
        every { airport.stormy() } returns false
        airport.clearForLanding(Plane())
        every { airport.stormy() } returns true
        assertThrows(BadWeatherException::class.java) {
            airport.clearForTakeOff(Plane())
        }
    }
}