package game;

import javax.swing.ImageIcon;
import javax.swing.JButton;

// Tile.java - Represents one card/tile in the matching game
// Each tile can display two possible images: one when hidden and one when revealed.
class Tile extends JButton {
    
    //icon1: image shown when the tile is revealed
    //icon2: image shown when the tile is hidden
    ImageIcon icon1;
    ImageIcon icon2;
    
    //hidden: true if the tile is currently face down
    // noIcon: true if the tile has been removed from the board
    private boolean hidden, noIcon;

    // Constructor initializes the tile with two images: front and back
    public Tile(ImageIcon icon1, ImageIcon icon2) {
        this.icon1 = icon1;
        this.icon2 = icon2;
        setSize(100, 100);
        setFocusable(false); // prevnts the button from stealing keyboard focus
    }

    //Reveals the tile by showing its front image
    public synchronized void showTile() {
        setIcon(icon1);
        hidden = false;
    }

    // Hides the tile by showing the back image
    public synchronized void hideTile() {
        setIcon(icon2);
        hidden = true;
    }

    // Removes the icon entirely when match is found
    public synchronized void setNoIcon() {
        setIcon(null);
        noIcon = true;
    }

    // Returns the front image of the tile for comparison
    public ImageIcon getImage() {
        return icon1;
    }

    // Checks if the tile has been removed
    public synchronized boolean isNoIcon() {
        return noIcon;
    }

}
