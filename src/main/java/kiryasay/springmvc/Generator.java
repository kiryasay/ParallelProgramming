package kiryasay.springmvc;

import java.util.Random;

public class Generator {
    private static final String[] FIRST_NAMES = {"Александр", "Иван", "Мария", "Елена", "Анна", "Дмитрий", "Светлана", "Евгений", "Ольга", "Наталья"};
    private static final String[] LAST_NAMES = {"Иванов", "Петров", "Сидоров", "Кузнецов", "Смирнова", "Попова", "Васильев", "Семенов", "Николаева", "Козлов"};
    private static final String[] CITIES = {"Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Нижний Новгород", "Ижевск", "Челябинск", "Омск", "Самара", "Ростов-на-Дону"};
    private static final String[] STREETS = {"Ленина", "Пушкина", "Гагарина", "Кирова", "Советская", "Мира", "Красная", "Комсомольская", "Воровского", "Первомайская"};
    private static final String[] POSTAL_CODES = {"123456", "654321", "987654", "456789", "321654", "789123", "564738", "987123", "741852", "852963"};

    public static String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder();

        // Генерация первой части номера (XXX)
        for (int i = 0; i < 3; i++) {
            phoneNumber.append(random.nextInt(10)); // Генерация случайной цифры от 0 до 9
        }
        phoneNumber.append("-");

        // Генерация второй части номера (XXX)
        for (int i = 0; i < 3; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        phoneNumber.append("-");

        // Генерация третьей части номера (XXXX)
        for (int i = 0; i < 4; i++) {
            phoneNumber.append(random.nextInt(10));
        }

        return phoneNumber.toString();
    }
    public static String generateName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
    public static String generateAddress() {
        Random random = new Random();
        String city = CITIES[random.nextInt(CITIES.length)];
        String street = STREETS[random.nextInt(STREETS.length)];
        String postalCode = POSTAL_CODES[random.nextInt(POSTAL_CODES.length)];
        int houseNumber = random.nextInt(100) + 1; // Генерация номера дома от 1 до 100
        return "г. " + city + ", ул. " + street + ", " + houseNumber + ", " + postalCode;
    }
    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        // Генерация номера счета из 16 цифр
        for (int i = 0; i < 16; i++) {
            accountNumber.append(random.nextInt(10)); // Генерация случайной цифры от 0 до 9
        }

        return accountNumber.toString();
    }

}
