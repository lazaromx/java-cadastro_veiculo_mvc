package br.lazaro.views;

import br.lazaro.controllers.VeiculoController;
import br.lazaro.models.Veiculo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VeiculoViewTest {

    @InjectMocks
    private VeiculoView view;

    @Mock
    private VeiculoController controller;

    ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        controller = mock(VeiculoController.class);
        view = new VeiculoView(controller);
        outputStream = new ByteArrayOutputStream();

    }

    @Test
    public void showMenuTest() {
        System.setOut(new PrintStream(outputStream));

        view.showMenu();

        String expectedOutput = "1 - Comprar veículo\r\n2 - Estoque de veículo\r\n3 - Vender veículo\r\n4 - Relatório de vendas\r\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void mostrarListaVeiculosEstoqueComSucesso() {

        List<Veiculo> estoque = new ArrayList<>();
        Veiculo veiculo = new Veiculo("TestMarca", "TestModelo", "TestCor", 2020, 70000);
        estoque.add(veiculo);

        when(controller.estoque()).thenReturn(estoque);

        System.setOut(new PrintStream(outputStream));

        view.listarEstoque();

        String expectedOutput = "\n----- Estoque de veículos -----\r\nVeiculo 0 - Marca: TestMarca\t\tModelo: TestModelo\t\tcor: TestCor\t\tano: 2020\t\tpreço: 70000.0\t\tstatus: null\r\n\nTotal de veículos: 1\r\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void mostrarListaVendidosComSucesso() {
        List<Veiculo> vendidos = new ArrayList<>();
        Veiculo veiculo = new Veiculo("TestMarca", "TestModelo", "TestCor", 2020, 70000);
        vendidos.add(veiculo);

        when(controller.vendidos()).thenReturn(vendidos);

        System.setOut(new PrintStream(outputStream));

        view.listarVendidos();

        String expectedOutput = "\n----- Veiculos vendidos -----\r\nVeiculo 0 - Marca: TestMarca\t\tModelo: TestModelo\t\tcor: TestCor\t\tano: 2020\t\tpreço: 70000.0\t\tstatus: null\r\n\nTotal de veículos: 1\r\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

}