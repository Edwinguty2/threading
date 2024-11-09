package com.edwin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    public static void main(String[] args) {
        // Definir una lista de números para calcular sus cuadrados en paralelo
        List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);

        // Crear tareas (Callables) que calculan el cuadrado de cada número
        List<Callable<Integer>> tasks = numbers.stream()
                .map(num -> (Callable<Integer>) () -> {
                    int square = num * num;
                    System.out.println("Calculating square of " + num + ": " + square);
                    return square;
                })
                .toList();

        // Crear un ExecutorService con un pool de 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Ejecutar todas las tareas y almacenar los resultados futuros
            List<Future<Integer>> results = executor.invokeAll(tasks);
            
            // Calcular la suma total de los cuadrados
            int totalSquareSum = 0;
            for (Future<Integer> result : results) {
                totalSquareSum += result.get();
            }
            
            System.out.println("Total sum of squares: " + totalSquareSum);
            
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Cerrar el ExecutorService
        }
    }
}


