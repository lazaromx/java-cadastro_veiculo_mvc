package br.lazaro.controllers;

import br.lazaro.exceptions.VeiculoNotFoundException;
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
        //TODO: Melhorar mensagem de erro
        if (veiculo.getPreco() <= 0) {
            throw new IllegalArgumentException("O valor do veículo deve ser maior que 0.");
        }
        repository.adicionarVeiculo(veiculo);
    }

    public void venderVeiculo(int idVeiculo) {
        Veiculo veiculo = getVeiculo(idVeiculo);
        if (!veiculo.getStatus().equals("estoque")) {
            throw new VeiculoNotFoundException("Veículo está fora de estoque e não pode ser vendido.");
        }
        repository.atualizarVeiculo(veiculo);
    }


    public List<Veiculo> estoque() {
        return repository.obterVeiculosPorStatus("estoque");
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
        return repository.obterVeiculosPorStatus("vendido");
    }

    public Veiculo getVeiculo(int idVeiculo) {
        return repository.obterVeiculoPorId(idVeiculo);
    }

}
