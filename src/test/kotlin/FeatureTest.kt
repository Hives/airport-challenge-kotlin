import io.mockk.every
import io.mockk.spyk
import org.junit.Test

class FeatureTest {

    @Test(expected = BadWeatherException::class)
    fun `planes are not allowed to land when it's stormy`() {
        val airport = spyk(Airport())
        every { airport.stormy() } returns true
        airport.clearForLanding(Plane())
    }

    @Test(expected = BadWeatherException::class)
    fun `planes are not allowed to take off when it's stormy`() {
        val airport = spyk(Airport())
        every { airport.stormy() } returns false
        airport.clearForLanding(Plane())
        every { airport.stormy() } returns true
        airport.clearForTakeOff(Plane())
    }
}