package ghcharactertracker.com.ghct.customControls;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LockableScrollView extends NestedScrollView {
    private boolean scrollable;

    public LockableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setScrollable(true);
    }

    public LockableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setScrollable(true);
    }

    public LockableScrollView(Context context) {
        super(context);
        this.setScrollable(true);
    }

    public void setScrollable(boolean enabled) {
        this.scrollable = enabled;
    }

    public boolean getScrollable() {
        return this.scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                return this.getScrollable() && super.onTouchEvent(ev);
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.getScrollable() && super.onInterceptTouchEvent(ev);
    }
}
