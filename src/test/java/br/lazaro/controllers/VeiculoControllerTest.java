package br.lazaro.controllers;

import br.lazaro.exceptions.VeiculoNotFoundException;
import br.lazaro.models.Veiculo;
import br.lazaro.repositories.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoControllerTest {
    @InjectMocks
    private VeiculoController controller;
    @Mock
    private VeiculoRepository repository;
    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        repository = mock(VeiculoRepository.class);
        controller = new VeiculoController(repository);
    }

    @Test
    void comprarVeiculoDeveInserirNoRepositorio() {
        veiculo = new Veiculo("Ford", "Fiesta", "Vermelho", 2020, 20000.0);
        controller.comprarVeiculo(veiculo);
        verify(repository).inserirVeiculo(veiculo);
    }

    @Test
    void venderVeiculoDeveAtualizarStatus() {
        veiculo = new Veiculo("Ford", "Fiesta", "Vermelho", 2020, 20000.0);
        veiculo.setStatus("estoque");
        when(repository.buscarVeiculoPorId(1)).thenReturn(veiculo);

        controller.venderVeiculo(1);

        verify(repository).atualizarVeiculo(veiculo);
        assertEquals("vendido", veiculo.getStatus());
    }

    @Test
    void estoqueDeveRetornarVeiculosEmEstoque() {
        List<Veiculo> veiculosEmEstoqueMock = Arrays.asList(
                new Veiculo("Marca1", "Modelo1", "Cor1", 2020, 30000.0),
                new Veiculo("Marca2", "Modelo2", "Cor2", 2021, 40000.0)
        );
        when(repository.listarVeiculosPorStatus("estoque")).thenReturn(veiculosEmEstoqueMock);

        List<Veiculo> resultado = controller.estoque();

        assertEquals(2, resultado.size());
        verify(repository).listarVeiculosPorStatus("estoque");
    }

    @Test
    void vendidosDeveRetornarVeiculosVendidos() {
        List<Veiculo> veiculosVendidosMock = Collections.singletonList(
                new Veiculo("Marca3", "Modelo3", "Cor3", 2019, 25000.0)
        );
        when(repository.listarVeiculosPorStatus("vendido")).thenReturn(veiculosVendidosMock);

        List<Veiculo> resultado = controller.vendidos();

        assertEquals(1, resultado.size());
        verify(repository).listarVeiculosPorStatus("vendido");
    }

    @Test
    void getVeiculoDeveRetornarVeiculoCorreto() {
        Veiculo veiculoMock = new Veiculo("Marca4", "Modelo4", "Cor4", 2018, 20000.0);
        when(repository.buscarVeiculoPorId(1)).thenReturn(veiculoMock);

        Veiculo resultado = controller.getVeiculo(1);

        assertNotNull(resultado);
        assertEquals("Marca4", resultado.getMarca());
        verify(repository).buscarVeiculoPorId(1);
    }

    @Test
    public void test_nao_vender_veiculo_fora_de_estoque() {
        Veiculo veiculoMock = mock(Veiculo.class);
        when(veiculoMock.getStatus()).thenReturn("vendido");
        when(repository.buscarVeiculoPorId(0)).thenReturn(veiculoMock);

        assertThrows(VeiculoNotFoundException.class, () -> controller.venderVeiculo(0));
    }
}

