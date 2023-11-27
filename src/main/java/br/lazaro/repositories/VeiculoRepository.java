package br.lazaro.repositories;

import br.lazaro.models.*;
import br.lazaro.repositories.db.DBConfig;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class VeiculoRepository {

    private static String URL;
    private static String USUARIO;
    private static String SENHA;

    private static final String INSERIR_VEICULO = "INSERT INTO tb_veiculos (marca, modelo, cor, ano, preco, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String BUSCAR_VEICULO_POR_ID = "SELECT * FROM tb_veiculos WHERE id=?";
    private static final String LISTAR_VEICULOS_POR_STATUS = "SELECT * FROM tb_veiculos WHERE status=?";
    private static final String LISTAR_VEICULOS = "SELECT * FROM tb_veiculos";
    private static final String ATUALIZAR_VEICULO = "UPDATE tb_veiculos SET marca=?, modelo=?, cor=?, ano=?, preco=?, status=? WHERE id=?";
//    private static final String DELETAR_VEICULO = "DELETE FROM tb_veiculos WHERE id=?";

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
    public void inserirVeiculo(Veiculo veiculo) {
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conexao.prepareStatement(INSERIR_VEICULO)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setInt(4, veiculo.getAnoFabricacao());
            stmt.setDouble(5, veiculo.getPreco());
            stmt.setString(6, veiculo.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
//            logger.error("Erro ao inserir veículo no banco de dados", e);
        }
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

//    public void deletarVeiculo(int id) {
//        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
//             PreparedStatement stmt = conexao.prepareStatement(DELETAR_VEICULO)) {
//
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
////            logger.error("Erro ao excluir veículo por ID no banco de dados", e);
//        }
//    }


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
//            logger.error("Erro ao buscar veículo por ID no banco de dados", e);
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
//            logger.error("Erro ao buscar veículos por STATUS no banco de dados", e);
        }

        return veiculos;
    }

//    public List<Veiculo> listarVeiculos() {
//        List<Veiculo> veiculos = new ArrayList<>();
//
//        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
//             PreparedStatement stmt = conexao.prepareStatement(LISTAR_VEICULOS);
//             ResultSet resultSet = stmt.executeQuery()) {
//
//            while (resultSet.next()) {
//                veiculos.add(carregarVeiculo(resultSet));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
////            logger.error("Erro ao buscar veículos no banco de dados", e);
//        }
//
//        return veiculos;
//    }

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


//
//public class VeiculoRepository {
//    private final List<Veiculo> veiculos;
//
//    public VeiculoRepository() {
//        this.veiculos = new ArrayList<>();
//
//        // adicionando apenas para até conectar a uma base de dados real
//        this.adicionarVeiculo(new Veiculo("Renault", "Duster", "Preto", 2010, 200));
//    }
//
//    public void adicionarVeiculo(Veiculo veiculo) {
//        veiculo.setStatus("estoque");
//        veiculos.add(veiculo);
//    }
//
//    public void atualizarVeiculo(Veiculo veiculo) {
//        veiculo.setStatus("vendido");
//    }
//
//    public Veiculo obterVeiculoPorId(int idVeiculo) {
//        try {
//            return obterVeiculosPorStatus("estoque").get(idVeiculo);
//        } catch (IndexOutOfBoundsException ix) {
//            throw new NoSuchElementException("Código de Veículo não existe!", ix);
//        }
//    }
//
//    public List<Veiculo> obterVeiculosPorStatus(String status) {
//        return filtrarVeiculosPorStatus(status);
//    }
//
//    private List<Veiculo> filtrarVeiculosPorStatus(String status) {
//        List<Veiculo> veiculosPorStatus = new ArrayList<>();
//        for (Veiculo veiculo : this.veiculos) {
//            if (veiculo.getStatus().equals(status)) {
//                veiculosPorStatus.add(veiculo);
//            }
//        }
//        return veiculosPorStatus;
//    }
//
//
//}
