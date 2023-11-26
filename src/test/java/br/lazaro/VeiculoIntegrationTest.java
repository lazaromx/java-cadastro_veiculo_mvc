package br.lazaro;

import br.lazaro.controllers.VeiculoController;
import br.lazaro.repositories.VeiculoRepository;
import br.lazaro.models.Veiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class VeiculoIntegrationTest {
    private VeiculoController controller;
    private VeiculoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new VeiculoRepository();
        controller = new VeiculoController(repository);

    }

    @Test
    void compraEVendaVeiculos(){
        Veiculo veiculo = new Veiculo("Fiat", "Uno", "Branco", 2015, 15000.0);
        controller.comprarVeiculo(veiculo);
        Assertions.assertTrue(repository.obterVeiculosPorStatus("estoque").contains(veiculo));

        controller.venderVeiculo(0);
        Assertions.assertTrue(repository.obterVeiculosPorStatus("vendido").contains(veiculo));

        Assertions.assertEquals("vendido", veiculo.getStatus());
        Assertions.assertFalse(repository.obterVeiculosPorStatus("estoque").contains(veiculo));
    }
}
