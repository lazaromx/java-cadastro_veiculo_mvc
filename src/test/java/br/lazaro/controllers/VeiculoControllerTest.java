package br.lazaro.controllers;

import br.lazaro.repositories.VeiculoRepository;
import br.lazaro.models.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;

class VeiculoControllerTest {
    private VeiculoController controller;
    private VeiculoRepository repositoryMock;
    private Veiculo veiculoMock;


    @BeforeEach
    void setUp(){
        repositoryMock = mock(VeiculoRepository.class);
        veiculoMock = mock(Veiculo.class);
        controller = new VeiculoController(repositoryMock);
        when(repositoryMock.getVeiculo(anyInt())).thenReturn(veiculoMock);

        when(veiculoMock.getMarca()).thenReturn("Chevrolet");
        when(veiculoMock.getModelo()).thenReturn("Camaro");
        when(veiculoMock.getCor()).thenReturn("Amarelo");
        when(veiculoMock.getAnoFabricacao()).thenReturn(2007);
        when(veiculoMock.getPreco()).thenReturn(200000.0);
        when(veiculoMock.getStatus()).thenReturn("estoque");

//        when(veiculoMock.setMarca()).thenReturn("Chevrolet");
//        when(veiculoMock.setModelo()).thenReturn("Camaro");
//        when(veiculoMock.setCor()).thenReturn("Amarelo");
//        when(veiculoMock.setAnoFabricacao()).thenReturn(2007);
//        when(veiculoMock.setPreco()).thenReturn(200000.0);
//        when(veiculoMock.getStatus()).thenReturn("estoque");
    }

    @Test
    void comprarVeiculoUmaVez(){
        veiculoMock.setModelo("Camaro");
        veiculoMock.setCor("Amarelo");
        veiculoMock.setAnoFabricacao(2007);
        veiculoMock.setMarca("Chevrolet");
        veiculoMock.setPreco(200000);
        veiculoMock.setStatus("estoque");

        controller.comprarVeiculo(veiculoMock);

        verify(repositoryMock, times(1)).comprarVeiculo(veiculoMock);
    }

    @Test
    void venderVeiculoComSucesso(){

        when(veiculoMock.getStatus()).thenReturn("estoque");
        when(repositoryMock.getVeiculo(0)).thenReturn(veiculoMock);


        controller.venderVeiculo(0);

        verify(repositoryMock, times(1)).venderVeiculo(veiculoMock);


        boolean result = controller.venderVeiculo(0);
        assertTrue(result);

    }
}