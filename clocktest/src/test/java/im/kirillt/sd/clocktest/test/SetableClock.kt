package im.kirillt.sd.clocktest.test

import im.kirillt.sd.clocktest.MyClock
import java.time.Instant

class SetableClock(private var now: Instant) : MyClock {

    fun setNow(now: Instant) {
        this.now = now
    }

    override fun now(): Instant {
        return now
    }
}
