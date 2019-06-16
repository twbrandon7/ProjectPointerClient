package tw.edu.niu.csie.clx.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;

    private JLabel mainLabel;

    public MainWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setTitle("遠端控制程式");
        this.setVisible(true);
        this.setLayout(null);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocation((int) screenSize.getWidth() - WINDOW_WIDTH, (int) screenSize.getHeight() - WINDOW_HEIGHT - 35);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainWindow.this.dispose();
            }
        });
        //setExtendedState(ICONIFIED);

        String text = "";
        mainLabel = new JLabel();
        mainLabel.setSize(this.getWidth(), this.getHeight());
        mainLabel.setLocation(0, 0);
        this.add(mainLabel);
        this.setMessage(text);

        this.revalidate();
    }

    private void setMessage(String message) {
        mainLabel.setText("<html><div style='text-align: center;'>" + message + "</div></html>");
    }

}
