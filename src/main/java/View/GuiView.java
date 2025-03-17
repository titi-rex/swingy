/*
 * The MIT License
 *
 * Copyright 2025 Pril Wolf.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Pril Wolf
 */
public class GuiView extends JFrame {

    public static final int WINDOW_SIZE = 500;
    public static final String TITLE = "Swingy - The best RPG you never played";
    private JPanel characterSelectionPanel;
    private JPanel characterCreationPanel;
    private JPanel playPanel;

    private JMenuBar createMenuBar() {
        JMenuBar mbar = new JMenuBar();

        JMenuItem switchItem = new JMenuItem("Switch");
        switchItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        switchItem.addActionListener((ActionEvent e) -> {
            System.out.println("switch to cli requested");
        });

        DefaultButtonModel saveItemModel = new DefaultButtonModel();
        saveItemModel.setEnabled(false);
        saveItemModel.setMnemonic(KeyEvent.VK_S);
        saveItemModel.addActionListener((ActionEvent e) -> {
            System.out.println("save requested");
        });

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setModel(saveItemModel);

        DefaultButtonModel selectItemModel = new DefaultButtonModel();
        selectItemModel.addActionListener((ActionEvent e) -> {
            System.out.println("switch to selector requested");
        });
        selectItemModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("state changed");
            }
        });
        JMenuItem selectItem = new JMenuItem("Selector");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((ActionEvent e) -> {
            System.out.println("exit requested");
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        JMenu menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.add(switchItem);
        menu.add(saveItem);
        menu.add(selectItem);
        menu.add(exitItem);

        mbar.add(menu);
        return mbar;
    }

    private JPanel createSelectPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
            }
        });
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
            }
        });

        JPanel buttonBox = new JPanel(new FlowLayout());
        buttonBox.add(createButton);
        buttonBox.add(playButton);

        panel.add(new JLabel("Select your character"), BorderLayout.PAGE_START);
        panel.add(new JLabel("LINE_START"), BorderLayout.LINE_START);
        panel.add(new JLabel("CENTER"), BorderLayout.CENTER);
        panel.add(new JLabel("LINE_END"), BorderLayout.LINE_END);
        panel.add(buttonBox, BorderLayout.PAGE_END);
        return panel;
    }

    private JPanel createCreationPanel() {
        JPanel panel = new JPanel();
        panel.setVisible(false);
        return panel;
    }

    private JPanel createPlayPanel() {
        JPanel panel = new JPanel();
        panel.setVisible(false);
        return panel;
    }

    public GuiView() throws HeadlessException {
        super(TITLE);
        Dimension size = new Dimension(WINDOW_SIZE, WINDOW_SIZE);
        setSize(size);
        setMinimumSize(size);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Windows closed");
            }
        });

        setJMenuBar(createMenuBar());

        characterSelectionPanel = createSelectPanel();
        characterCreationPanel = createCreationPanel();
        playPanel = createPlayPanel();
        add(characterCreationPanel);
        add(playPanel);
        add(characterSelectionPanel);

    }

}
