import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlaneTest {
    val plane = Plane()

    @Test
    fun `flying planes can land`() {
        plane.land()
        assertTrue(plane.landed)
    }

    @Test
    fun `landed planes can take off`() {
        plane.land()
        plane.takeOff()
        assertTrue(plane.flying)
    }

    @Test
    fun `flying planes cannot take off`() {
        val thrown = assertThrows(Exception::class.java) {
            plane.takeOff()
        }
        assert(thrown.message!!.contains("Plane could not take off; plane already flying."))
    }

    @Test
    fun `landed planes cannot land`() {
        plane.land()
        val thrown = assertThrows(Exception::class.java) {
            plane.land()
        }
        assert(thrown.message!!.contains("Plane could not land; plane already landed."))
    }
}