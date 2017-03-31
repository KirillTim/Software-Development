package im.kirillt.sd.clocktest


fun main(args: Array<String>) {
    val stat = EventsStatisticImpl(NormalClock())
    stat.incEvent("a")
    stat.incEvent("a")
    stat.incEvent("b")
    stat.incEvent("c")
    stat.printStatistic()
}