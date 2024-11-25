import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

 class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Клиент подключен.");

                    // Сервер загадывает случайное число от 1 до 100
                    int secretNumber = new Random().nextInt(100) + 1;
                    output.println("Сервер загадал число от 1 до 100. Введите ваше число:");

                    String clientInput;
                    while ((clientInput = input.readLine()) != null) {
                        try {
                            int guessedNumber = Integer.parseInt(clientInput);
                            if (guessedNumber < 1 || guessedNumber > 100) {
                                output.println("Ошибка: введите число от 1 до 100.");
                            } else if (guessedNumber < secretNumber) {
                                output.println("Ваше число меньше загаданного. Попробуйте снова.");
                            } else if (guessedNumber > secretNumber) {
                                output.println("Ваше число больше загаданного. Попробуйте снова.");
                            } else {
                                output.println("Поздравляем! Вы угадали число: " + secretNumber);
                                break; // Завершить игру
                            }
                        } catch (NumberFormatException e) {
                            output.println("Ошибка: введено не число.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }
}

