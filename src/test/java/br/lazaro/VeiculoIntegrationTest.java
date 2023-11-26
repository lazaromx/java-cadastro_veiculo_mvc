package br.lazaro;

import br.lazaro.controllers.VeiculoController;
import br.lazaro.repositories.VeiculoRepository;
import br.lazaro.models.Veiculo;
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
        assertTrue(repository.obterVeiculosPorStatus("estoque").contains(veiculo));
        controller.venderVeiculo(0);
        assertTrue(repository.obterVeiculosPorStatus("vendido").contains(veiculo));
        assertEquals("vendido", veiculo.getStatus());
        assertFalse(repository.obterVeiculosPorStatus("estoque").contains(veiculo));
    }
}
