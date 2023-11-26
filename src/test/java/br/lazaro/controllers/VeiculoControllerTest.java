package br.lazaro.controllers;

import br.lazaro.models.Veiculo;
import br.lazaro.repositories.*;
import br.lazaro.exceptions.VeiculoNotFoundException;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class VeiculoControllerTest {

    private VeiculoController controller;

    private VeiculoRepository repositoryMock;

    private Veiculo veiculoMock;

    @BeforeEach
    void setUp() {
        // inicializacao padrao para todos os testes
        repositoryMock = mock(VeiculoRepository.class);
        veiculoMock = mock(Veiculo.class);
        controller = new VeiculoController(repositoryMock);

        //necessario para testes de vendas
        when(repositoryMock.obterVeiculoPorId(anyInt())).thenReturn(veiculoMock);

        // necessario para testes de compras
        when(veiculoMock.getMarca()).thenReturn("Chevrolet");
        when(veiculoMock.getModelo()).thenReturn("Camaro");
        when(veiculoMock.getCor()).thenReturn("Amarelo");
        when(veiculoMock.getAnoFabricacao()).thenReturn(2007);
        when(veiculoMock.getPreco()).thenReturn(200000.0);
        when(veiculoMock.getStatus()).thenReturn("estoque");

//        doNothing().when(veiculoMock).setMarca("Chevrolet");
    }

//    @Test
//    public void test_comprar_veiculo() {
//
//        when(repositoryMock.adicionarVeiculo(any(Veiculo.class))).thenReturn(new Veiculo());
//        Veiculo veiculo = new Veiculo();
//        VeiculoController controller = new VeiculoController(repositoryMock);
//        controller.comprarVeiculo(veiculo);
//
//        verify(repositoryMock).adicionarVeiculo(veiculo);
//    }

    @Test
    public void comprarVeiculoComSucesso() {
        controller.comprarVeiculo(veiculoMock);
        verify(repositoryMock).adicionarVeiculo(veiculoMock);
    }

    @Test
    public void comprarVeiculoPrecoNegativo() {
        when(veiculoMock.getPreco()).thenReturn(-10000.0);
        assertThrows(IllegalArgumentException.class, () -> controller.comprarVeiculo(veiculoMock));
    }

    @Test
    public void test_comprar_veiculos_concorrentes() throws InterruptedException {
        Veiculo veiculoMock2 = mock(Veiculo.class);
        when(veiculoMock.getPreco()).thenReturn(10000.0);
        when(veiculoMock2.getPreco()).thenReturn(20000.0);
        VeiculoController controller = new VeiculoController(repositoryMock);

        Thread thread1 = new Thread(() -> controller.comprarVeiculo(veiculoMock));
        Thread thread2 = new Thread(() -> controller.comprarVeiculo(veiculoMock2));

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        verify(repositoryMock).adicionarVeiculo(veiculoMock);
        verify(repositoryMock).adicionarVeiculo(veiculoMock2);
    }

    @Test
    public void test_get_veiculo() {
        controller.getVeiculo(0);
        verify(repositoryMock).obterVeiculoPorId(0);
    }

    @Test
    public void test_listar_estoque() {
        controller.estoque();
        verify(repositoryMock).obterVeiculosPorStatus("estoque");
    }

    @Test
    public void test_vender_veiculo() {
        controller.venderVeiculo(0);
        verify(repositoryMock).atualizarVeiculo(veiculoMock);
    }


    @Test
    public void test_nao_vender_veiculo_fora_de_estoque() {
        Veiculo veiculoMock = mock(Veiculo.class);
        when(veiculoMock.getStatus()).thenReturn("vendido");
        when(repositoryMock.obterVeiculoPorId(0)).thenReturn(veiculoMock);

        assertThrows(VeiculoNotFoundException.class, () -> controller.venderVeiculo(0));
    }

    @Test
    public void test_vender_veiculo_estoque_multiplos_veiculos() {
        Veiculo veiculoMock2 = mock(Veiculo.class);
        when(veiculoMock.getStatus()).thenReturn("estoque");
        when(veiculoMock2.getStatus()).thenReturn("estoque");

        controller.venderVeiculo(0);

        verify(repositoryMock, times(1)).atualizarVeiculo(veiculoMock);
    }

    @Test
    public void test_vender_veiculo_estoque_vazio() {
        repositoryMock = mock(VeiculoRepository.class);
        veiculoMock = mock(Veiculo.class);
        controller = new VeiculoController(repositoryMock);

        assertThrows(NullPointerException.class, () -> controller.venderVeiculo(0));
    }

    @Test
    public void test_lancar_excecao_veiculo_nao_existe() {
        when(veiculoMock.getStatus()).thenReturn("estoque");
        when(repositoryMock.obterVeiculoPorId(1)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> controller.venderVeiculo(1));
    }

    @Test
    public void test_get_veiculo_invalid_id() {
        when(repositoryMock.obterVeiculoPorId(anyInt())).thenThrow(new IndexOutOfBoundsException());
        assertThrows(IndexOutOfBoundsException.class, () -> controller.getVeiculo(0));
    }

    @Test
    public void test_listar_vendidos() {
        controller.vendidos();
        verify(repositoryMock).obterVeiculosPorStatus("vendido");
    }

}