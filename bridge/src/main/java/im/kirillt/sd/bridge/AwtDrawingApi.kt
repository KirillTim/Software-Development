package im.kirillt.sd.bridge

import java.awt.Color
import java.awt.Font
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D

/**
 * @author kirill on 17.12.16.
 */
class AwtDrawingApi : DrawingApi, Frame() {

    override val areaWidth = 400
    override val areaHeight = 400

    init {
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(we: WindowEvent) {
                System.exit(0)
            }
        })
        setSize(areaWidth, areaHeight)
        isVisible = true
    }

    private val graphics2D: Graphics2D = graphics as Graphics2D

    override fun drawCircle(x: Double, y: Double, radius: Double) {
        val ga = graphics2D
        ga.paint = Color.WHITE
        ga.fill(Ellipse2D.Float((y - radius).toFloat(), (x - radius).toFloat(), (radius * 2).toFloat(), (radius * 2).toFloat()))
    }

    override fun drawText(text: String, x: Double, y: Double) {
        graphics2D.color = Color.BLACK
        graphics2D.font = Font("TimesRoman", Font.PLAIN, 14)
        graphics2D.drawString(text, x.toFloat(), y.toFloat())
    }

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        graphics2D.color = Color.BLACK
        graphics2D.drawLine(y1.toInt(), x1.toInt(), y2.toInt(), x2.toInt())
    }

    override fun render() {
        super.paint(graphics2D)
    }
}
