package br.lazaro.views;

import br.lazaro.controllers.*;
import br.lazaro.models.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VeiculoView {
    private VeiculoController veiculoController;
    private Scanner input;

    public VeiculoView(VeiculoController veiculoController) {
        this.veiculoController = veiculoController;
        this.input = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("1 - Comprar veículo");
        System.out.println("2 - Estoque de veículo");
        System.out.println("3 - Vender veículo");
        System.out.println("4 - Relatório de vendas");
    }

    public void comprarVeiculo() {
        try {
            System.out.println("Informe a marca do veiculo: ");
            String marca = input.next();

            System.out.println("Informe o modelo do veículo: ");
            String modelo = input.next();

            System.out.println("Informe a cor do veículo: ");
            String cor = input.next();

            System.out.println("Informe o ano de fabricação do veículo");
            int ano = input.nextInt();

            System.out.println("Informe o preço do veículo");
            double preco = input.nextDouble();

            Veiculo veiculo = new Veiculo(marca, modelo, cor, ano, preco);

            veiculoController.comprarVeiculo(veiculo);
            System.out.println("Veículo comprado com sucesso!");
        } catch (InputMismatchException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void exibirVeiculos(List<Veiculo> veiculos, String titulo) {
        System.out.println("\n----- " + titulo + " -----");
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo.toString());
        }
        System.out.println("\nTotal de veículos: " + veiculos.size());
    }

    public void listarEstoque() {
        List<Veiculo> estoque = veiculoController.estoque();
        if (estoque.isEmpty()) {
            System.out.println("Nenhum veículo em estoque disponível.");
            return;
        }
        exibirVeiculos(estoque, "Estoque de veículos");

    }

    public void listarVendidos() {
        List<Veiculo> vendidos = veiculoController.vendidos();
        if (vendidos.isEmpty()) {
            System.out.println("Nenhum veículo vendido.");
            return;
        }

        exibirVeiculos(vendidos, "Veiculos vendidos");
    }

    public void venderVeiculo() {
        try {
            listarEstoque();

            System.out.println("Informe o código do veículo que deseja vender: ");
            int idVeiculo = input.nextInt();

            veiculoController.venderVeiculo(idVeiculo);
            System.out.println("Veículo vendido com sucesso!");

        } catch (ArrayIndexOutOfBoundsException ax) {
            System.out.println("Código do veículo não encontrado!");
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }
}
