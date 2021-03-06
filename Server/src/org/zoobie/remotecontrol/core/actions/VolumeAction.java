package org.zoobie.remotecontrol.core.actions;


import java.io.IOException;

public class VolumeAction implements Action {

    private OS os;
    private final byte[] command;
    private Runtime runtime;
    enum OS {
        WINDOWS,LINUX,MACOS
    }
    public VolumeAction(byte... command) {
        this.runtime = Runtime.getRuntime();
        if (System.getProperty("os.name").equals("Windows 10")) {
            os = OS.WINDOWS;
        }
        this.command = command;
    }

    @Override
    public void performAction() {
        try {
            switch (command[1]) {
                case Actions.VOLUME_DOWN_ACTION:
                    down();
                    break;
                case Actions.VOLUME_UP_ACTION:
                    up();
                    break;
                case Actions.VOLUME_MUTE_ACTION:
                    mute();
                    break;
            }
        }catch(IOException e){
            System.out.println("Unsupported volume action...");
        }

    }

    private void up() throws IOException {
        if (os== OS.WINDOWS)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]175)"});
        else if(os == OS.LINUX)
            runtime.exec("amixer -q sset Master 3%+");
    }

    private void down() throws IOException {
        if (os== OS.WINDOWS)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]174)"});
        else if(os == OS.LINUX)
            runtime.exec("amixer -q sset Master 3%-");
    }

    private void mute() throws IOException {
        if (os== OS.WINDOWS)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]173)"});
        else if(os == OS.LINUX)
            runtime.exec("amixer -q sset Master toggle");
    }
}
