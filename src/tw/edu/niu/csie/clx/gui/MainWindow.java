package tw.edu.niu.csie.clx.gui;

import tw.edu.niu.csie.clx.main.Main;
import tw.edu.niu.csie.clx.network.Client;
import tw.edu.niu.csie.clx.network.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends JFrame implements Client.EventHandler {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;

    private JLabel mainLabel;
    private Client client;

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
                MainWindow.this.client.close();
                System.exit(0);
            }
        });

        mainLabel = new JLabel();
        mainLabel.setSize(this.getWidth(), this.getHeight());
        mainLabel.setLocation(0, 0);
        this.add(mainLabel);

        setMessage("連線中...");

        try {
            this.client = new Client(this);
        } catch (Exception e) {
            setMessage("系統錯誤!");
        }

        this.revalidate();

        if(client != null) {
            client.connect();
        }
    }

    private void setMessage(String message) {
        mainLabel.setText("<html><div style='text-align: center; font-size:16px; width: "+(WINDOW_HEIGHT*0.8)+"px;'>" + message + "</div></html>");
        this.revalidate();
    }

    @Override
    public boolean onCommand(String command) {
        if(command.equals("UP")) {
            simulateKey(KeyEvent.VK_UP);
        } else if(command.equals("DOWN")) {
            simulateKey(KeyEvent.VK_DOWN);
        } else {
            return false;
        }
        return true;
    }

    private void simulateKey(int keyCode) {
        try {
            Robot robot = new Robot();

            // Simulate a key press
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onIdReady(String id) {
        String controlUrl = Main.SERVER_BASE_URL + id;
        Connection connection = new Connection("http://tinyurl.com/api-create.php?url="+controlUrl, "GET");
        String response = connection.execute();
        if(connection.getResponseCode() != 200) {
            this.setMessage("短網址建立失敗!");
            return;
        }
        String text = String.format("<img src='https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=%s'><br/>", response);
        text += "控制網址 : <br/>" + response;
        this.setMessage(text);
        System.out.println(id);
    }

    @Override
    public void onReady() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 5;
            @Override
            public void run() {
                if(count < 0) {
                    setExtendedState(ICONIFIED);
                    this.cancel();
                    setMessage("關閉視窗即可結束!");
                } else {
                    setMessage(String.format("準備就緒!<br/>%d 秒後開始!", count));
                    count--;
                }
            }
        }, 0, 1000);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        setMessage("伺服器連線中斷!");
    }
}
