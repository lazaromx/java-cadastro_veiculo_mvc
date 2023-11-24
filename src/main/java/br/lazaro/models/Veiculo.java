package br.lazaro.models;

public class Veiculo {
//    private int id;
    private String marca;
    private String modelo;
    private String cor;
    private int anoFabricacao;
    private double preco;
    private String status;

    public Veiculo(String marca, String modelo, String cor, int anoFabricacao, double preco)
    {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
        this.preco = preco;
        this.status = "estoque";
    }

    public String getMarca(){
        return this.marca;
    }
    public String getModelo(){
        return this.modelo;
    }
    public String getCor(){
        return this.cor;
    }
    public int getAnoFabricacao(){
        return this.anoFabricacao;
    }
    public double getPreco(){
        return this.preco;
    }

    public String getStatus(){
        return this.status;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    public void setCor(String cor){
        this.cor = cor;
    }
    public void setAnoFabricacao(int anoFabricacao){
        this.anoFabricacao = anoFabricacao;
    }
    public void setPreco(double preco){
        this.preco = preco;
    }



    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "Veiculo: " + getMarca() + " Modelo: " + getModelo() + " cor: " + getCor()
                + " ano: " + getAnoFabricacao() + " preço: " + getPreco() + " status: " + getStatus();
    }
}
