# Airport Challenge in Kotlin

Makers Academy Airport Challenge, this time in Kotlin. Instructions are
[here](instructions.md).

## Thoughts etc.

In [this
commit](https://github.com/Hives/airport-challenge-kotlin/commit/25688a82c5d8275059c1f273998f2dff4213ba86)
I refactored the tests so that the planes and airports were initialised outside
the test functions. I would have thought this would break the tests, as a plane
landed at the airport in the first test would still be at the airport in the
second test, but... it didn't. Why is that?
- It's because JUnit 4 reinitialises the test class for each test - which is a
  waster of time and resources. You can get around this in Java, but the
  equivalent solution in Kotlin is quite nasty. Solution is to use JUnit 5, which
  is more friendly with Kotlin. See this vid:
  <https://www.youtube.com/watch?v=RX_g65J14H0&t=210>
