package knottythreadsgame.view;

import knottythreadsgame.model.GameModel;

import javax.swing.*;
import java.awt.event.*;

public class Menu extends JDialog {
    private JPanel contentPane;
    private JButton buttonStart;
    private JButton buttonCancel;
    private ButtonGroup levels;
    private JRadioButton easyRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton hardRadioButton;
    private JRadioButton IMPOSSIBLERadioButton;

    public Menu() {
        //Add action commands to buttons
        easyRadioButton.setActionCommand("easy");
        mediumRadioButton.setActionCommand("medium");
        hardRadioButton.setActionCommand("hard");
        IMPOSSIBLERadioButton.setActionCommand("impossible");

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonStart);

        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onStart();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void showMenu() {
        Menu dialog = new Menu();
        dialog.pack();
        dialog.setVisible(true);
    }

    private void onStart() {
        if (levels.getSelection() != null) {
            dispose();

            //Инициировать игровое поле
            GameModel.start(levels.getSelection().getActionCommand());
            setVisible(false);
        } else
            JOptionPane.showMessageDialog(null, "Specify the level, please");
    }

    private void onCancel() {
        dispose();
        System.exit(0);
    }
}
