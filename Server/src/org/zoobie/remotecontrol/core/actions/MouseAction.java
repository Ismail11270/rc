package org.zoobie.remotecontrol.core.actions;

import java.awt.*;

public class MouseAction implements Action {

    private static final int SCROLL_SIZE = 1;
    private Robot robot;
    private final byte[] command;
    private int keyCode;

    public MouseAction(Robot robot, byte... command) {
        this.robot = robot;
        this.command = command;
    }


    @Override
    public void performAction() {
        if (command[1] == Actions.MOUSE_CLICK_ACTION || command[1] == Actions.MOUSE_DOWN_ACTION || command[1] == Actions.MOUSE_UP_ACTION) {
            if (command[2] >= 1 && command[2] <= 3) {
                keyCode = (int) Math.pow(2, 9 + command[2]);
                performKeyAction(command[1]);
            }
        } else if (command[1] == Actions.MOUSE_MOVE_ACTION) {
            int x = command[2], y = command[3];
            x *= -1;
            y *= -1;
            moveBy(x,y);
        } else if(command[1] == Actions.MOUSE_SCROLL_ACTION){
            int scroll = command[2];
            performScroll(scroll);
        }
    }

    private void performScroll(int scroll) {
        int x = SCROLL_SIZE;
        if(scroll > 0) {
            x *= -1;
        }
        robot.mouseWheel(x);
    }

    private void performKeyAction(byte actionCode) {
        switch(actionCode){
            case Actions.MOUSE_CLICK_ACTION:
                robot.mousePress(keyCode);
                robot.mouseRelease(keyCode);
                break;
            case Actions.MOUSE_DOWN_ACTION:
                robot.mousePress(keyCode);
                break;
            case Actions.MOUSE_UP_ACTION:
                robot.mouseRelease(keyCode);
                break;
        }
    }

    private void moveBy(int x, int y){
        Point pos = MouseInfo.getPointerInfo().getLocation();
        x += pos.x;
        y += pos.y;
        robot.mouseMove(x, y);
    }

}
