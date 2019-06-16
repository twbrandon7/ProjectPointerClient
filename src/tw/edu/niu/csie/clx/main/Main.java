package tw.edu.niu.csie.clx.main;

import tw.edu.niu.csie.clx.gui.MainWindow;
import tw.edu.niu.csie.clx.network.Client;
import tw.edu.niu.csie.clx.network.WebSocketManager;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
//        new MainWindow();
        Client client = new Client();
        client.connect();
    }
}
