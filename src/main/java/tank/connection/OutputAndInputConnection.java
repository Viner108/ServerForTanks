package tank.connection;

public class OutputAndInputConnection {
    InputConnection inputConnection;
    OutputConnection outputConnection;

    public OutputAndInputConnection(InputConnection inputConnection, OutputConnection outputConnection) {
        this.inputConnection = inputConnection;
        this.outputConnection = outputConnection;
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
