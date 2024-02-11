import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        String input = scanner.nextLine();

        String[] data = input.split(" ");
        if (data.length != 6) {
            System.err.println("Ошибка: Неверное количество данных. Пожалуйста, введите данные в требуемом формате.");
            return;
        }

        try {
            String surname = data[0];
            String name = data[1];
            String patronymic = data[2];
            String birthDate = data[3];
            long phoneNumber = Long.parseLong(data[4]);
            char gender = data[5].charAt(0);

            if (gender != 'f' && gender != 'm') {
                throw new InvalidDataFormatException("Ошибка: Неверный формат пола. Пол должен быть 'f' или 'm'.");
            }

            // Проверка формата даты рождения
            if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                throw new InvalidDataFormatException("Ошибка: Неверный формат даты рождения. Формат должен быть dd.mm.yyyy.");
            }

            // Создание строки для записи в файл
            String record = String.format("%s %s %s %s %d %c%n", surname, name, patronymic, birthDate, phoneNumber, gender);

            // Запись в файл
            FileWriter fileWriter = new FileWriter(surname + ".txt", true); // Добавление в конец файла
            fileWriter.write(record);
            fileWriter.close();

            System.out.println("Данные успешно записаны в файл.");

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Неверный формат номера телефона. Номер должен быть целым числом.");
        } catch (InvalidDataFormatException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом:");
            e.printStackTrace();
        }
    }
}
