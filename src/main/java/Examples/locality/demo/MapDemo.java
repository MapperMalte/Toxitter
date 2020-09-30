package Examples.locality.demo;

import Examples.locality.geometry.LeyMap;
import Examples.locality.geometry.LeyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapDemo extends JFrame
{
    LeyMap hub = new LeyMap();

    public MapDemo() {
        super("Lines Drawing Demo");

        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        hub.addLeypoint(new LeyPoint(100,100,"#ff00ff"));
        hub.addLeypoint(new LeyPoint(345,250,"#ff00ff"));
        hub.addLeypoint(new LeyPoint(220,500,"#00ff00"));
        hub.addLeypoint(new LeyPoint(520,500,"#ffff00"));

        for(int i = 0; i < 150; i++)
        {
            hub.addLeypoint(new LeyPoint(
                    (int)(Math.random()*1200),
                    (int)(Math.random()*800),
                    String.format("#%06x",(int) (Math.random()*16777215) )
            ));
        }

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("MouseClick: "+e.getX());
                selectedLey = hub.getCorrespondingLeyPoint(e.getX(),e.getY());
                repaint();
            }
        });
    }

    private LeyPoint selectedLey;

    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        LeyPoint last = null;

        for(int x = 0; x < 1200; x++)
        {
            for(int y = 0; y < 800; y++)
            {
                LeyPoint p = hub.getCorrespondingLeyPoint(x,y);
                if ( last == null )
                {
                    last = p;
                } else if (!p.equals(last)) {
                    p.addNeighbour(last);
                    last.addNeighbour(p);
                }

                if ( !p.neighbourOf(selectedLey) )
                {
                    g2d.setColor(Color.decode(p.colorHex));
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.drawLine(x,y,x,y);
                last = p;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MapDemo().setVisible(true);
            }
        });
    }

}
