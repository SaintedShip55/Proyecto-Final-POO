package org.example;

import java.io.*;
import java.util.*;

public class Methods {
    public static void writeObjectsToFile(String path)  {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            Scanner sca = new Scanner(System.in);
            int precio;
            int stock;
            String input;
            boolean continuar = true;
            while (continuar) {
                System.out.print("Ingrese el SKU a registrar, o '0' para dejar de registrar: ");
                input = sca.nextLine();
                if (input.equalsIgnoreCase("0")) {
                    continuar = false;
                    continue;
                }
                int id;
                try {
                    id = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("SKU invalido. Porfavor intente de nuevo.");
                    continue;
                }
                System.out.print("Ingrese el nombre del producto: ");
                String nombre = sca.nextLine();
                System.out.print("Ingrese el modelo del producto: ");
                String modelo = sca.nextLine();
                do {
                    try {
                        System.out.print("Ingrese el precio del producto: ");
                        precio = sca.nextInt();
                        sca.nextLine();
                        if (precio>=0){
                            break;
                        }else {
                            System.out.println("El numero es un valor negativo, vuelva a capturar");
                        }
                    }catch (InputMismatchException e) {
                        System.out.println("El numero no es un valor entero, vuelva a capturar");
                        sca.next();
                    }
                }while (true);
                do {
                    try {
                        System.out.print("Ingrese el stock del producto: ");
                        stock = sca.nextInt();
                        sca.nextLine();
                        if (stock>=0){
                            break;
                        }else {
                            System.out.println("El numero es un valor negativo, vuelva a capturar");
                        }
                    }catch (InputMismatchException e) {
                        System.out.println("El numero no es un valor entero, vuelva a capturar");
                        sca.next();
                    }
                }while (true);
                System.out.println("Producto registrado con exito.");
                writer.println(id + ":" + nombre + ":" + precio + ":" + modelo + ":" + stock);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void readDataFromFile(String path) {
        List<Datos> Datos = readObjectsFromFile(path);
        Scanner sca = new Scanner(System.in);
        System.out.print("Ingrese el SKU que desea consultar: ");
        int targetId = sca.nextInt();
        Datos foundObject = null;
        for (Datos obj : Datos) {
            if (obj.getSKU() == targetId) {
                foundObject = obj;
                break;
            }
        }
        if (foundObject != null) {
            System.out.println("Se encontro el producto con el SKU: " + targetId);
            System.out.println("Nombre: " + foundObject.getNombre());
            System.out.println("Marca: " + foundObject.getMarca());
            System.out.println("Precio: " + foundObject.getPrecio());
            System.out.println("Stock: " + foundObject.getStock());
        } else {
            System.out.println("No se encontro el producto con el SKU: " + targetId);
        }
    }
    public static List<Datos> readObjectsFromFile(String path) {
        List<Datos> Datos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String nombre = parts[1];
                    int precio = Integer.parseInt(parts[2]);
                    String modelo = parts[3];
                    int stock = Integer.parseInt(parts[4]);
                    Datos.add(new Datos(id, nombre, precio, modelo,stock));
                }
            }
        } catch (IOException | IncorrectDataException e) {
            System.out.println(e.getMessage());
        }
        return Datos;
    }
    public static void removeProductFromFile(String path) {
        List<Datos> Datoss = readObjectsFromFile(path);
        Scanner sca = new Scanner(System.in);
        System.out.print("Ingrese el SKU que desea eliminar: ");
        int targetId = sca.nextInt();
        Iterator<Datos> iterator = Datoss.iterator();
        while (iterator.hasNext()) {
            Datos Datos = iterator.next();
            if (Datos.getSKU() == targetId) {
                iterator.remove();
                break;
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Datos Datos : Datoss) {
                System.out.println("El producto con el SKU: " + targetId + " ha sido eliminado correctamente");
                writer.println(Datos.getSKU() + ":" + Datos.getNombre() + ":" + Datos.getPrecio() + ":" + Datos.getMarca() + ":" + Datos.getStock());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void editProductFromFile(String path) throws IncorrectDataException {
        List<Datos> Datoss = readObjectsFromFile(path);
        Scanner sca = new Scanner(System.in);
        int opc;
        int precio;
        int stock;
        int targetId = 0;
        System.out.print("Ingrese el SKU que desea editar: \n");
        targetId = sca.nextInt();
        Datos foundObject = null;
        for (Datos obj : Datoss) {
            if (obj.getSKU() == targetId) {
                foundObject = obj;
                break;
            }
        }
        if (foundObject!=null){
            System.out.println("Desea editar, agregar o dar de baja stock?");
            System.out.println("1. Editar\n2.- Agregar stock\n3.- Dar de baja stock");
            opc = sca.nextInt();
            if (opc==1) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Ingrese los nuevos atributos del producto:");
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Marca: ");
                String marca = scanner.nextLine();
                do {
                    try {
                        System.out.print("Ingrese el precio del producto: ");
                        precio = sca.nextInt();
                        sca.nextLine();
                        if (precio >= 0) {
                            break;
                        } else {
                            System.out.println("El numero es un valor negativo, vuelva a capturar");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("El numero no es un valor entero, vuelva a capturar");
                        sca.next();
                    }
                } while (true);
                for (Datos Datos : Datoss) {
                    if (Datos.getSKU() == targetId) {
                        Datos.setNombre(nombre);
                        Datos.setMarca(marca);
                        Datos.setPrecio(precio);
                        break;
                    }
                }
                try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
                    for (Datos Datos : Datoss) {
                        writer.println(Datos.getSKU() + ":" + Datos.getNombre() + ":" + Datos.getPrecio() + ":" + Datos.getMarca());
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }else if(opc==2){
                do {
                    try {
                        System.out.println("Ingrese cuanto stock desea añadir: ");
                        stock = sca.nextInt();
                        if (stock>=0){
                            System.out.println("Se añadio " + stock + " de stock del producto");
                            break;
                        }else {
                            System.out.println("El numero ingresado es un valor negativo");
                        }
                    }catch (InputMismatchException e){
                        System.out.println("El numero no es un valor entero, vuelva a capturar");
                        sca.next();
                    }
                }while (true);
                for (Datos Datos : Datoss) {
                    if (Datos.getSKU() == targetId) {
                        Datos.setStock(Datos.getStock()+stock);
                        break;
                    }
                }
                try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
                    for (Datos Datos : Datoss) {
                        System.out.println("Stock: " + Datos.getStock());
                        writer.println(Datos.getSKU() + ":" + Datos.getNombre() + ":" + Datos.getPrecio() + ":" + Datos.getMarca() + ":" +Datos.getStock());
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }else if (opc==3) {
                do {
                    try {
                        System.out.println("Ingrese cuanto stock desea dar de baja: ");
                        stock = sca.nextInt();
                        if (stock >= 0) {
                            System.out.println("Se dio de baja " + stock + " de stock del producto");
                            break;
                        } else {
                            System.out.println("El numero ingresado es un valor negativo");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("El numero no es un valor entero, vuelva a capturar");
                        sca.next();
                    }
                } while (true);
                for (Datos Datos : Datoss) {
                    if (Datos.getSKU() == targetId) {
                        Datos.setStock(Datos.getStock() - stock);
                        break;
                    }
                }
                try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
                    for (Datos Datos : Datoss) {
                        System.out.println("Stock: " + Datos.getStock());
                        writer.println(Datos.getSKU() + ":" + Datos.getNombre() + ":" + Datos.getPrecio() + ":" + Datos.getMarca() + ":" + Datos.getStock());
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }else {
            System.out.println("No se encontro el producto con el SKU: " + targetId);
        }
    }
    public static void AdminMenu(){
        Scanner sc = new Scanner(System.in);
        int in;
        do {
            System.out.println("\nQue desea realizar?\n");
            System.out.println("0.- Si desea detener el programa\n1.- Captura de inventario\n2.- Consulta de inventario\n3.- Eliminar un producto\n4.- Editar un producto");
            System.out.println("\nIngrese su opcion: ");
            in = sc.nextInt();
            if (in == 1) {
                writeObjectsToFile("C:\\Users\\cavil\\OneDrive\\Escritorio\\Escuela\\Programación\\Trabajos 2° Semestre\\Unidad 4\\Datos.txt");
            } else if (in == 2) {
                readDataFromFile("C:\\Users\\cavil\\OneDrive\\Escritorio\\Escuela\\Programación\\Trabajos 2° Semestre\\Unidad 4\\Datos.txt");
            } else if (in == 3) {
                removeProductFromFile("C:\\Users\\cavil\\OneDrive\\Escritorio\\Escuela\\Programación\\Trabajos 2° Semestre\\Unidad 4\\Datos.txt");
            } else if (in == 4) {
                editProductFromFile("C:\\Users\\cavil\\OneDrive\\Escritorio\\Escuela\\Programación\\Trabajos 2° Semestre\\Unidad 4\\Datos.txt");
            }
        } while (in!=0);
    }
    public static void CustomerMenu(){
        int in;
        Scanner sc = new Scanner(System.in);
        do {
            readDataFromFile("C:\\Users\\cavil\\OneDrive\\Escritorio\\Escuela\\Programación\\Trabajos 2° Semestre\\Unidad 4\\Datos.txt");
            System.out.println("Desea seguir la consulta?");
            System.out.println("1.- Seguir\n0.- Salir");
            in = sc.nextInt();
        }while (in!=0);
    }
}
