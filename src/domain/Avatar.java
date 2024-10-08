package domain;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.Serializable;
import javax.swing.ImageIcon;


public class Avatar implements Serializable {
    
    public static ImageIcon getAvatarImage(Integer avatarType) {
        ImageIcon selectedAvatar = null;
        switch (avatarType) {
            case 1:
            	selectedAvatar = new ImageIcon("images/avatar1.png");
                break;
            case 2:
            	selectedAvatar = new ImageIcon("images/avatar2.png");
                break;
            case 3:
            	selectedAvatar = new ImageIcon("images/avatar3.png");
                break;
            case 4:
            	selectedAvatar = new ImageIcon("images/avatar4.png");
                break;
            case 5:
            	selectedAvatar = new ImageIcon("images/avatar5.png");
                break;
            case 6:
            	selectedAvatar = new ImageIcon("images/avatar6.png");
                break;
            default:
                break;
        }
        return selectedAvatar;
    }
}
