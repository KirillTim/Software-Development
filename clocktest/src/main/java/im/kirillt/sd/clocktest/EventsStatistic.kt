package im.kirillt.sd.clocktest

import java.time.Clock
import java.time.Duration
import java.time.Instant


interface EventsStatistic {
    fun incEvent(event: String)
    fun getEventStatistic(event: String): Double
    fun getAllEventStatistic(): Double
    fun printStatistic()
}

class EventsStatisticImpl(val clock: Clock) : EventsStatistic {
    private val eventsStorage = mutableMapOf<String, MutableList<Instant>>()
    override fun incEvent(event: String) {
        val new = eventsStorage.getOrDefault(event, mutableListOf()) + clock.instant()
        eventsStorage.put(event, new.toMutableList())
    }

    override fun getEventStatistic(event: String): Double {
        val deadline = clock.instant().minus(Duration.ofHours(1))
        val actualEvents = eventsStorage.getOrDefault(event, mutableListOf()).filter { it.isAfter(deadline) }.toMutableList()
        eventsStorage.put(event, actualEvents)
        return actualEvents.size / 60.0
    }

    override fun getAllEventStatistic(): Double
            = eventsStorage.keys.map(this::getEventStatistic).sum()


    override fun printStatistic() {
        for ((event, stat) in eventsStorage) {
            println("$event: $stat")
        }
    }
}