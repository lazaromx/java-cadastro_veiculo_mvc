package br.lazaro.repositories;

import br.lazaro.models.Veiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.*;

//import static org.mockito.Mockito.mock;
//import org.mockito.junit.jupiter.Extension;

import static org.mockito.Mockito.*;
@ExtendWith(Mockito.class)



public class VeiculoRepositoryTest {
    private VeiculoRepository repository;

    private Veiculo veiculo;

    @BeforeEach
    void setUp(){
        repository = mock(VeiculoRepository.class);
        veiculo = new Veiculo("Chevrolet", "Camaro", "Amarelo", 2007, 200000);
    }

    @Test
    void comprarVeiculoComSucesso(){
        repository.comprarVeiculo(veiculo);
        List<Veiculo> estoque = repository.estoque();

        Assertions.assertEquals( 1,estoque.size());
        Assertions.assertTrue(estoque.contains(veiculo));
    }

//    @Test
//    void venderVeiculoComSucesso() {
//        this.comprarVeiculoComSucesso();
//
//        Assertions.assertTrue(veiculoRepository.venderVeiculo(veiculo));
//        Assertions.assertFalse(veiculoRepository.estoque().contains(veiculo));
//
//        List<Veiculo> vendidos = veiculoRepository.vendidos();
//
//        Assertions.assertEquals(1, veiculoRepository.estoque().size());
//        Assertions.assertEquals(1, vendidos.size());
//        Assertions.assertTrue(vendidos.contains(veiculo));
//    }
//
//    @Test
//    void retonarVeiculoCorreto(){
//        veiculo = veiculoRepository.getVeiculo(0);
//        Assertions.assertNotNull(veiculo);
//        Assertions.assertEquals("Renault", veiculo.getMarca());
//        Assertions.assertEquals("Duster", veiculo.getModelo());
//        Assertions.assertEquals("Preto", veiculo.getCor());
//        Assertions.assertEquals(2010, veiculo.getAnoFabricacao());
//        Assertions.assertEquals(200, veiculo.getPreco());
//    }
}

