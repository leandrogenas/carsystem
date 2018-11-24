package atox.model;

import atox.BancoDeDados;

import java.sql.ResultSet;

public class Veiculo {

    private String placa, marca, modelo, ano, cor, numParcelas, cpfProprietario;
    private boolean importado;
    private float km;

    public Veiculo(String placa, String cpfProprietario, String numParcelas, String cor, String modelo, String marca, String ano, boolean importado, float km) {
        this.placa = placa;
        this.cpfProprietario = cpfProprietario;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.importado = importado;
        this.km = km;
    }

    public String getPlaca() {
        return placa;
    }
    public String getCpfProprietario() {
        return cpfProprietario;
    }
    public String getNumParcelas() {
        return numParcelas;
    }
    public String getMarca() {
        return marca;
    }
    public String getAno() {
        return ano;
    }
    public String getCor() {
        return cor;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public void setCpfProprietario(String cpfProprietario) {
        this.cpfProprietario = cpfProprietario;
    }
    public void setNumParcelas(String numParcelas) {
        this.numParcelas = numParcelas;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setKm(float km) {
        this.km = km;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public boolean isImportado() {
        return importado;
    }
    public void setcpfProprietario(String cpfProprietario) {
        this.cpfProprietario = cpfProprietario;
    }
    public void setImportado(boolean importado) {
        this.importado = importado;
    }

    public float getKm() {
        return km;
    }

    private static Veiculo buscaPorPlaca(String placa){
        Veiculo veiculo = null;
        try {
            String sql = "SELECT * FROM veiculo WHERE placa=" + placa + " LIMIT 1";
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            rSet.next();

            veiculo = new Veiculo(
                    rSet.getString("placa"),
                    rSet.getString("cpf_proprietario"),
                    rSet.getString("num_parcelas"),
                    rSet.getString("cor"),
                    rSet.getString("modelo"),
                    rSet.getString("marca"),
                    rSet.getString("ano"),
                    rSet.getBoolean("importado"),
                    rSet.getFloat("kilometragem")
                    );
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return veiculo;
    }
}
