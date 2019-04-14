package com.example.ict885101;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

    private boolean enabled = true;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(enabled)
            return super.onInterceptTouchEvent(arg0);

        return false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    private class TouchImageViewListener implements OnTouchListener {

        //
        // Remember last point position for dragging
        //
        private PointF last = new PointF();

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mScaleDetector.onTouchEvent(event);
            mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());

            if (state == NONE || state == DRAG || state == FLING) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        last.set(curr);
                        if (fling != null)
                            fling.cancelFling();
                        setState(DRAG);
                        //Modified Portion

                        getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (state == DRAG) {
                            float deltaX = curr.x - last.x;
                            float deltaY = curr.y - last.y;
                            float fixTransX = getFixDragTrans(deltaX, viewWidth,
                                    getImageWidth());
                            float fixTransY = getFixDragTrans(deltaY, viewHeight,
                                    getImageHeight());
                            matrix.postTranslate(fixTransX, fixTransY);
                            if (deltaX == 0 || normalizedScale == 1.0)
                                //Modified Portion

                                getParent().requestDisallowInterceptTouchEvent(false)
                            else
                                getParent().requestDisallowInterceptTouchEvent(true);
                            fixTrans();
                            last.set(curr.x, curr.y);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        //Modified Portion

                        getParent().requestDisallowInterceptTouchEvent(false)

                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        setState(NONE);
                        break;
                }
            }

            setImageMatrix(matrix);
            //
            // indicate event was handled
            //
            return true;
        }
    }//requestDisallowInterceptTouchEvent is Added When Touch Lister Occurs While Zoomed In

.......
    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return performClick();
        }

        @Override
        public void onLongPress(MotionEvent e) {
            performLongClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (fling != null) {
                //
                // If a previous fling is still active, it should be cancelled
                // so that two flings
                // are not run simultaenously.
                //
                fling.cancelFling();
            }
            fling = new Fling((int) velocityX, (int) velocityY);
            compatPostOnAnimation(fling);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            boolean consumed = false;

//Modified Portion
            if (state == NONE) {
                float targetZoom = (normalizedScale == minScale) ? maxScale
                        : minScale;
                DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom,
                        e.getX(), e.getY(), false);
                compatPostOnAnimation(doubleTap);
                consumed = true;
            }
            else
            {
                float targetZoom = (normalizedScale == minScale) ? maxScale
                        : minScale;
                DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom,
                        e.getX(), e.getY(), false);
                compatPostOnAnimation(doubleTap);
            }
            return consumed;
        }
    }