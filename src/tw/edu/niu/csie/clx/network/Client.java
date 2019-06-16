package tw.edu.niu.csie.clx.network;

import com.google.gson.Gson;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Client implements WebSocketManager.WebSocketHandler {
    private static String SERVER_URL = "ws://localhost:3000/ws";

    private static int STATE_INITIAL = -1;
    private static int STATE_WAIT_ID = 0;
    private static int STATE_WAIT_ROLE = 1;
    private static int STATE_WAIT_CONTROL = 2;
    private static int STATE_START = 3;

    private String id = null;
    private WebSocketManager manager;
    private Gson gson = new Gson();
    private int currentState = STATE_INITIAL;

    public Client() throws URISyntaxException {
        manager = new WebSocketManager(new URI(SERVER_URL), this);
    }

    public void connect() {
        manager.connect();
    }

    public void response(String message) {
        RemoteInfo info = new RemoteInfo();
        info.setType("response");
        info.setMessage(message);
        manager.send(gson.toJson(info));
    }

    @Override
    public void onMessage(String message) {
        RemoteInfo remoteInfo = gson.fromJson(message, RemoteInfo.class);

        if(currentState == STATE_INITIAL) {
            this.currentState = STATE_WAIT_ID;
            if(remoteInfo.getType().equals("start") && remoteInfo.getMessage().equals("hello")) {
                RemoteInfo info = new RemoteInfo();
                info.setType("start");
                String json = gson.toJson(info);
                this.currentState = STATE_WAIT_ID;
                manager.send(json);
            }
        } else if(currentState == STATE_WAIT_ID && remoteInfo.getType().equals("start")) {
            this.id = remoteInfo.getId();

            this.currentState = STATE_WAIT_ROLE;

            RemoteInfo info = new RemoteInfo();
            info.setType("set");
            info.setRole("display");
            manager.send(gson.toJson(info));
        } else if(currentState == STATE_WAIT_ROLE && remoteInfo.getType().equals("set")) {
            if(remoteInfo.getMessage().equals("ok")) {
                System.out.println("READY!");
                System.out.println("ID : " + this.id);
                this.currentState = STATE_WAIT_CONTROL;
            }
        } else if(currentState == STATE_WAIT_CONTROL) {
            if(remoteInfo.getType().equals("set")) {
                this.currentState = STATE_START;
            }
        } else if(currentState == STATE_START) {
            if(remoteInfo.getType().equals("control")) {
                if(processControl(remoteInfo.getCommand())) {
                    this.response("ok");
                }
            } else {
                System.out.println("Invalid Message");
                System.out.println(message);
            }
        }
    }

    private boolean processControl(String command) {
        System.out.println(command);
        return true;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {

    }
}
