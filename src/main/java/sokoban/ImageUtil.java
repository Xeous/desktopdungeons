package sokoban;

import javax.swing.*;

public class ImageUtil {

    public static ImageIcon createImageIcon(String path, Class clazz) {
        java.net.URL imgURL = clazz.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
