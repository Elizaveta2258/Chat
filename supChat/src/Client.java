import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            String serverMessage = input.readLine();
            System.out.println(serverMessage); // Сообщение от сервера

            while (true) {
                System.out.print("Введите ваше число (от 1 до 100): ");
                String userInput = scanner.nextLine();
                output.println(userInput); // Отправка числа на сервер

                String response = input.readLine(); // Получение ответа от сервера
                System.out.println(response);

                // Если угадали, выходим из цикла
                if (response.startsWith("Поздравляем")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при подключении к серверу: " + e.getMessage());
        }
    }
}


