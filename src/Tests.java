import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

public class Tests {
    @Test
    public void testGUI() throws IOException {
        GUI gui = new GUI();

        //todo remove
        for (int i = 0; i < 9; i++) {
            gui.addToConsole("Some message: " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gui.redraw();
        }
        gui.updateBoard(ImageIO.read(new File("Assets/Test Files/Test 2.png")));
        gui.redraw();
        try {
            Thread.sleep(9999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
