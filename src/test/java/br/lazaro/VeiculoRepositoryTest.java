package br.lazaro;

import br.lazaro.models.Veiculo;
import br.lazaro.repositories.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoRepositoryTest {
    @InjectMocks
    VeiculoRepository repository;

    @Mock
    Veiculo veiculoMock;

    @BeforeEach
    void setUp() {
        repository = new VeiculoRepository();
        veiculoMock = mock(Veiculo.class);
    }

    @Test
    public void comprarVeiculo() {
        Veiculo veiculo = new Veiculo("Fiat", "Uno", "Branco", 2015, 15000.0);
        repository.adicionarVeiculo(veiculo);
        List<Veiculo> estoque = repository.obterVeiculosPorStatus("estoque");
        assertEquals(2, estoque.size());
        assertEquals(veiculo, estoque.get(1));
    }

    @Test
    public void test_estoque() {
        List<Veiculo> estoque = repository.obterVeiculosPorStatus("estoque");
        assertEquals(1, estoque.size());
        assertEquals("Renault", estoque.get(0).getMarca());
        assertEquals("Duster", estoque.get(0).getModelo());
        assertEquals("Preto", estoque.get(0).getCor());
        assertEquals(2010, estoque.get(0).getAnoFabricacao());
        assertEquals(200, estoque.get(0).getPreco(), 0.001);
    }

    @Test
    public void test_vender_veiculo() {
//        Veiculo veiculo = new Veiculo("Fiat", "Uno", "Branco", 2015, 15000.0);
//        repository.comprarVeiculo(veiculo);
        repository.atualizarVeiculo(veiculoMock);
        assertEquals("vendido", veiculoMock.getStatus());
    }

    @Test
    public void test_vendidos() {
        Veiculo veiculo = new Veiculo("Fiat", "Uno", "Branco", 2015, 15000.0);

//        repository.comprarVeiculo(veiculo);
        veiculoMock.setStatus("vendido");
        repository.atualizarVeiculo(veiculoMock);
        List<Veiculo> vendidos = repository.obterVeiculosPorStatus("vendido");
        assertEquals(1, vendidos.size());
        assertEquals(veiculoMock, vendidos.get(0));
    }


    @Test
    public void test_get_veiculo_with_valid_id() {
        veiculoMock = repository.obterVeiculoPorId(0);
        assertNotNull(veiculoMock);
        assertEquals("Renault", veiculoMock.getMarca());
        assertEquals("Duster", veiculoMock.getModelo());
        assertEquals("Preto", veiculoMock.getCor());
        assertEquals(2010, veiculoMock.getAnoFabricacao());
        assertEquals(200, veiculoMock.getPreco(), 0.001);
    }

    @Test
    public void test_comprar_veiculo_with_vendido_status() {

        int estoqueAtual = repository.obterVeiculosPorStatus("estoque").size();
        int vendidosAtual = repository.obterVeiculosPorStatus("vendidoF").size();

        // força o status para vendido a compra deve alterar para estoque
        Veiculo veiculo = new Veiculo("Fiat", "Uno", "Branco", 2015, 15000.0);
        veiculo.setStatus("vendido");

        repository.adicionarVeiculo(veiculo);

        // Validando que o estoque adicionou +1 e vendidos manteve a mesma quantidade
        assertEquals(estoqueAtual + 1, repository.obterVeiculosPorStatus("estoque").size());
        assertEquals(vendidosAtual, repository.obterVeiculosPorStatus("vendido").size());
    }
}