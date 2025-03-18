/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package fr.ft.swingo;

import View.GuiView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Pril Wolf
 */
public class Swingo {

    public static void main(String[] args) {
        GuiView gui = new GuiView();
        gui.showCreatorView();
        gui.setVisible(true);
        System.out.println("main end");
    }
}
