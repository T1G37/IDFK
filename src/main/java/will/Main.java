package will;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements NativeKeyListener {

    public static StringBuilder keys = new StringBuilder();
    // define your functions here as well.
    public static String[] macros = new String[]{
            "off", "ctrlc"
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

            if (macro.equals("ctrlc")) {
                execute("clipboard clear");
            }

            if (macro.equals("off")) {
                execute("monitor off");
            }


            // clears the keys
            keys = new StringBuilder();
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