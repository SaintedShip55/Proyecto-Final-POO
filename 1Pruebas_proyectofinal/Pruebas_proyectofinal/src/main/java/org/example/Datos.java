package org.example;

public class Datos{
    private final int SKU;
    private String nombre;
    private int precio;
    private String marca;
    private int stock;

    public Datos(int SKU, String nombre, int precio, String marca, int stock) throws IncorrectDataException{
        this.SKU = SKU;
        this.nombre = nombre;
        this.marca = marca;
        if (precio<0){
            throw new IncorrectDataException();
        }
        this.precio = precio;
        this.stock = stock;
    }

    public int getSKU() {
        return SKU;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio){
        if (precio<0){
            throw new IllegalArgumentException();
        }
        this.precio = precio;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}


class IncorrectDataException extends RuntimeException{
    public IncorrectDataException(){
        super("No se admiten numeros negativos o numeros decimales");
    }
}

