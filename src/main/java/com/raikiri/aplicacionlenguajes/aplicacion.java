/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raikiri.aplicacionlenguajes;

import java.util.*;
import java.io.*;

public class aplicacion {

    private List<Character> alfabeto;
    private List<String> cadenasAceptadas, cadenasRechazadas;
    private Set<String> estados;
    private String estadoInicial;
    private Set<String> estadosFinales;
    private Map<String, Map<Character, String>> transiciones; // Estado -> (Símbolo -> Estado de destino)

    // Constructor para inicializar las listas y estructuras del autómata
    public aplicacion() {
        alfabeto = new ArrayList<>();
        cadenasAceptadas = new ArrayList<>();
        cadenasRechazadas = new ArrayList<>();
        estados = new HashSet<>();
        estadosFinales = new HashSet<>();
        transiciones = new HashMap<>();
    }

    // Método para definir el alfabeto
    public void definirAlfabeto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los caracteres del alfabeto separados por espacios:");
        String entrada = scanner.nextLine();
        for (char c : entrada.toCharArray()) {
            if (c != ' ' && !alfabeto.contains(c)) { // Ignorar espacios y caracteres duplicados
                alfabeto.add(c);
            }
        }
        if (alfabeto.isEmpty()) {
            System.out.println("No se ingresó un alfabeto válido.");
        } else {
            System.out.println("Alfabeto registrado: " + alfabeto);
        }
    }

    // Método para registrar cadenas y verificar si pertenecen al alfabeto
    public void registrarCadena() {
        if (alfabeto.isEmpty()) {
            System.out.println("Primero debe definir un alfabeto.");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese una cadena para registrar:");
        String cadena = scanner.nextLine();

        if (cadenaPerteneceAlfabeto(cadena)) {
            cadenasAceptadas.add(cadena);
            System.out.println("La cadena ha sido aceptada.");
        } else {
            cadenasRechazadas.add(cadena);
            System.out.println("La cadena no pertenece al alfabeto.");
        }
    }

    // Método que verifica si una cadena pertenece al alfabeto
    private boolean cadenaPerteneceAlfabeto(String cadena) {
        for (char c : cadena.toCharArray()) {
            if (!alfabeto.contains(c)) { // Si un caracter no está en el alfabeto, la cadena no es válida
                return false;
            }
        }
        return true;
    }

    // Método para generar un archivo TXT con los resultados
    public void exportarTXT() {
        if (alfabeto.isEmpty()) {
            System.out.println("No hay alfabeto definido para exportar.");
            return;
        }

        try {
            FileWriter writer = new FileWriter("alfabeto_y_cadenas.txt");
            writer.write("Alfabeto: " + alfabeto + "\n\n");
            writer.write("Cadenas aceptadas:\n");
            for (String cadena : cadenasAceptadas) {
                writer.write(cadena + "\n");
            }
            writer.write("\nCadenas rechazadas:\n");
            for (String cadena : cadenasRechazadas) {
                writer.write(cadena + "\n");
            }
            writer.close();
            System.out.println("Datos exportados exitosamente a 'alfabeto_y_cadenas.txt'.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al exportar los datos.");
        }
    }

    // *** Funcionalidades del autómata ***

    // Opción para crear estados
    public void crearEstado() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del estado:");
        String estado = scanner.nextLine();
        estados.add(estado);
        transiciones.put(estado, new HashMap<>());
        System.out.println("Estado '" + estado + "' creado.");
    }

    // Opción para definir el estado inicial
    public void definirEstadoInicial() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el estado inicial:");
        String estado = scanner.nextLine();
        if (estados.contains(estado)) {
            estadoInicial = estado;
            System.out.println("Estado inicial definido: " + estadoInicial);
        } else {
            System.out.println("El estado no existe.");
        }
    }

    // Opción para definir los estados finales
    public void agregarEstadoFinal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el estado final:");
        String estado = scanner.nextLine();
        if (estados.contains(estado)) {
            estadosFinales.add(estado);
            System.out.println("Estado final agregado: " + estado);
        } else {
            System.out.println("El estado no existe.");
        }
    }

    // Opción para definir las transiciones
    public void definirTransicion() {
        if (estados.isEmpty()) {
            System.out.println("Primero debe crear estados.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el estado de origen:");
        String estadoOrigen = scanner.nextLine();
        System.out.println("Ingrese el símbolo de la transición:");
        char simbolo = scanner.nextLine().charAt(0);
        System.out.println("Ingrese el estado de destino:");
        String estadoDestino = scanner.nextLine();

        if (estados.contains(estadoOrigen) && estados.contains(estadoDestino) && alfabeto.contains(simbolo)) {
            transiciones.get(estadoOrigen).put(simbolo, estadoDestino);
            System.out.println("Transición agregada: " + estadoOrigen + " -- " + simbolo + " --> " + estadoDestino);
        } else {
            System.out.println("Alguno de los estados o el símbolo no es válido.");
        }
    }

    // Opción para visualizar la tabla de transiciones
    public void mostrarTablaTransiciones() {
        System.out.println("Tabla de Transiciones:");
        for (String estado : transiciones.keySet()) {
            for (Character simbolo : transiciones.get(estado).keySet()) {
                String estadoDestino = transiciones.get(estado).get(simbolo);
                System.out.println(estado + " -- " + simbolo + " --> " + estadoDestino);
            }
        }
    }

    // Método principal para la ejecución del programa
    public static void main(String[] args) {
        aplicacion app = new aplicacion();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Bucle para mostrar el menú hasta que el usuario elija salir
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Definir alfabeto");
            System.out.println("2. Registrar cadena");
            System.out.println("3. Exportar a TXT");
            System.out.println("4. Crear estado");
            System.out.println("5. Definir estado inicial");
            System.out.println("6. Agregar estado final");
            System.out.println("7. Definir transicion");
            System.out.println("8. Mostrar tabla de transiciones");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                // Manejo del menú
                switch (opcion) {
                    case 1:
                        app.definirAlfabeto();
                        break;
                    case 2:
                        app.registrarCadena();
                        break;
                    case 3:
                        app.exportarTXT();
                        break;
                    case 4:
                        app.crearEstado();
                        break;
                    case 5:
                        app.definirEstadoInicial();
                        break;
                    case 6:
                        app.agregarEstadoFinal();
                        break;
                    case 7:
                        app.definirTransicion();
                        break;
                    case 8:
                        app.mostrarTablaTransiciones();
                        break;
                    case 9:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opcion invalida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido.");
                opcion = 0; // Para evitar que se salga del bucle en caso de error
            }
        } while (opcion != 9); // Continuar hasta que el usuario elija salir
    }
}
