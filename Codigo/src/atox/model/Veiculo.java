package atox.model;

public class Veiculo {
    private String placa, marca, modelo, ano, cor, numParcelas, cpfProprietario;
    private boolean importado;
    private float km;

    public Veiculo() {}
    public Veiculo(String placa, String cpfProprietario, String numParcelas, String cor, String modelo, String marca, boolean importado, float km) {
        setPlaca(placa);
        setCpfProprietario(cpfProprietario);
        setNumParcelas(numParcelas);
        setCor(cor);
        setModelo(modelo);
        setMarca(marca);
        setImportado(importado);
        setKm(km);
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCpfProprietario() {
        return cpfProprietario;
    }

    public void setCpfProprietario(String cpfProprietario) {
        this.cpfProprietario = cpfProprietario;
    }

    public String getNumParcelas() { return numParcelas; }

    public void setNumParcelas(String numParcelas) { this.numParcelas = numParcelas; }

    public String getMarca() { return marca; }

    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isImportado() {
        return importado;
    }

    public void setImportado(boolean importado) {
        this.importado = importado;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }
}
