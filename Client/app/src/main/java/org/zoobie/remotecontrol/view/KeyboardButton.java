package org.zoobie.remotecontrol.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import org.zoobie.pomd.remotecontrol.R;

public class KeyboardButton extends AppCompatButton {
    private static int keysCount;
    private static int keysPressed;
    static {
        keysCount = 0;
        keysPressed = 0;
    }

    private boolean pressed = false;
    private int background = R.drawable.button_background;
    private int backgroundPressed = R.drawable.button_background_pressed;

    public KeyboardButton(Context context) {
        super(context);
        this.setBackground(getResources().getDrawable(background,null));
    }

    public KeyboardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(getResources().getDrawable(background,null));
        keysCount++;
    }

    public KeyboardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackground(getResources().getDrawable(background,null));
    }

    public void toggle(){
        Drawable newBackground;
        if(!pressed){
            newBackground = getResources().getDrawable(backgroundPressed,null);
        } else {
            newBackground = getResources().getDrawable(background,null);
        }
        this.setBackground(newBackground);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean performLongClick() {
        super.performLongClick();
        return true;
    }

    public boolean isButtonPressed(){
        return pressed;
    }

    public void buttonPress(){
        pressed = !pressed;
    }

}