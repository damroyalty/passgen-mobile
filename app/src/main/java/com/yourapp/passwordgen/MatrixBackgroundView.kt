package com.yourapp.passwordgen

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class MatrixBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.GREEN
        typeface = Typeface.MONOSPACE
        textSize = 20f
    }

    private val symbols = "01アイウエオカキクケコサシスセソ".toCharArray()
    private var columns: Int = 0
    private lateinit var symbolPositions: FloatArray
    private lateinit var symbolSpeeds: FloatArray

    init {
        post {
            columns = (width / paint.textSize).toInt()
            symbolPositions = FloatArray(columns) { Random.nextFloat() * height }
            symbolSpeeds = FloatArray(columns) { Random.nextFloat() * 10 + 5 }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // dark semi-transparent background
        canvas.drawColor(Color.argb(150, 0, 0, 0))

        for (i in 0 until columns) {
            val xPos = i * paint.textSize

            paint.alpha = 255
            canvas.drawText(
                symbols.random().toString(),
                xPos,
                symbolPositions[i],
                paint
            )

            for (j in 1..10) {
                val yPos = symbolPositions[i] - j * paint.textSize
                if (yPos > 0) {
                    paint.alpha = 255 - (25 * j)
                    canvas.drawText(
                        symbols.random().toString(),
                        xPos,
                        yPos,
                        paint
                    )
                }
            }

            symbolPositions[i] += symbolSpeeds[i]

            if (symbolPositions[i] > height + 10 * paint.textSize) {
                symbolPositions[i] = 0f
                symbolSpeeds[i] = Random.nextFloat() * 10 + 5
            }
        }

        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        columns = (w / paint.textSize).toInt()
        symbolPositions = FloatArray(columns) { Random.nextFloat() * h }
        symbolSpeeds = FloatArray(columns) { Random.nextFloat() * 10 + 5 }
    }
}