package tw.edu.niu.csie.clx.main;

import tw.edu.niu.csie.clx.gui.MainWindow;
import tw.edu.niu.csie.clx.network.Client;
import tw.edu.niu.csie.clx.network.WebSocketManager;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static final String WS_SERVER_URL = "ws://localhost:3000/ws";
    public static final String SERVER_BASE_URL = "http://localhost:3000/control/";

    public static void main(String[] args) throws URISyntaxException {
        new MainWindow();
//        Client client = new Client();
//        client.connect();
    }
}
