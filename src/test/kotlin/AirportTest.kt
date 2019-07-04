import org.junit.Assert.*
import org.junit.Test

class AirportTest {
    val airport = Airport()

    @Test
    fun `returns 'hello'`() {
        assertEquals("hello", airport.test())
    }
}