import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AirportTest {

    private val weatherReporter = mockk<WeatherReporter>()
    private val airport = Airport(weatherReporter = weatherReporter)
    private val plane = mockk<Plane>(relaxed = true)
    private val plane2 = mockk<Plane>(relaxed = true)

    init {
        every { weatherReporter.stormy() } returns false
    }

    @Nested
    @DisplayName("Airport#clearForLanding")
    inner class ClearForLanding {
        @Test
        fun `it tells a plane to land`() {
            airport.clearForLanding(plane)
            assert(airport.contains(plane))
            verify { plane.land() }
        }

        @Test
        fun `it returns the list of planes at the airport`() {
            airport.clearForLanding(plane)
            assertEquals(listOf(plane, plane2), airport.clearForLanding(plane2))
        }
    }

    @Nested
    @DisplayName("Airport#clearForTakeOff")
    inner class ClearForTakeOff {
        @Test
        fun `it tells a plane to take off`() {
            airport.clearForLanding(plane)
            airport.clearForTakeOff(plane)
            assertFalse(airport.contains(plane))
            verify { plane.takeOff() }
        }

        @Test
        fun `it returns the list of planes at the airport`() {
            airport.clearForLanding(plane)
            airport.clearForLanding(plane2)
            assertEquals(listOf(plane2), airport.clearForTakeOff(plane))
        }

        @Test
        fun `it throws an error if the plane is not at the airport`() {
            val airport2 = Airport()
            airport.clearForLanding(plane)
            val thrown = assertThrows(Exception::class.java) {
                airport2.clearForTakeOff(plane)
            }
            assert(thrown.message!!.contains("Plane could not take off; plane is not at this airport."))
        }

    }

    @Nested
    @DisplayName("Bad weather behaviour")
    inner class BadWeather {
        @Test
        fun `planes can't land in bad weather`() {
            every { weatherReporter.stormy() } returns true
            val thrown = assertThrows(Exception::class.java) {
                airport.clearForLanding(plane)
            }
            assert(thrown.message!!.contains("Plane could not land; weather was stormy."))
        }

        @Test
        fun `planes can't take off in bad weather`() {
            airport.clearForLanding(plane)
            every { weatherReporter.stormy() } returns true
            val thrown = assertThrows(Exception::class.java) {
                airport.clearForTakeOff(plane)
            }
            assert(thrown.message!!.contains("Plane could not take off; weather was stormy."))
        }

    }

    @Nested
    inner class Capacity {
        @Test
        fun `planes can't land if the airport is full`() {
            repeat(20) {
                airport.clearForLanding(plane)
            }
            val thrown = assertThrows(Exception::class.java) {
                airport.clearForLanding(plane)
            }
            assert(thrown.message!!.contains("Plane could not land; airport was full"))
        }

        @Test
        fun `default capacity can be overridden on initialisation`() {
            val customCapacityAirport = Airport(weatherReporter = weatherReporter, capacity = 5)
            repeat(5) {
                customCapacityAirport.clearForLanding(plane)
            }
            val thrown = assertThrows(Exception::class.java) {
                customCapacityAirport.clearForLanding(plane)
            }
            assert(thrown.message!!.contains("Plane could not land; airport was full"))
        }
    }

}