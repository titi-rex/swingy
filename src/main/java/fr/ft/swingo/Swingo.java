/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package fr.ft.swingo;

import fr.ft.swingo.View.GuiView;
import javax.swing.JFrame;

/**
 *
 * @author Pril Wolf
 */
public class Swingo {

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        GuiView gui = new GuiView();
        gui.showCreatorView();
        gui.setVisible(true);
        System.out.println("main end");
    }
}
