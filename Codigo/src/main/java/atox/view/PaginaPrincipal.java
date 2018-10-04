package atox.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaginaPrincipal extends PaginaBase {

    public PaginaPrincipal() throws Exception {
    }

    @Override
    public String getNomeFXML() { return "fxml/template_paginas.fxml"; }

    @Override
    public String getTitulo() { return "Principal"; }

}

