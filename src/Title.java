import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Title extends JPanel implements MouseListener, MouseMotionListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int mouseX, mouseY;
    private Rectangle bounds;
    private boolean leftClick = false;
    private BufferedImage title, instructions, play;
    private MainGame mainGame;
    private BufferedImage[] playButton = new BufferedImage[2];
    private Timer timer;


    public Title(MainGame mainGame){
        try {
            title = ImageIO.read(GridBoard.class.getResource("/Title.png"));
            instructions = ImageIO.read(GridBoard.class.getResource("/arrow.png"));
            play = ImageIO.read(GridBoard.class.getResource("/play.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer = new Timer(1000/60, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }

        });
        timer.start();
        mouseX = 0;
        mouseY = 0;

        playButton[0] = play.getSubimage(0, 0, 100, 80);
        playButton[1] = play.getSubimage(100, 0, 100, 80);

        bounds = new Rectangle((mainGame.WIDTH+120)/2 - 50, mainGame.HEIGHT/2 - 100, 100, 80);
        this.mainGame = mainGame;



    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(leftClick && bounds.contains(mouseX, mouseY)) {
            try {
                mainGame.startTetris();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, mainGame.WIDTH + 120, mainGame.HEIGHT);

        g.drawImage(title, (mainGame.WIDTH+120)/2 - title.getWidth()/2, MainGame.HEIGHT/2 - title.getHeight()/2 - 200, null);
        g.drawImage(instructions, (mainGame.WIDTH+120)/2 - instructions.getWidth()/2,
                MainGame.HEIGHT/2 - instructions.getHeight()/2 + 150, null);

        if(bounds.contains(mouseX, mouseY))
            g.drawImage(playButton[0], (mainGame.WIDTH+120)/2 - 50, mainGame.HEIGHT/2 - 100, null);
        else
            g.drawImage(playButton[1], (mainGame.WIDTH+120)/2 - 50, mainGame.HEIGHT/2 - 100, null);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftClick = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftClick = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
