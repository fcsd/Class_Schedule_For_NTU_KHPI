package com.khpi.classschedule.data

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.khpi.classschedule.R

import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedList
import java.util.Queue
import android.graphics.Bitmap

abstract class SwipeHelper(private val context: Context, private val recyclerView: RecyclerView)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var buttons = mutableListOf<UnderlayButton>()
    private var gestureDetector: GestureDetector? = null
    private var swipedPos = -1
    private var swipeThreshold = 0.5f
    private val buttonsBuffer: MutableMap<Int, MutableList<UnderlayButton>>
    private var recoverQueue: Queue<Int>? = null
    private val BUTTON_WIDTH = context.resources.getDimension(R.dimen._80sdp)

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            for (button in buttons) {
                if (button.onClick(e.x, e.y))
                    break
            }
            return true
        }
    }

    private val onTouchListener = View.OnTouchListener { view, e ->
        if (swipedPos < 0) return@OnTouchListener false
        val point = Point(e.rawX.toInt(), e.rawY.toInt())

        val swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos)
        val swipedItem = swipedViewHolder.itemView
        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)

        if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_MOVE) {
            if (rect.top < point.y && rect.bottom > point.y)
                gestureDetector?.onTouchEvent(e)
            else {
                recoverQueue?.add(swipedPos)
                swipedPos = -1
                recoverSwipedItem()
            }
        }
        false
    }

    init {
        this.buttons = ArrayList()
        this.gestureDetector = GestureDetector(context, gestureListener)
        this.recyclerView.setOnTouchListener(onTouchListener)
        buttonsBuffer = HashMap()
        recoverQueue = object : LinkedList<Int>() {
            override fun add(element: Int): Boolean {
                return if (contains(element))
                    false
                else
                    super.add(element)
            }
        }

        attachSwipe()
    }


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition

        if (swipedPos != pos) {
            recoverQueue?.add(swipedPos)
        }

        swipedPos = pos

        if (buttonsBuffer.containsKey(swipedPos)) {
            buttonsBuffer[swipedPos]?.let { buttons = it.toMutableList() }
        } else {
            buttons.clear()
        }

        buttonsBuffer.clear()
        swipeThreshold = 0.5f * buttons.size.toFloat() * BUTTON_WIDTH.toFloat()
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder?): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView

        if (pos < 0) {
            swipedPos = pos
            return
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer = mutableListOf<UnderlayButton>()

                if (!buttonsBuffer.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer)
                    buttonsBuffer[pos]?.let {  buffer = it }
                } else {
                    buttonsBuffer[pos]?.let {  buffer = it }
                }

                translationX = dX * buffer.size.toFloat() * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive)
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (recoverQueue?.isNotEmpty() == true) {
            val pos = recoverQueue?.poll() ?: break
            if (pos > -1) {
                recyclerView.adapter.notifyItemChanged(pos)
            }
        }
    }

    private fun drawButtons(c: Canvas, itemView: View, buffer: List<UnderlayButton>, pos: Int, dX: Float) {
        val textSize = context.resources.getDimension(R.dimen._12sdp)
        val corners = 10f
        var right = (itemView.right - itemView.paddingRight).toFloat()
        val dButtonWidth = -1 * dX / buffer.size

        for (button in buffer) {
            val left = right - dButtonWidth
            val top = (itemView.top + itemView.paddingTop).toFloat()
            val bottom = (itemView.bottom - itemView.paddingBottom).toFloat()
            button.onDraw(c, RectF(left, top, right, bottom), pos, corners, textSize)
            right = left
        }
    }

    private fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder, underlayButtons: MutableList<UnderlayButton>)

    class UnderlayButton(private val text: String,
                         private val imageBitmap: Bitmap,
                         private val color: Int,
                         private val clickListener: UnderlayButtonClickListener) {
        private var pos: Int = 0
        private var clickRegion: RectF? = null

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion?.contains(x, y) == true) {
                clickListener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(c: Canvas, rect: RectF, pos: Int, corners: Float, textSize: Float) {
            val p = Paint()

            // Draw background
            p.color = color
            c.drawRoundRect(rect, corners, corners, p)

            val r = Rect()
            val cHeight = rect.height()
            val cWidth = rect.width()

            //draw image
            p.textAlign = Paint.Align.LEFT
            var x = cWidth / 2f - imageBitmap.width / 2f
            var y = cHeight / 3f - imageBitmap.height / 3f
            c.drawBitmap(imageBitmap, rect.left + x, rect.top + y, p)


            // Draw Text
            p.color = Color.WHITE
            p.textSize = textSize
            p.getTextBounds(text, 0, text.length, r)
            x = cWidth / 2f - r.width() / 2f - r.left.toFloat()
            y = cHeight / 3f * 2f + r.height() / 3f * 2f - r.bottom
            c.drawText(text, rect.left + x, rect.top + y, p)

            clickRegion = rect
            this.pos = pos
        }
    }

    interface UnderlayButtonClickListener {
        fun onClick(pos: Int)
    }
}
