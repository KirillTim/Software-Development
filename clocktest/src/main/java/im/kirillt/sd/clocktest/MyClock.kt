package im.kirillt.sd.clocktest

import java.time.Instant

interface MyClock {
    fun now(): Instant
}

class NormalClock() : MyClock {
    override fun now(): Instant = Instant.now()
}