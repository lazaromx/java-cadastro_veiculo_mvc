package br.lazaro.repositories;

import br.lazaro.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class VeiculoRepository {
    private final List<Veiculo> veiculos;

    public VeiculoRepository() {
        this.veiculos = new ArrayList<>();

        // adicionando apenas para até conectar a uma base de dados real
        this.adicionarVeiculo(new Veiculo("Renault", "Duster", "Preto", 2010, 200));
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculo.setStatus("estoque");
        veiculos.add(veiculo);
    }

    public void atualizarVeiculo(Veiculo veiculo) {
        veiculo.setStatus("vendido");
    }

    public Veiculo obterVeiculoPorId(int idVeiculo) {
        try {
            return obterVeiculosPorStatus("estoque").get(idVeiculo);
        } catch (IndexOutOfBoundsException ix) {
            throw new NoSuchElementException("Código de Veículo não existe!", ix);
        }
    }

    public List<Veiculo> obterVeiculosPorStatus(String status) {
        return filtrarVeiculosPorStatus(status);
    }

    private List<Veiculo> filtrarVeiculosPorStatus(String status) {
        List<Veiculo> veiculosPorStatus = new ArrayList<>();
        for (Veiculo veiculo : this.veiculos) {
            if (veiculo.getStatus().equals(status)) {
                veiculosPorStatus.add(veiculo);
            }
        }
        return veiculosPorStatus;
    }


}
