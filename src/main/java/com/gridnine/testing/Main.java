package com.gridnine.testing;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Tables tables = new Tables();
        boolean again = true;
        Scanner scanner = new Scanner(System.in);

        while (again) {
            System.out.print(Menu.homeMenu);
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.println(tables.getTableFlightsdDepartureOnDate());
                }
                case "2" -> {
                    System.out.println(tables.getTableFlightsdDepartureAfterDate());
                }
                case "3" -> {
                    System.out.println(tables.getTableArrivalOnDate());
                }
                case "4" -> {
                    System.out.println(tables.getTableFaster());
                }
                case "5" -> {
                    System.out.println(tables.getTableSooner());
                }
                case "6" -> {
                    System.out.println(tables.getTableMinimalLayover());
                }
                default -> {
                    System.out.println("Вы не выбрали пункт меню либо ввели не верную цифру, нажмите Enter и попробуйте еще раз");
                    scanner.nextLine();
                    continue;
                }
            }
            System.out.print("Хотите начать новый поиск?\n1.Да\n2.Нет\n> ");
            if (!scanner.nextLine().equals("1")) {
                again = false;
            }
        }
    }
}