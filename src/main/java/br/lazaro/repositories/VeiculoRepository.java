package br.lazaro.repositories;

import br.lazaro.models.*;

import java.util.ArrayList;
import java.util.List;

public class VeiculoRepository {
    private List<Veiculo> veiculos;

    public VeiculoRepository() {
        this.veiculos = new ArrayList<>();
        this.veiculos.add(new Veiculo("Renault", "Duster", "Preto", 2010, 200));
    }

    public void comprarVeiculo(Veiculo veiculo) {
        veiculo.setStatus("estoque");
        veiculos.add(veiculo);
    }

    public List<Veiculo> estoque(){
        return filtrarVeiculosPorStatus("estoque");
    }

    public List<Veiculo> vendidos() {
        return filtrarVeiculosPorStatus("vendido");
    }

    public boolean venderVeiculo(Veiculo veiculo) {
        veiculo.setStatus("vendido");
        return true;
    }
    private List<Veiculo> filtrarVeiculosPorStatus(String status) {
        List<Veiculo> veiculosPorStatus = new ArrayList<>();
        for (Veiculo veiculo : this.veiculos) {
            if (veiculo.getStatus().equals(status)){
                veiculosPorStatus.add(veiculo);
            }
        }
        return veiculosPorStatus;
    }

    public Veiculo getVeiculo(int idVeiculo)  {
        try {
            return estoque().get(idVeiculo);
        }
        catch (IndexOutOfBoundsException ix) {
            throw new ArrayIndexOutOfBoundsException("Código de Veículo não existe!");
        }

    }
}
