package tank.connection;

public class FullConnection {
    InputConnection inputConnection;
    OutputConnection outputConnection;

    public FullConnection(InputConnection inputConnection,
                          OutputConnection outputConnection) {
        this.outputConnection = outputConnection;
        this.inputConnection = inputConnection;
    }

    public InputConnection getInputConnection() {
        return inputConnection;
    }

    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    public OutputConnection getOutputConnection() {
        return outputConnection;
    }

    public void setOutputConnection(OutputConnection outputConnection) {
        this.outputConnection = outputConnection;
    }
}
