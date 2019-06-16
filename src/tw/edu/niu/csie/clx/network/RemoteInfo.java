package tw.edu.niu.csie.clx.network;

public class RemoteInfo {
    protected String type;
    protected String role;
    protected String opposite;
    protected String command;
    protected String message;
    protected String source;
    protected String destination;
    protected String id;

    public RemoteInfo() {

    }


    public void setCommand(String command) {
        this.command = command;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOpposite(String opposite) {
        this.opposite = opposite;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }

    public String getOpposite() {
        return opposite;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }

    public String getId() {
        return id;
    }
}
