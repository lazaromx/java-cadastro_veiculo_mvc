package br.lazaro.repositories;

import br.lazaro.models.*;
import br.lazaro.repositories.db.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VeiculoRepository {

    private String URL;
    private String USUARIO;
    private String SENHA;

    private static final String INSERIR_VEICULO = "INSERT INTO tb_veiculos (marca, modelo, cor, ano, preco, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String BUSCAR_VEICULO_POR_ID = "SELECT * FROM tb_veiculos WHERE id=?";
    private static final String LISTAR_VEICULOS_POR_STATUS = "SELECT * FROM tb_veiculos WHERE status=?";
    private static final String LISTAR_VEICULOS = "SELECT * FROM tb_veiculos";
    private static final String ATUALIZAR_VEICULO = "UPDATE tb_veiculos SET marca=?, modelo=?, cor=?, ano=?, preco=?, status=? WHERE id=?";

    public VeiculoRepository() {
        this.URL = DBConfig.getUrl();
        this.USUARIO = DBConfig.getUsuario();
        this.SENHA = DBConfig.getSenha();
    }

    public VeiculoRepository(String url, String usuario, String senha) {
        this.URL = url;
        this.USUARIO = usuario;
        this.SENHA = senha;
    }
    public int inserirVeiculo(Veiculo veiculo) {
        int idInserido = -1; // Valor padrão indicando que nenhum ID foi inserido


        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conexao.prepareStatement(INSERIR_VEICULO, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setInt(4, veiculo.getAnoFabricacao());
            stmt.setDouble(5, veiculo.getPreco());
            stmt.setString(6, veiculo.getStatus());
            stmt.executeUpdate();

            // Obtém as chaves geradas (no caso, o ID gerado)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idInserido = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
//            logger.error("Erro ao inserir veículo no banco de dados", e);
        }

        return idInserido;
    }

    public void atualizarVeiculo(Veiculo veiculo) {
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conexao.prepareStatement(ATUALIZAR_VEICULO)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setInt(4, veiculo.getAnoFabricacao());
            stmt.setDouble(5, veiculo.getPreco());
            stmt.setString(6, veiculo.getStatus());
            stmt.setInt(7, veiculo.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Veiculo buscarVeiculoPorId(int id) {
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conexao.prepareStatement(BUSCAR_VEICULO_POR_ID)) {

            stmt.setInt(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return carregarVeiculo(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Veiculo> listarVeiculosPorStatus(String status) {
        List<Veiculo> veiculos = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conexao.prepareStatement(LISTAR_VEICULOS_POR_STATUS)) {
            stmt.setString(1, status);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    veiculos.add(carregarVeiculo(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return veiculos;
    }

    private Veiculo carregarVeiculo(ResultSet resultSet) throws SQLException {
        Veiculo veiculo = new Veiculo(
                resultSet.getString("marca"),
                resultSet.getString("modelo"),
                resultSet.getString("cor"),
                resultSet.getInt("ano"),
                resultSet.getDouble("preco")
        );
        veiculo.setStatus(resultSet.getString("status"));
        veiculo.setId(resultSet.getInt("id"));
        return veiculo;
    }
}
