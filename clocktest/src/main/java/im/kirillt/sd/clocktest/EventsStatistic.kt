package im.kirillt.sd.clocktest

import java.time.Clock
import java.time.LocalDateTime

data class Event(val name: String)

interface EventsStatistic {
    fun incEvent(event: Event)
    fun getEventStatistic(event: Event): Int
    fun getAllEventsStatistic(): Map<Event, Int>
    fun printStatistic()
}

class EventStatisticImpl(val clock: Clock) : EventsStatistic {
    val eventsStorage = mutableMapOf<Event, MutableList<LocalDateTime>>()
    override fun incEvent(event: Event) {
        eventsStorage.getOrPut(event, { mutableListOf() }).add(LocalDateTime.now(clock))
    }

    override fun getEventStatistic(event: Event): Int {
        val now = LocalDateTime.now(clock)
        val actualEvents = eventsStorage.getOrDefault(event, mutableListOf()).filter { now.minusHours(1) < it }.toMutableList()
        eventsStorage.put(event, actualEvents)
        return actualEvents.size
    }

    override fun getAllEventsStatistic(): Map<Event, Int>
            = eventsStorage.keys.map { it to getEventStatistic(it) }.toMap()


    override fun printStatistic() {
        println(getAllEventsStatistic())
    }

}