import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View

class MatrixView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.GREEN
        textSize = 30f
        typeface = Typeface.MONOSPACE
    }

    private val handler = Handler(Looper.getMainLooper())
    private val chars = "01"
    private val yPositions = IntArray(100) { 0 }
    private val charWidth = paint.measureText("0")

    private val drawRunnable = object : Runnable {
        override fun run() {
            invalidate()
            handler.postDelayed(this, 50L)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handler.post(drawRunnable)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(drawRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val columns = (width / charWidth).toInt()
        val adjustedColumns = columns.coerceAtMost(yPositions.size)

        for (i in 0 until adjustedColumns) {
            val char = chars.random().toString()
            val x = i * charWidth
            val y = yPositions[i].toFloat()
            canvas.drawText(char, x, y, paint)

            yPositions[i] += 20
            if (yPositions[i] > height) {
                yPositions[i] = 0
            }
        }
    }
}