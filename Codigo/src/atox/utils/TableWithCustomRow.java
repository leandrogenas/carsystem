package atox.utils;
import java.util.List;
import java.util.function.Function;

import atox.model.Orcamento;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class TableWithCustomRow  {

    public interface PaneDetalhes<S>{
        void desenhar(BorderPane details, S item);
    }

    public static <S> TableView<S> createTable(TableView<S> table, PaneDetalhes detalhes) {
        table.setRowFactory(tv -> new TableRow<S>() {
            Node detailsPane;
            {
                selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        getChildren().add(detailsPane);
                    } else {
                        getChildren().remove(detailsPane);
                    }
                    this.requestLayout();
                });
                detailsPane = createDetailsPane(itemProperty(), detalhes);
            }

            @Override
            protected double computePrefHeight(double width) {
                if (isSelected()) {
                    return super.computePrefHeight(width)+detailsPane.prefHeight(getWidth());
                } else {
                    return super.computePrefHeight(width);
                }
            }

            @Override
            protected void layoutChildren() {
                super.layoutChildren();
                if (isSelected()) {
                    double width = getWidth();
                    double paneHeight = detailsPane.prefHeight(width);
                    detailsPane.resizeRelocate(0, getHeight()-paneHeight, width, paneHeight);
                }
            }
        });

        return table;
    }

    public static <S> TableView<S> createTable(TableView<S> table, String label) {
        table.setRowFactory(tv -> new TableRow<S>() {
            Node detailsPane;
            {
                selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        getChildren().add(detailsPane);
                    } else {
                        getChildren().remove(detailsPane);
                    }
                    this.requestLayout();
                });
                detailsPane = createDetailsPane(itemProperty(), label);
            }

            @Override
            protected double computePrefHeight(double width) {
                if (isSelected()) {
                    return super.computePrefHeight(width)+detailsPane.prefHeight(getWidth());
                } else {
                    return super.computePrefHeight(width);
                }
            }

            @Override
            protected void layoutChildren() {
                super.layoutChildren();
                if (isSelected()) {
                    double width = getWidth();
                    double paneHeight = detailsPane.prefHeight(width);
                    detailsPane.resizeRelocate(0, getHeight()-paneHeight, width, paneHeight);
                }
            }
        });

        return table;
    }

    private static <S> Node createDetailsPane(ObjectProperty<S> item, String labelText) {
        BorderPane detailsPane = new BorderPane();
        Label detailsLabel = new Label();
        VBox labels = new VBox(5, new Label(labelText+": "), detailsLabel);
        labels.setAlignment(Pos.CENTER_LEFT);
        labels.setPadding(new Insets(2, 2, 2, 16));
        detailsPane.setCenter(labels);

        detailsPane.setStyle("-fx-background-color: -fx-background; -fx-background: skyblue;");
        item.addListener((obs, oldItem, newItem) -> {
            if (newItem == null) {
                detailsLabel.setText("");
            } else {
                detailsLabel.setText(newItem.toString());
            }
        });
        return detailsPane ;
    }


    private static <S> Node createDetailsPane(ObjectProperty<S> item, PaneDetalhes pane) {
        BorderPane detailsPane = new BorderPane();
        Label detailsLabel = new Label();

        VBox labels = new VBox(5, detailsLabel);
        labels.setAlignment(Pos.CENTER_LEFT);
        labels.setPadding(new Insets(2, 2, 2, 16));
        detailsPane.setCenter(labels);

        detailsPane.setStyle("-fx-background-color: -fx-background; -fx-background: skyblue;");
        item.addListener((obs, oldItem, newItem) -> {
            if (newItem == null) {
                detailsLabel.setText("");
            } else {
                pane.desenhar(detailsPane, newItem);
            }
        });
        return detailsPane ;
    }

    public static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        if(property != null)
            col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setPrefWidth(150);
        return col ;
    }
}