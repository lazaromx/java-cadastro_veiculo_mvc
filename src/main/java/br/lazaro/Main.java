package br.lazaro;

import br.lazaro.repositories.*;
import br.lazaro.controllers.*;
import br.lazaro.views.*;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);
        Scanner input = new Scanner(System.in);


        int menu;
        do{
            System.out.println("\n");
            VeiculoView.showMenu();
            menu = input.nextInt();

            switch (menu) {
                case 1:
                    view.comprarVeiculo();
                    break;
                case 2:
                    view.listarEstoque();
                    break;
                case 3:
                    view.venderVeiculo();
                    break;
                case 4:
                    view.listarVendidos();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }while(menu !=5);
        input.close();
    }

}