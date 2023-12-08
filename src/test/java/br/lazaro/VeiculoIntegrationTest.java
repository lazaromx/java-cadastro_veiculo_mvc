package br.lazaro;

import br.lazaro.controllers.VeiculoController;
import br.lazaro.models.Veiculo;
import br.lazaro.repositories.*;
import br.lazaro.views.VeiculoView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VeiculoIntegrationTest {

    // Usando um banco de dados de teste em memória (H2 Database) para integração
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    private VeiculoRepository repository;
    private VeiculoController controller;
    private Veiculo veiculo;
    private VeiculoView view;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() throws SQLException {
        // Inicializar o banco de dados de teste
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE tb_veiculos (id INT AUTO_INCREMENT, marca VARCHAR(50), modelo VARCHAR(50), cor VARCHAR(20), ano INT, preco DOUBLE, status VARCHAR(30), PRIMARY KEY (id))");
        }

        // Inicializar o VeiculoRepository usando o banco de dados de teste
        repository = new VeiculoRepository(URL, USUARIO, SENHA);
        controller = new VeiculoController(repository);
        view = new VeiculoView(controller);

        veiculo = new Veiculo("TestMarca", "TestModelo", "TestCor", 2022, 50000.0);

        // Configurar a entrada do usuário para simular interações
        String userInput = "TestInputMarca\nTestInputModelo\nTestInputCor\n2020\n70000\n1\n";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

    }

    @AfterEach
    void tearDown() throws SQLException {
        // Limpar o banco de dados após cada teste
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE tb_veiculos");
        }
    }

    @Test
    void comprarEListarEstoqueVeiculosTest() {

        controller.comprarVeiculo(veiculo);

        List<Veiculo> estoque = controller.estoque();

        assertEquals(1, estoque.size());

        Veiculo veiculoRetornado = estoque.get(0);
        assertEquals("TestMarca", veiculoRetornado.getMarca());
        assertEquals("TestModelo", veiculoRetornado.getModelo());
        assertEquals("TestCor", veiculoRetornado.getCor());
        assertEquals(2022, veiculoRetornado.getAnoFabricacao());
        assertEquals(50000.0, veiculoRetornado.getPreco(), 0.01);
        assertEquals("estoque", veiculoRetornado.getStatus());
    }

    @Test
    void compraEVendaVeiculos() {
        List<Veiculo> estoque = controller.estoque();
        assertEquals(0, estoque.size());

        List<Veiculo> vendidos = controller.vendidos();
        assertEquals(0, vendidos.size());

        controller.comprarVeiculo(veiculo);
        assertEquals("estoque", veiculo.getStatus());

        assertEquals(1, controller.estoque().size());

        controller.venderVeiculo(1);
        veiculo = repository.buscarVeiculoPorId(1);
        assertEquals("vendido", veiculo.getStatus());
        assertEquals(1, controller.vendidos().size());
        assertEquals(0, controller.estoque().size());
    }

    @Test
    void compraEVendaVeiculoView() {
        assertEquals(0, controller.estoque().size());
        view.comprarVeiculo();

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        view.listarEstoque();

        String expectedOutput = "\n----- Estoque de veículos -----\r\n" +
                "Veiculo 1 - Marca: TestInputMarca\t\tModelo: TestInputModelo\t\tcor: TestInputCor\t\tano: 2020\t\tpreço: 70000.0\t\tstatus: estoque\r\n" +
                "\nTotal de veículos: 1\r\n";

        assertEquals(expectedOutput, outputStream.toString());

    }
}

