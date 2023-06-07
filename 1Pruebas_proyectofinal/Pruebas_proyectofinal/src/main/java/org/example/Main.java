package org.example;

import static org.example.Methods.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IncorrectDataException {
        Scanner sc = new Scanner(System.in);
        int in;
        String username;
        String password;
        int attempts = 0;
        boolean isAdmin = false;
        Admin admin = null;
        Admin[] admins = {
                new Admin("Khorae", "Admin1"),
                new Admin("Carlitos", "Admin2"),
                new Admin("Miguelito", "Admin3"),
                new Admin("Saulini", "Admin4")
        };
        System.out.println("Ingrese un nombre de Usuario: ");
        username = sc.nextLine();
        for (Admin admin1 : admins) {
            if (admin1.getUsername().equals(username)) {
                isAdmin = true;
                admin = admin1;
                break;
            }
        }
        if (isAdmin) {
            while (attempts<3){
            System.out.println("Inicio de sesion de administrador: " + admin);
            System.out.println("Ingrese su contraseña: ");
            password = sc.nextLine();
            if (admin.authenticate(password)) {
                System.out.println("Inicio de sesion de administrador exitoso!");
                    AdminMenu();
                    break;
            } else {
                attempts++;
                System.out.println("Inicio de sesion fallido.");
                int remainingAttempts = 3 - attempts;
                System.out.println("Quedan: "+remainingAttempts+" intentos antes de que se cierre el programa");
                if (remainingAttempts==0){
                    System.out.println("Inicios de sesion excedidos. Cerrando programa");
                    return;
                }
            }
            }
        } else {
            Customer customer = new Customer(username);
            System.out.println("Inicio de sesion de cliente");
            CustomerMenu();
        }
    }
}