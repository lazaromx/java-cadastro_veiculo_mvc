package br.lazaro.repositories;

import br.lazaro.models.Veiculo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.List;

//@ExtendWith(MockitoExtension.class)
public class VeiculoRepositoryTest {
    // Usando um banco de dados de teste em memória (H2 Database) para integração
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";
    private VeiculoRepository repository;


    @BeforeEach
    void setUp() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE tb_veiculos (id INT AUTO_INCREMENT, marca VARCHAR(50), modelo VARCHAR(50), cor VARCHAR(20), ano INT, preco DOUBLE, status VARCHAR(30), PRIMARY KEY (id))");

        }

        repository = new VeiculoRepository(URL, USUARIO, SENHA);
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
    public void test_inserir_e_atualizar_veiculo() {
        Veiculo veiculo = new Veiculo("TestMarca", "TestModelo", "TestCor", 2022, 50000.0);
        veiculo.setStatus("novoStatus");
        int idVeiculo = repository.inserirVeiculo(veiculo);

        veiculo.setId(idVeiculo);
        veiculo.setMarca("UpdatedMarca");
        veiculo.setModelo("UpdatedModelo");
        veiculo.setCor("UpdatedCor");
        veiculo.setAnoFabricacao(2023);
        veiculo.setPreco(60000.0);
        veiculo.setStatus("UpdatedStatus");

        repository.atualizarVeiculo(veiculo);

        Veiculo veiculoAtualizado = repository.buscarVeiculoPorId(veiculo.getId());

        assertNotNull(veiculoAtualizado);
        assertEquals(veiculo.getMarca(), veiculoAtualizado.getMarca());
        assertEquals(veiculo.getModelo(), veiculoAtualizado.getModelo());
        assertEquals(veiculo.getCor(), veiculoAtualizado.getCor());
        assertEquals(veiculo.getAnoFabricacao(), veiculoAtualizado.getAnoFabricacao());
        assertEquals(veiculo.getPreco(), veiculoAtualizado.getPreco(), 0.001);
        assertEquals(veiculo.getStatus(), veiculoAtualizado.getStatus());
    }

    @Test
    public void test_inserir_e_buscar_veiculo_por_id() {
        Veiculo veiculo = new Veiculo("TestMarca", "TestModelo", "TestCor", 2022, 50000.0);
        veiculo.setStatus("novoStatus");
        int idVeiculo = repository.inserirVeiculo(veiculo);

        Veiculo veiculoInserido = repository.buscarVeiculoPorId(idVeiculo);

        assertNotNull(veiculoInserido);
        assertEquals(veiculo.getMarca(), veiculoInserido.getMarca());
        assertEquals(veiculo.getModelo(), veiculoInserido.getModelo());
        assertEquals(veiculo.getCor(), veiculoInserido.getCor());
        assertEquals(veiculo.getAnoFabricacao(), veiculoInserido.getAnoFabricacao());
        assertEquals(veiculo.getPreco(), veiculoInserido.getPreco(), 0.001);
        assertEquals(veiculo.getStatus(), veiculoInserido.getStatus());
    }

    @Test
    public void test_inserir_e_buscar_veiculos_por_status() {

        Veiculo veiculo1 = new Veiculo("TestMarca1", "TestModelo1", "TestCor1", 2022, 50000.0);
        Veiculo veiculo2 = new Veiculo("TestMarca2", "TestModelo2", "TestCor2", 2022, 60000.0);

        veiculo1.setStatus("TestStatus1");
        veiculo2.setStatus("TestStatus2");

        repository.inserirVeiculo(veiculo1);
        repository.inserirVeiculo(veiculo2);

        List<Veiculo> veiculosDisponiveis = repository.listarVeiculosPorStatus("TestStatus1");
        List<Veiculo> veiculosVendidos = repository.listarVeiculosPorStatus("TestStatus2");

        assertEquals(1, veiculosDisponiveis.size());
        assertEquals(1, veiculosVendidos.size());
    }
}