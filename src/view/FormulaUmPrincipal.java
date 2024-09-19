package view;

import controller.FormulaUm;

public class FormulaUmPrincipal {
    public static void main(String[] args) {
        for (int i = 1; i <= 7; i++) {
            FormulaUm carro1 = new FormulaUm(i, 1);
            FormulaUm carro2 = new FormulaUm(i, 2);

            carro1.start();
            carro2.start();
        }
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FormulaUm.exibirGrid();
    }
}

