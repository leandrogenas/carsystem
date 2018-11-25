package atox.model;


import atox.BancoDeDados;

import java.sql.ResultSet;

public class Veiculo {

    private int id;
    private String placa;
    private Cliente proprietario;
    private String modelo;
    private String ano;
    private String cor;
    private boolean importado;
    private float km;

    public Veiculo(String placa){
        this.placa = placa;
    }

    public Veiculo(String placa, Cliente proprietario, String modelo, String ano, String cor, boolean importado, float km){
        this.placa = placa;
        this.proprietario = proprietario;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.importado = importado;
        this.km = km;
    }

    public Veiculo(int id, String placa, Cliente proprietario, String modelo, String ano, String cor, boolean importado, float km){
        this(placa, proprietario, modelo, ano, cor, importado, km);
        this.id = id;
    }

    public int getId(){ return id; }
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
    public float getKm() {
        return km;
    }
    public boolean isImportado() { return importado; }

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
    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }
    public void setImportado(boolean importado) {
        this.importado = importado;
    }

    private static Veiculo buscaPorPlaca(String placa){
        Veiculo veiculo = null;
        try {
            String sql = "SELECT * FROM veiculo WHERE placa=" + placa + " LIMIT 1";
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            rSet.next();

            veiculo = new Veiculo(
                    rSet.getInt("cod_veiculo"),
                    rSet.getString("placa"),
                    Cliente.buscaPorId(rSet.getInt("cod_proprietario")),
                    rSet.getString("cor"),
                    rSet.getString("modelo"),
                    rSet.getString("ano"),
                    rSet.getBoolean("importado"),
                    rSet.getFloat("kilometragem")
            );

            veiculo.setAno(rSet.getString("ano"));
            veiculo.setCor(rSet.getString("cor"));
            veiculo.setModelo(rSet.getString("modelo"));
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return veiculo;
    }

}