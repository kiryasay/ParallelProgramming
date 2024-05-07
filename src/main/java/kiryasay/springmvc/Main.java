package kiryasay.springmvc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        DBWorker.deleteClientTable();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество записей для заполнения базы данных:");
        int number = scanner.nextInt();

        System.out.println("Выберете один город значение которого в таблице будет изменено:");
        System.out.println(" Москва  - 1 \n" +
                " Санкт-Петербург - 2 \n" +
                " Новосибирск -3 \n" +
                " Екатеринбург - 4 \n" +
                " Нижний Новгород - 5 \n" +
                " Ижевск - 6 \n" +
                " Челябинск - 7 \n" +
                " Омск - 8 \n" +
                " Самара - 9 \n" +
                " Ростов-на-Дону - 10 ");
        int cityNumber = scanner.nextInt();
        scanner.close();
        String city = "";
        switch (cityNumber)
        {
            case 1: city = "Москва"; break;
            case 2: city = "Санкт-Петербург"; break;
            case 3: city = "Новосибирск"; break;
            case 4: city = "Екатеринбург"; break;
            case 5: city = "Нижний Новгород"; break;
            case 6: city = "Ижевск"; break;
            case 7: city = "Челябинск"; break;
            case 8: city = "Омск"; break;
            case 9: city = "Самара"; break;
            case 10: city = "Ростов-на-Дону"; break;
        }


        System.out.println("\t\t\t\tНепараллельная заполнение базы данных");
        System.out.println("----------------------------------------------------------------------");
        NotParallel notParallel = new NotParallel();
        notParallel.insertInto(number);
        System.out.println("----------------------------------------------------------------------");
        System.out.println("\t\t\t\tНепараллельное изминение базы данных");
        System.out.println("----------------------------------------------------------------------");
        notParallel.selectWhere(city,number);
        System.out.println("----------------------------------------------------------------------");
        System.out.println("\n");
        System.out.println("\t\t\t\tПараллельное рзаполнение базы данных");
        System.out.println("----------------------------------------------------------------------");
        Parallel parallel = new Parallel();
        parallel.insertInto(number);
        System.out.println("----------------------------------------------------------------------");
        System.out.println("\t\t\t\tПараллельное изминение базы данных");
        System.out.println("----------------------------------------------------------------------");
        parallel.selectWhere(city,number);
        System.out.println("----------------------------------------------------------------------");
    }

}