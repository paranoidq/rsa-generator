package com.up.sim.clientview;

import com.up.sim.clientview.utils.DialogUtil;
import com.up.sim.commons.Encoding;
import com.up.sim.service.utils.UrlEncodeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author paranoidq
 * @since 0.1
 */
public class UrlEncodeController implements Initializable {

    private static final Logger logger = Logger.getLogger(UrlEncodeController.class);

    @FXML
    private ComboBox<Encoding> comboBox_encoding;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_encode;

    @FXML
    private Button btn_decode;

    @FXML
    private TextArea t_left;

    @FXML
    private TextArea t_right;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox_encoding.setItems(Encoding.getEncodings4View());
        comboBox_encoding.getSelectionModel().select(Encoding.UTF_8);
    }

    @FXML
    private void onClear() {
        t_left.clear();
        t_right.clear();
        t_left.setWrapText(true);
        t_right.setWrapText(true);
    }

    @FXML
    private void onEncode() {
        String leftInput = t_left.getText().trim();
        if (StringUtils.isEmpty(leftInput)) {
            return;
        }
        String encoded = StringUtils.EMPTY;
        try {
            encoded = UrlEncodeService.getInstance().encode(leftInput, comboBox_encoding.getSelectionModel().getSelectedItem());
        } catch (UnsupportedEncodingException e) {
            logger.error("URLEncode发生错误");
            DialogUtil.showErrorAlert("未知错误", "URLEncode发生错误");
        }
        t_right.setText(encoded);
    }

    @FXML
    private void onDecode() {
        String rightInput = t_right.getText().trim();
        if (StringUtils.isEmpty(rightInput)) {
            return;
        }
        String decoded = null;
        try {
            decoded = UrlEncodeService.getInstance().decode(rightInput, comboBox_encoding.getSelectionModel().getSelectedItem());
        } catch (UnsupportedEncodingException e) {
            logger.error("URLDecode发生错误");
            DialogUtil.showErrorAlert("未知错误", "URLDecode发生错误");
        }
        t_left.setText(decoded);
    }

}
