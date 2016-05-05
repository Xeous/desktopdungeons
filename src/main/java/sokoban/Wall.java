package sokoban;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Wall extends Actor {

    private Image image;

    public Wall(int x, int y) {
        super(x, y);

        String path = "/sokoban/wall.png";
        ImageIcon iia = ImageUtil.createImageIcon(path, getClass());
        image = iia.getImage();
        this.setImage(image);

    }
}