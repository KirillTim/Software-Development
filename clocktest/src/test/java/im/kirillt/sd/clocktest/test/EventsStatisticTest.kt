package im.kirillt.sd.clocktest.test

import im.kirillt.sd.clocktest.EventsStatisticImpl
import org.junit.Before
import org.junit.Test

import java.time.Duration
import java.time.Instant

import org.junit.Assert.assertEquals

class EventsStatisticTest {

    val DELTA = 1e-3

    private var clock: SetableClock? = null
    private var statistic: EventsStatisticImpl? = null

    @Before
    fun setUp() {
        clock = SetableClock(Instant.EPOCH)
        statistic = EventsStatisticImpl(clock!!)
    }

    @Test
    fun testIncEvent() {
        statistic!!.incEvent("a")
        statistic!!.incEvent("a")
        assertEquals(2.0 / 60, statistic!!.getEventStatistic("a"), DELTA)
    }

    @Test
    fun testGetEventStatistic() {
        statistic!!.incEvent("a")
        clock!!.setNow(Instant.EPOCH.plus(Duration.ofMinutes(30)))
        statistic!!.incEvent("b")
        clock!!.setNow(Instant.EPOCH.plus(Duration.ofMinutes(61)))

        assertEquals(0.0, statistic!!.getEventStatistic("a"), DELTA)
        assertEquals(1.0 / 60, statistic!!.getEventStatistic("b"), DELTA)
        clock!!.setNow(Instant.EPOCH.plus(Duration.ofMinutes((30 + 61).toLong())))
        assertEquals(0.0, statistic!!.getEventStatistic("b"), DELTA)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllEventStatistic() {
        statistic!!.incEvent("a")
        statistic!!.incEvent("b")
        assertEquals(2.0 / 60, statistic!!.getAllEventStatistic(), DELTA)
    }

    @Test
    @Throws(Exception::class)
    fun testExpiration() {
        statistic!!.incEvent("a")
        assertEquals(1.0 / 60, statistic!!.getEventStatistic("a"), DELTA)
        clock!!.setNow(Instant.EPOCH.plus(Duration.ofMinutes(61)))
        assertEquals(0.0, statistic!!.getEventStatistic("a"), DELTA)
    }
}