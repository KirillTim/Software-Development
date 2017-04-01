package im.kirillt.sd.clocktest.test

import im.kirillt.sd.clocktest.EventsStatisticImpl
import org.junit.Before
import org.junit.Test

import java.time.Duration
import java.time.Instant

import org.junit.Assert.assertEquals
import org.mockito.Mockito.*
import java.time.Clock

class EventsStatisticTest {

    val DELTA = 1e-3

    private var clock: Clock? = null
    private var statistic: EventsStatisticImpl? = null

    @Before
    fun setUp() {
        clock = mock(Clock::class.java)
        statistic = EventsStatisticImpl(clock!!)
    }

    @Test
    fun testIncEvent() {
        `when`(clock?.instant()).thenReturn(Instant.EPOCH)
        statistic!!.incEvent("a")
        statistic!!.incEvent("a")
        assertEquals(2.0 / 60, statistic!!.getEventStatistic("a"), DELTA)
    }

    @Test
    fun testGetEventStatistic() {
        `when`(clock?.instant()).thenReturn(Instant.EPOCH)
        statistic!!.incEvent("a")
        `when`(clock?.instant()).thenReturn(Instant.EPOCH.plus(Duration.ofMinutes(30)))
        statistic!!.incEvent("b")
        `when`(clock?.instant()).thenReturn(Instant.EPOCH.plus(Duration.ofMinutes(61)))

        assertEquals(0.0, statistic!!.getEventStatistic("a"), DELTA)
        assertEquals(1.0 / 60, statistic!!.getEventStatistic("b"), DELTA)
        `when`(clock?.instant()).thenReturn(Instant.EPOCH.plus(Duration.ofMinutes((30 + 61).toLong())))
        assertEquals(0.0, statistic!!.getEventStatistic("b"), DELTA)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllEventStatistic() {
        `when`(clock?.instant()).thenReturn(Instant.EPOCH)
        statistic!!.incEvent("a")
        statistic!!.incEvent("b")
        assertEquals(2.0 / 60, statistic!!.getAllEventStatistic(), DELTA)
    }

    @Test
    @Throws(Exception::class)
    fun testExpiration() {
        `when`(clock?.instant()).thenReturn(Instant.EPOCH)
        statistic!!.incEvent("a")
        assertEquals(1.0 / 60, statistic!!.getEventStatistic("a"), DELTA)
        `when`(clock?.instant()).thenReturn(Instant.EPOCH.plus(Duration.ofMinutes(61)))
        assertEquals(0.0, statistic!!.getEventStatistic("a"), DELTA)
    }
}