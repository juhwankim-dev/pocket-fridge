package com.andback.pocketfridge.present.views.main.recipe

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecipeItemDecoration(private val height: Float, @ColorInt private val color: Int) : ItemDecoration() {
    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val left = parent.paddingStart
        val right = parent.width - parent.paddingEnd

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height
            canvas.drawRect(left.toFloat(), top, right.toFloat(), bottom, paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        var position = parent.getChildAdapterPosition(view)

        if(position > 0) {
            outRect.top = height.toInt()
        }
    }
}