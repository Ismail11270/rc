package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Connector;

public class TouchPadKeysGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    private static final String TAG = "Touch_Pad_Keys_Listener";
    private Context context;
    private GestureDetector gestureDetector;
    private Connector connector;
    private boolean tapped = false;
    private View view;
    private TouchPadGestureListener touchpadListener;

    public TouchPadKeysGestureListener(Context context, Connector connector,TouchPadGestureListener touchpadListener) {
        this.context = context;
        this.connector = connector;
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);
        this.touchpadListener = touchpadListener;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
//        Log.i(TAG,"POINTER " + event.getPointerCount());
        view = v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "Action up");
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,event.getPointerCount() + " Pointers found");

//                if (event.getPointerCount() > 1) {
//                    MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
//                    event.getPointerCoords(1,coords);
//                    touchpadListener.move((int)coords.x,(int)coords.y);
//                }
//                else touchpadListener.onTouch(view,event);
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        sendClick(view);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, "SCROLLING");
        return true;
    }

    private void sendClick(View v) {
        int keyCode = Integer.parseInt(v.getTag().toString());
        Log.d(TAG, "Key " + keyCode);
        byte[] bytes = new byte[]{Actions.MOUSE_ACTION,Actions.MOUSE_KEY_ACTION, (byte) keyCode};
        connector.sendUdp(bytes);
    }
}