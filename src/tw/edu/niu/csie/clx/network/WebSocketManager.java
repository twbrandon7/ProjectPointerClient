package tw.edu.niu.csie.clx.network;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketManager extends WebSocketClient {

    WebSocketHandler handler = null;

    public WebSocketManager(URI serverUri) {
        super(serverUri);
    }

    public WebSocketManager(URI serverUri, WebSocketHandler handler) {
        super(serverUri);
        this.handler = handler;
    }

    public void setHandler(WebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        System.out.println("new connection opened");
        if(handler != null) {
            handler.onOpen(handshakeData);
        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
        if(handler != null) {
            handler.onMessage(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
        if(handler != null) {
            handler.onClose(code, reason, remote);
        }
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public interface WebSocketHandler {
        void onMessage(String message);
        void onClose(int code, String reason, boolean remote);
        void onOpen(ServerHandshake handshakeData);
    }
}
