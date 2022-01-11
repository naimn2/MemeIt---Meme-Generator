package muflihun.naim.memeit.utils

import android.view.MotionEvent
import android.view.View

class DragableViewMaker {
    companion object {
        fun makeViewDragable(view: View) {
            view.setOnTouchListener(object: View.OnTouchListener{
                var dX: Float = 0.0f
                var dY: Float = 0.0f
                override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            dX = view?.x!! - event.rawX;
                            dY = view?.y!! - event.rawY;
                        }
                        MotionEvent.ACTION_MOVE -> {
                            view?.animate()
                                ?.x(event.rawX + dX)
                                ?.y(event.rawY + dY)
                                ?.setDuration(0)
                                ?.start()
                        }
                        else -> {
                            return false
                        }
                    }
                    return true
                }
            })
        }
    }
}