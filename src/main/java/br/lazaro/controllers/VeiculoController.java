package br.lazaro.controllers;

import br.lazaro.repositories.*;
import br.lazaro.models.*;
import java.util.List;

public class VeiculoController {
    private VeiculoRepository repository = new VeiculoRepository();

    public VeiculoController(VeiculoRepository repository){
        this.repository = repository;
    }
//    public static void comprarVeiculo(String marca, String modelo, String cor, int anoFabricacao, double preco){
//        Veiculo veiculo = new Veiculo(marca, modelo, cor, anoFabricacao, preco);
//        repository.estoque.add(veiculo);
//    }
    public void comprarVeiculo(Veiculo veiculo){
        repository.comprarVeiculo(veiculo);
    }

    public void venderVeiculo(int idVeiculo) {
        Veiculo veiculo = getVeiculo(idVeiculo);
        if (!veiculo.getStatus().equals("estoque")) {
            throw new IllegalStateException("Veículo está fora de estoque e não pode ser vendido.");
        }
        repository.venderVeiculo(veiculo);
    }


    public List<Veiculo> estoque() {
        return repository.estoque();
    }


//    public static void venderVeiculo(int codigo) {
//        if (codigo < 0 || codigo > repository.estoque.size())
//            throw new IndexOutOfBoundsException();
//
//        Veiculo veiculo = repository.estoque.get(codigo);
//        vendidos.add(veiculo);
//        repository.estoque.remove(codigo);
//    }

    public List<Veiculo> vendidos() {
        return repository.vendidos();
    }

    public Veiculo getVeiculo(int idVeiculo) {
        return repository.getVeiculo(idVeiculo);
    }

}
