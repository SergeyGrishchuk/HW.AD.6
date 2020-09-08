package HomeWork5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Socket clientSocket = null;
        Scanner scanner = new Scanner(System.in);
        try(ServerSocket serverSocket = new ServerSocket(1408)){
            System.out.println("Сервер запущен");
            clientSocket = serverSocket.accept();
            System.out.println("Подключен клиент");
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            Thread thread = new Thread(() -> {
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
                    System.out.println("Клиент вышел с сервера");
                    output.writeUTF("/end");
                    break;
                } else {
                    System.out.println("Клиент: " + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
