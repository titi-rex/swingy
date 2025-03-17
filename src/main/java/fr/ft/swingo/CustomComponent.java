/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ft.swingo;

/**
 *
 * @author reina
 */
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class CustomComponent extends JPanel implements ActionListener, MouseListener {

    int circleX = 150;
    int circleY = 0;

    public CustomComponent() {
        setBackground(new Color(0, 255, 255));

        addMouseListener(this);
        new Timer(16, this).start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Happy Coding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CustomComponent csc = new CustomComponent();

        frame.add(csc);

        frame.setSize(300, 300);

        frame.setVisible(true);
    }

    private void step() {
        circleY++;
        if (circleY > getHeight()) {
            circleY = 0;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillOval(circleX - 10, circleY, 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        circleX = me.getX();
        circleY = me.getY();
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }
}
