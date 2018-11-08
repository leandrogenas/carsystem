package atox.utils;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public abstract class MaskFieldUtil {
    public final static int cpfLength = 14;
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

    private static void positionCaret(TextField textField) {
        Platform.runLater(() -> {
                    if (textField.getText().length() != 0) {
                        textField.positionCaret(textField.getText().length());
                    }
                }
        );
    }

    public static void maxField(TextField textField, Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue == null || newValue.length() > length) {
                        textField.setText(oldValue);
                    }
                }
        );
    }
}