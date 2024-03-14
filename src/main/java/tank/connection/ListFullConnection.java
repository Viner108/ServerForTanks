package tank.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListFullConnection extends Thread {
    ServerSocket serverSocketOutput;
    ServerSocket serverSocketInput;
    private static int PORT_INPUT = 8001;
    private static int PORT_OUTPUT = 8002;
    public static List<FullConnection> listFullConnection = new ArrayList<>();

    @Override
    public void run() {
        startConnection();
        while (true) {
            try {
                creatFullConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startConnection() {
        try {
            System.out.println("Start FullConnection");
            serverSocketOutput = new ServerSocket(PORT_OUTPUT);
            serverSocketInput = new ServerSocket(PORT_INPUT);
        } catch (Exception e) {
            try {
                Thread.sleep(500);
                System.out.println("Try connection by FullConnection");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void creatFullConnection() throws IOException {
        OutputConnection outputConnection = getOutputConnection();
        InputConnection inputConnection = getInputConnection();
        FullConnection fullConnection = new FullConnection(inputConnection, outputConnection);
        listFullConnection.add(fullConnection);
        inputConnection.start();
        outputConnection.start();
    }

    private OutputConnection getOutputConnection() throws IOException {
        Socket output = serverSocketOutput.accept();
        System.out.println("Created OutputConnection");
        OutputConnection outputConnection = new OutputConnection(output);
        return outputConnection;
    }

    private InputConnection getInputConnection() throws IOException {
        Socket input = serverSocketInput.accept();
        System.out.println("Created InputConnection");
        InputConnection inputConnection = new InputConnection(input);
        return inputConnection;
    }

    public static void removeFullConnection(InputConnection inputConnection) {
        FullConnection fullConnectionForRemove = null;
        for (FullConnection fullConnection : listFullConnection) {
            if (fullConnection.inputConnection == inputConnection) {
                fullConnectionForRemove = fullConnection;
            }
        }
        if (fullConnectionForRemove != null) {
            try {
                fullConnectionForRemove.inputConnection.input.close();
                fullConnectionForRemove.outputConnection.output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listFullConnection.remove(fullConnectionForRemove);
        }
    }

    public static void removeFullConnection(OutputConnection outputConnection) {
        FullConnection fullConnectionForRemove = null;
        for (FullConnection fullConnection : listFullConnection) {
            if (fullConnection.outputConnection == outputConnection) {
                fullConnectionForRemove = fullConnection;
            }
        }
        if (fullConnectionForRemove != null) {
            try {
                fullConnectionForRemove.inputConnection.input.close();
                fullConnectionForRemove.outputConnection.output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listFullConnection.remove(fullConnectionForRemove);
        }
    }

}
