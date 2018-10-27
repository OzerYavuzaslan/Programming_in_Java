
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Fire
{
    private int x_coordinate, y_coordinate;

    public Fire(int x_coordinate, int y_coordinate) {
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public int getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public int getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(int y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
}

public class Game_Class extends JPanel implements KeyListener, ActionListener
{
    Timer timer = new Timer(1, this);
    
    private int fire_direction_y = 2;
    private BufferedImage spaceShip_image;
    private int game_time = 0, weapon_fire_num = 0;
    private int ball_location_x = 0, ball_direction_x = 4;
    private ArrayList<Fire> fire_list = new ArrayList<>();
    private int spaceShip_location_x = 0, spaceShip_direction_x = 20;

    public boolean endGame_method()
    {
        for(Fire fire: fire_list)
        {
            if(new Rectangle(fire.getX_coordinate(), 
                fire.getY_coordinate(), 10, 20).intersects(new Rectangle(ball_location_x, 0, 20, 20)))
                return true;
        }
        return false;
    }
    
    public Game_Class()
    {
        try
        {
            spaceShip_image = ImageIO.read(new FileImageInputStream(new File("SpaceShip.png")));
        }
        catch (IOException ex)
        {
            Logger.getLogger(Game_Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics graphics)
    {
        super.paint(graphics);
        
        game_time += 5;
        
        graphics.setColor(Color.red);
        graphics.fillOval(ball_location_x, 0, 20, 20);
        graphics.drawImage(spaceShip_image, spaceShip_location_x, 545, 
            spaceShip_image.getWidth() / 10, spaceShip_image.getHeight() / 10, this);
        
        for(Fire fire: fire_list)
        {
            if(fire.getY_coordinate() < 0)
                fire_list.remove(fire);
        }
        
        graphics.setColor(Color.BLUE);
        
        for(Fire fire: fire_list)
            graphics.fillRect(fire.getX_coordinate(), fire.getY_coordinate(), 10, 20);
        
        if(endGame_method())
        {
            timer.stop();
            String message = "You've won!\n Weapon Fired: " + weapon_fire_num + " times.\n"
                    + "Game Time: " + game_time / 1000.0;
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint()
    {
        super.repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        int ship_direction = event.getKeyCode();
        
        if(ship_direction == KeyEvent.VK_LEFT)
        {
            if(spaceShip_location_x < 1)
                spaceShip_location_x = 0;
            else
                spaceShip_location_x -= spaceShip_direction_x;
        }
        else if(ship_direction == KeyEvent.VK_RIGHT)
        {
            if(spaceShip_location_x > 900)
                spaceShip_location_x = 900;
            else
                spaceShip_location_x += spaceShip_direction_x;
        }
        else if(ship_direction == KeyEvent.VK_SPACE)
        {
            fire_list.add(new Fire((spaceShip_location_x + 15), 530));
            weapon_fire_num++;            
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(Fire fire: fire_list)
            fire.setY_coordinate(fire.getY_coordinate() - fire_direction_y);
        
        ball_location_x += ball_direction_x;
        
        if(ball_location_x > 910)
            ball_direction_x *= -1;
        
        if(ball_location_x < 1)
            ball_direction_x *= -1;
        
        repaint();
    }
}
