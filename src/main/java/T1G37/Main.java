package T1G37;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class Main implements NativeKeyListener {

    public static StringBuilder keys = new StringBuilder();
    // define your functions here as well.
    public static String[] macros = new String[]{
            "off", "ctrlc", "loud", "quite", "silence", "bye", "restart", "white", "flash"
    };

    public static void main(String[] args) {
        // sets the logger for jnativehook to off
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        // registers the hook for it to actually listen to keys
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(new Main());
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // key handling
        String keyChar = NativeKeyEvent.getKeyText(e.getKeyCode());
        keys.append(keyChar.toLowerCase());
        System.out.println(keys);

        // actual macro logic
        for (String macro : macros) {
            // if it doesn't find a valid macro string, it will keep looping until it finds one
            if (!keys.toString().contains(macro)) {
                continue;
            }
            // all code below runs after it finds a valid macro, this is where you add your commands.
            // https://www.nirsoft.net/utils/nircmd.html

            if (macro.equals("ctrlc"))
                execute("clipboard clear");

            // turns monitor off
            if (macro.equals("off"))
                execute("monitor off");

            // sets system volume to 100
            if (macro.equals("loud"))
                execute("setsysvolume 65535");

            // sets system volume to 1
            if (macro.equals("quite"))
                execute("setsysvolume 1500");

            // mutes system volume
            if (macro.equals("silence"))
                execute("mutesysvolume 1 ");

            // logs out of the pc
            if (macro.equals("bye"))
                execute("exitwin logoff");

            // restarts pc
            if (macro.equals("restart"))
                new Thread(() -> {
                    execute("qboxcom \"BYE BYE\" \"IDFK\" exitwin reboot ");
                    try {
                        Thread.sleep(840);
                        execute("dlg \"\" \"\" click yes");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();

            // full screen empty txt file
            Path emptyTxt = Paths.get("white.txt");
            if (macro.equals("white"))
                try {
                    // close/creates txt files
                    execute("win close ititle \"white.txt - notepad\"");
                    Files.write(emptyTxt, new byte[0]);
                    // opens the txt
                    new ProcessBuilder(
                            "cmd", "/c", "start", "/max", "notepad.exe", "white.txt"
                    ).inheritIO().start();
                } catch (Exception a) {
                    a.printStackTrace();
                }

            //flashbang txt file
            if (macro.equals("flash"))
                new Thread(() -> {
                    try {
                        // close/creates txt files
                        execute("win close ititle \"white.txt - notepad\"");
                        Files.write(emptyTxt, new byte[0]);
                        // opens the txt
                        new ProcessBuilder(
                                "cmd", "/c", "start", "/max", "notepad.exe", "white.txt"
                        ).inheritIO().start();
                        // waits before closing the txt
                        Thread.sleep(300);
                        execute("win close ititle \"white.txt - notepad\"");
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }).start();

            // clears the keys
            keys = new StringBuilder();
            break;
        }
    }
    
    public void execute(String command) {
        try {
            Runtime.getRuntime().exec(String.format("nircmd.exe %s", command));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}