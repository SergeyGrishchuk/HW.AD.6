package HomeWork5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADRESS = "localhost";
    private static final int SERVER_PORT = 1408;

    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket(SERVER_ADRESS, SERVER_PORT);
            System.out.println("Соединение с сервером установлено");
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            Thread thread = new Thread (() -> {
                try {
                    while (true) {
                        output.writeUTF(scanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            thread.start();

            while (true) {
                String str = input.readUTF();
                if(str.equals("/end")) {
                    System.out.println("Соединение с сервером потеряно");
                    output.writeUTF("/end");
                    break;
                } else {
                    System.out.println("Сервер: " + str);
                }
            }
        } catch (IOException e) {
             e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


