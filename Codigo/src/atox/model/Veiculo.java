package atox.model;


import atox.BancoDeDados;

import java.sql.ResultSet;

public class Veiculo {

    private String placa;
    private Cliente proprietario;
    private String modelo;
    private String ano;
    private String cor;
    private boolean importado;
    private float km;

    Veiculo(String placa, Cliente proprietario, String modelo, String ano, String cor, boolean importado, float km){
        this.placa = placa;
        this.proprietario = proprietario;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.importado = importado;
        this.km = km;

    }

    public String getPlaca() {
        return placa;
    }
    public Cliente getProprietario() {
        return proprietario;
    }
    public String getModelo() {
        return modelo;
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
    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
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
                    Cliente.buscaPorDocumento(Documento.Tipo.CPF, rSet.getString("cpf_proprietario")),
                    rSet.getString("modelo"),
                    rSet.getString("ano"),
                    rSet.getString("cor"),
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
