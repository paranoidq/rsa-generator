package com.up.sim.clientview.utils;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

/**
 * @author paranoidq
 * @since 0.1
 */
public class DialogUtil {

    public static void showErrorAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(StringUtils.EMPTY);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static boolean showConformation(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(StringUtils.EMPTY);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    public static int showConformation2(String title, String text) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        int flag = 0;
        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                alert.close();

                return;
            }
        });
        alert.setTitle(title);
        alert.setHeaderText(StringUtils.EMPTY);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return 1;
        } else if (result.get() == ButtonType.CANCEL) {
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean showInfo(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(StringUtils.EMPTY);
        alert.setContentText(text);
        alert.showAndWait();
        return true;
    }
}
