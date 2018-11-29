package atox.utils;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public abstract class MaskFieldUtil {
    public final static int cpfLength = 14;
    public final static int cnpjLength = 18;
    public final static int telefoneLength = 14;
    public final static int placaLength = 8;
    public final static int anoLength = 4;
    public final static int parcelasLength = 2;
    public final static int kmLength = 6;

    public static void valorMask(TextField textField) {
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
            textField.setText(value);
            MaskFieldUtil.positionCaret(textField);
        });
    }

    public static void anoMask(TextField textField) {
        MaskFieldUtil.maxField(textField, anoLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    try {
                        textField.setText(value);
                        MaskFieldUtil.positionCaret(textField);
                    }catch(Exception ex){}
                }
        );
    }

    public static void parcelasMask(TextField textField) {
        MaskFieldUtil.maxField(textField, parcelasLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    try {
                        textField.setText(value);
                        MaskFieldUtil.positionCaret(textField);
                    }catch(Exception ex){}
                }
        );
    }

    public static void kmMask(TextField textField) {
        MaskFieldUtil.maxField(textField, kmLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    try {
                        textField.setText(value);
                        MaskFieldUtil.positionCaret(textField);
                    }catch(Exception ex){}
                }
        );
    }

    public static void cpfMask(TextField textField) {
        MaskFieldUtil.maxField(textField, cpfLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                    value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                    value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
                    try {
                        textField.setText(value);
                        MaskFieldUtil.positionCaret(textField);
                    }catch(Exception ex){}
                }
        );
    }

    public static void cnpjMask(TextField textField) {
        MaskFieldUtil.maxField(textField, cnpjLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
                    value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                    value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
                    value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                    textField.setText(value);
                    MaskFieldUtil.positionCaret(textField);
                }
        );
    }

    public static void cpfCnpjMask(TextField textField) {
        MaskFieldUtil.maxField(textField, cnpjLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    String value = textField.getText();
                    if (number2.intValue() <= cpfLength) {
                        value = value.replaceAll("[^0-9]", "");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
                    } else {
                        value = value.replaceAll("[^0-9]", "");
                        value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
                        value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                    }
                    textField.setText(value);
                    MaskFieldUtil.positionCaret(textField);
                }
        );
    }

    public static void telefoneMask(TextField textField) {
        MaskFieldUtil.maxField(textField, telefoneLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
                    try {
                        String value = textField.getText();
                        value = value.replaceAll("[^0-9]", "");
                        int tam = value.length();
                        value = value.replaceFirst("(\\d{2})(\\d)", "($1)$2");
                        value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                        if (tam > 10) {
                            value = value.replaceAll("-", "");
                            value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
                        }
                        textField.setText(value);
                        MaskFieldUtil.positionCaret(textField);

                    } catch (Exception ex) {
                    }
                }
        );
    }

    public static void placaMask(TextField textField) {
        MaskFieldUtil.maxField(textField, placaLength);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            try {
                String value = textField.getText();
                if(value.length() < 4) {
                    value = value.replaceAll("[^A-Z]", "");
                }
                value = value.replaceAll("[^0-9A-Z]", "");
                value = value.replaceFirst("(\\w{3})(\\w)", "$1-$2");
                textField.setText(value);
                MaskFieldUtil.positionCaret(textField);

            } catch (Exception ex) {
            }
        });
    }

    public static void maxField(TextField textField, Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue == null || newValue.length() > length) {
                        textField.setText(oldValue);
                    }
                }
        );
    }
    public static void maxField(ComboBox<String> comboBox, Integer length) {
        comboBox.editorProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue == null || newValue.getText().length() > length) {
                        comboBox.setValue(oldValue.getText());
                    }
                }
        );
    }

    public static void positionCaret(ComboBox<String> comboBox) {
        Platform.runLater(() -> {
                    if (comboBox.getValue().length() != 0) {
                        comboBox.getEditor().positionCaret(comboBox.getValue().length());
                    }
                }
        );
    }
    public static void positionCaret(TextField textField) {
        Platform.runLater(() -> {
                    if (textField.getText().length() != 0) {
                        textField.positionCaret(textField.getText().length());
                    }
                }
        );
    }
}