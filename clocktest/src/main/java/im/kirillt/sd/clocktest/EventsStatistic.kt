package im.kirillt.sd.clocktest

data class Event(val name: String)

interface EventsStatistic {
    fun incEvent(event: Event)
    fun getEventStatistic(event: Event): Double
    fun getAllEventsStatistic(): Map<Event, Double>
    fun printStatistic()
}