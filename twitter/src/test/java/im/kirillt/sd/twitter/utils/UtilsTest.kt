package im.kirillt.sd.twitter.utils

import im.kirillt.sd.twitter.isCorrectHashTag
import org.junit.Assert
import org.junit.Test

/**
 * @author Kirill
 */
class UtilsTest {
    @Test
    fun isCorrectHashTagTestPositive1() {
        Assert.assertTrue(isCorrectHashTag("#foo"))
    }

    @Test
    fun isCorrectHashTagTestPositive2() {
        Assert.assertTrue(isCorrectHashTag("#123"))
    }

    @Test
    fun isCorrectHashTagTestNegative1() {
        Assert.assertFalse(isCorrectHashTag("foo"))
    }

    @Test
    fun isCorrectHashTagTestNegative2() {
        Assert.assertFalse(isCorrectHashTag("#foo bar"))
    }

    @Test
    fun isCorrectHashTagTestNegative3() {
        Assert.assertFalse(isCorrectHashTag("#"))
    }
}