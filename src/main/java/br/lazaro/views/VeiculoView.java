package br.lazaro.views;

import br.lazaro.controllers.*;
import br.lazaro.models.*;
import java.util.List;
import java.util.Scanner;

public class VeiculoView {
    private VeiculoController veiculoController;
    private Scanner input;

    public VeiculoView(VeiculoController veiculoController) {
        this.veiculoController = veiculoController;
        this.input = new Scanner(System.in);
    }

    public static void showMenu(){
            System.out.println("------Menu------");
            System.out.println("1 - Comprar veículo");
            System.out.println("2 - Estoque de veículo");
            System.out.println("3 - Vender veículo");
            System.out.println("4 - Relatório de vendas");
            System.out.println("5 - Sair");
            System.out.println("\nEscolha uma opção:");
    }

    public void comprarVeiculo() {

        System.out.println("Informe a marca do veiculo: ");
        String marca = input.next() ;
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
        System.out.println("Comprado com sucesso!");
    }


    private void exibirVeiculos(List<Veiculo> veiculos, String titulo) {
        int count = 0;
        System.out.println("\n----- " + titulo + " -----");
        for (Veiculo veiculo : veiculos) {
           System.out.println("ID: " + count + " - " + veiculo.toString());
//            System.out.println(toString().concat(toString(count), "-", veiculo.toString()));
//            System.out.println(String.format("%s - %s",count , veiculo.toString()));

            count++;
        }
    }

//    public List<Veiculo> estoque() {
//        return veiculoController.estoque();
//    }

    public void listarEstoque() {
        List<Veiculo> estoque = veiculoController.estoque();
        if (estoque.isEmpty()) {
            System.out.println("Nenhuma veiculo em estoque disponivel");
            return;
        }
        exibirVeiculos(estoque, "Estoque de veículos");

    }

//    public List<Veiculo> vendidos() {
//        return veiculoController.vendidos();
//    }

    public void listarVendidos() {
        List<Veiculo> vendidos = veiculoController.vendidos();
        if (vendidos.isEmpty()) {
            System.out.println("Nenhuma veiculo vendido.");
            return;
        }

        exibirVeiculos(vendidos, "Veiculos vendidos");
    }

    public void venderVeiculo() {
        listarEstoque();
        System.out.println("Informe o código do veículo que deseja vender: ");

        int idVeiculo = input.nextInt();

        try {
            //Veiculo veiculo = veiculoController.getVeiculo(idVeiculo);
            if (veiculoController.venderVeiculo(idVeiculo)) {
                System.out.println("Veículo vendido com sucesso!");
            } else {
                System.out.println("Veículo não disponivel para venda");
            }

        } catch (ArrayIndexOutOfBoundsException ax) {
            System.out.println("Código do veículo não encontrado!");
        }
        catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }
}
