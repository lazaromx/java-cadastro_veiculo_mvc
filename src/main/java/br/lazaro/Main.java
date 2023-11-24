package br.lazaro;

import br.lazaro.repositories.*;
import br.lazaro.controllers.*;
import br.lazaro.views.*;

import java.util.InputMismatchException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static Scanner input = new Scanner(System.in);
    static VeiculoView view;

    public static void main(String[] args) {
        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        view = new VeiculoView(controller);

        int menu = 0;
        do{
            menu = showMenu();

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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. \nTente novamente ou digite 0 para SAIR.");
            }

        } while(menu != 0);
        input.close();
    }

    /**
    * Exibe um menu para o usuário e solicita que ele faça uma seleção.
    *
    * @return         	A seleção do menu do usuário como um número inteiro.
    */
    private static int showMenu() {
        try
        {
            System.out.println("\n........:: Menu ::........");

            view.showMenu();

            System.out.println("0 - Sair");
            System.out.println("..........................");
            System.out.print("Escolha uma opção: ");

            return Integer.parseInt(input.next());
        }
        catch (Exception ex)
        {
            return -1; //-1 irá opcao inválida e reapresentar o menu de opcpes
        }

    }
}