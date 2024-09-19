package controller;

import java.util.concurrent.Semaphore;

public class FormulaUm extends Thread {
    private int equipe;
    private int carro;
    private static Semaphore pistaSemaforo = new Semaphore(5); // Máximo de 5 carros na pista
    private static Semaphore[] equipeSemaforos = new Semaphore[7]; // Semáforos para cada equipe
    private static int[] melhoresTempos = new int[14]; // Melhores tempos dos 14 carros
    private static final int numCarros = 14;
    
    static {
        for (int i = 0; i < 7; i++) {
            equipeSemaforos[i] = new Semaphore(1); // Cada equipe pode ter no máximo um carro na pista ao mesmo tempo
        }
        for (int i = 0; i < numCarros; i++) {
            melhoresTempos[i] = Integer.MAX_VALUE; // Inicializa os melhores tempos
        }
    }

    public FormulaUm(int equipe, int carro) {
        this.equipe = equipe;
        this.carro = carro;
    }

    @Override
    public void run() {
        pista();
    }

    private void pista() {
        try {
            equipeSemaforos[equipe - 1].acquire(); //aguardando liberação para a equipe
            pistaSemaforo.acquire(); //aguardando liberação para a pista
            System.out.println("O carro da equipe " + equipe + " entrou na corrida.");
            sleep(300);

            int melhorTempo = Integer.MAX_VALUE; // Inicializa com o maior valor possível
            for (int i = 1; i <= 3; i++) {
                int tempoVolta = deuVoltas();
                System.out.println("Carro " + carro + " da Equipe " + equipe + " fez a volta " + i + " em " + tempoVolta + " ms.");
                if (tempoVolta < melhorTempo) {
                    melhorTempo = tempoVolta;
                }
            }
            System.out.println("Carro " + carro + " da Equipe " + equipe + " finalizou com melhor volta de " + melhorTempo + " ms.");
            melhoresTempos[(equipe - 1) * 2 + (carro - 1)] = melhorTempo; // Armazena o melhor tempo do carro

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pistaSemaforo.release(); // Libera a pista
            equipeSemaforos[equipe - 1].release(); //Libera a equipe
        }
    }

    private int deuVoltas() {
        return (int) (Math.random() * (4001 - 1000)) + 1000; // tempo de volta aleatório entre 1000 e 4000 ms
    }
//Obs.: 
    public static void exibirGrid() {
        System.out.println("\nGrid de Largada:");
        for (int i = 0; i < numCarros; i++) {
            if (melhoresTempos[i] < Integer.MAX_VALUE) {
                int equipe = i / 2 + 1;
                int carro = i % 2 + 1;
                System.out.println("Carro " + carro + " da Equipe " + equipe + " com tempo de " + melhoresTempos[i] + " ms.");
            }
        }
    }
}


