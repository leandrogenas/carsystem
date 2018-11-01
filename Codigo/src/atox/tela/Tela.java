package atox.tela;

import atox.exception.CarSystemException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Classe responsável por carregar os FXML's das telas, é abstrata
 * pois é apenas uma 'base' de todas as outras telas, pois tem métodos
 * em comum
 *
 * Também tem dois métodos abstratos que devem ser implementados em cada classe
 *
 * (Colocar nas classes derivadas métodos que vão manipular as coisas da tela)
 */

public abstract class Tela {

    protected Pane conteudo;
    protected ArrayList<String> parametros = new ArrayList<>();


    private void loadConteudo() throws CarSystemException{
        URL fxml = getClass().getResource(getNomeFXML());

        if(fxml == null)
            throw new CarSystemException("Arquivo FXML não encontrado");
        try {
            conteudo = FXMLLoader.load(fxml);
        }catch (IOException e){
            throw new CarSystemException(e.getMessage());
        }
    }

    public Tela(){
        try {
            loadConteudo();
        } catch (CarSystemException e) {
            e.printStackTrace();
            // Todo: log
        }
    }

    public Tela(ArrayList<String> parametros){
        this();
        this.parametros = parametros;
    }

    public Pane getPane() throws CarSystemException{
        return conteudo;
    }

    public Node getNode(){
        return conteudo;
    }

    public abstract String getNomeFXML();
    public abstract String getTitulo();

}
