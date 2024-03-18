package tank.connection;


import tank.objectStream.MyObjectOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FullConnection extends Thread {
    ServerSocket serverSocketOutput;
    private static int PORT_OUTPUT = 8002;
    ServerSocket serverSocketInput;
    private static int PORT_INPUT = 8001;
    private ExecutorService executorService;
    public static List<OutputAndInputConnection> list = new ArrayList<>();

    public FullConnection( ) {
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        startConnection();
        while (true) {
            try {
                creatFullConnection();
            } catch (IOException e) {
                e.printStackTrace();
                executorService.shutdown();
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
        OutputAndInputConnection outputAndInputConnection = new OutputAndInputConnection(inputConnection,outputConnection);
        list.add(outputAndInputConnection);
        executorService.submit(inputConnection);
    }

    private OutputConnection getOutputConnection() throws IOException {
        Socket output = serverSocketOutput.accept();
        System.out.println("Created OutputConnection");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(output.getOutputStream());
        OutputConnection outputConnection = new OutputConnection(output,objectOutputStream);
        return outputConnection;
    }

    private InputConnection getInputConnection() throws IOException {
        Socket input = serverSocketInput.accept();
        System.out.println("Created InputConnection");
        InputConnection inputConnection = new InputConnection(input);
        return inputConnection;
    }

}
