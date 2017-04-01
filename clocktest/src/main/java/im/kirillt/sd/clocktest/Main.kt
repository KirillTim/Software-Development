package im.kirillt.sd.clocktest

import java.time.Clock


fun main(args: Array<String>) {
    val stat = EventsStatisticImpl(Clock.systemDefaultZone())
    stat.incEvent("a")
    stat.incEvent("a")
    stat.incEvent("b")
    stat.incEvent("c")
    stat.printStatistic()
}