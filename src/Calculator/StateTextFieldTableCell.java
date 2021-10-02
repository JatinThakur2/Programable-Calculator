package Calculator;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.util.function.IntFunction;

public class StateTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {

    private final IntFunction<ObservableValue<Boolean>> editableExtractor;

    public StateTextFieldTableCell(IntFunction<ObservableValue<Boolean>> editableExtractor, StringConverter<T> converter) {
        super(converter);
        this.editableExtractor = editableExtractor;
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);
        if (i == -1)  {
            editableProperty().unbind();
        } else {
            editableProperty().bind(editableExtractor.apply(i));
        }
    }

    public static <U, V> Callback<TableColumn<U, V>, TableCell<U, V>> forTableColumn(
            IntFunction<ObservableValue<Boolean>> editableExtractor,
            StringConverter<V> converter) {
        return column -> new StateTextFieldTableCell<>(editableExtractor, converter);
    }

    public static <U> Callback<TableColumn<U, String>, TableCell<U, String>> forTableColumn(
            IntFunction<ObservableValue<Boolean>> editableExtractor) {
        return forTableColumn(editableExtractor, new DefaultStringConverter());
    }

}