package com.up.sim.clientview;

import com.up.sim.clientview.utils.DialogUtil;
import com.up.sim.commons.DigestType;
import com.up.sim.commons.Encoding;
import com.up.sim.service.utils.DigestService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author paranoidq
 * @since 0.1
 */
public class DigestController implements Initializable {

    private static final Logger logger = Logger.getLogger(DigestController.class);

    @FXML
    private ComboBox<String> comboBox_digest;

    @FXML
    private ComboBox<Encoding> comboBox_encoding;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_digest;

    @FXML
    private Button btn_validate;

    @FXML
    private TextArea t_left;

    @FXML
    private TextArea t_right;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox_digest.setItems(DigestType.getDigest4View());
        comboBox_digest.getSelectionModel().selectFirst();

        comboBox_encoding.setItems(Encoding.getEncodings4View());
        comboBox_encoding.getSelectionModel().select(Encoding.UTF_8);

        t_left.setWrapText(true);
        t_right.setWrapText(true);
    }

    @FXML
    private void onClear() {
        t_left.clear();
        t_right.clear();
    }

    @FXML
    private void onDigest() {
        String leftInput = t_left.getText().trim();
        if (StringUtils.isEmpty(leftInput)) {
            return;
        }
        DigestType type = DigestType.get(comboBox_digest.getSelectionModel().getSelectedItem());
        Encoding encoding = comboBox_encoding.getSelectionModel().getSelectedItem();

        String encoded = DigestService.getInstance().digest(leftInput, type.value(), encoding);
        t_right.setText(encoded);
    }

    @FXML
    private void onValidate() {
        String leftInput = t_left.getText().trim();
        String rightInput = t_right.getText().trim();
        Encoding encoding = comboBox_encoding.getSelectionModel().getSelectedItem();

        boolean ifIdentical = StringUtils.equals(
                DigestService.getInstance().digest(leftInput, comboBox_digest.getSelectionModel().getSelectedItem(), encoding),
                rightInput);
        if (ifIdentical) {
            DialogUtil.showInfo("验证结果", "摘要值相同");
        } else {
            DialogUtil.showErrorAlert("验证结果", "摘要值不相同");
        }
    }
}
