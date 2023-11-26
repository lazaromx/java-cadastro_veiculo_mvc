package br.lazaro;

import br.lazaro.models.Veiculo;
import br.lazaro.repositories.*;
import br.lazaro.controllers.*;
import br.lazaro.views.VeiculoView;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class VeiculoViewTest {

    @Test
    public void test_instantiation() {
        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        assertNotNull(view);
    }

    @Test
    public void test_display_menu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        view.showMenu();

        String expectedOutput = "1 - Comprar veículo\r\n2 - Estoque de veículo\r\n3 - Vender veículo\r\n4 - Relatório de vendas\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_buy_vehicle() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Teste\nTeste\nTeste\n2022\n10000.0\n".getBytes());
        System.setIn(inContent);

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        view.comprarVeiculo();

        List<Veiculo> estoque = repository.obterVeiculosPorStatus("estoque");
        assertEquals(1, estoque.size());
    }

    @Test
    public void test_list_inventory() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        controller.venderVeiculo(0);
        view.listarEstoque();

        String expectedOutput = "Nenhum veículo em estoque disponível.\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_sell_vehicle() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inContent);

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        view.venderVeiculo();

        List<Veiculo> vendidos = repository.obterVeiculosPorStatus("vendido");
        assertEquals(1, vendidos.size());
    }

    @Test
    public void test_list_sold_vehicles() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        view.listarVendidos();

        String expectedOutput = "Nenhum veículo vendido.\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_list_inventory_empty() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        controller.venderVeiculo(0);
        view.listarEstoque();

        String expectedOutput = "Nenhum veículo em estoque disponível.\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_list_sold_vehicles_empty() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        VeiculoRepository repository = new VeiculoRepository();
        VeiculoController controller = new VeiculoController(repository);
        VeiculoView view = new VeiculoView(controller);

        view.listarVendidos();

        String expectedOutput = "Nenhum veículo vendido.\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

}