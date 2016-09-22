package com.up.sim.clientview;

import com.up.sim.clientview.utils.DialogUtil;
import com.up.sim.commons.Base64Type;
import com.up.sim.commons.Encoding;
import com.up.sim.service.utils.Base64Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author paranoidq
 * @since 0.1
 */
public class BASE64Controller implements Initializable {

    private static final Logger logger = Logger.getLogger(BASE64Controller.class);

    @FXML
    private ComboBox<String> comboBox_base64Type;

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
        comboBox_base64Type.setItems(Base64Type.getBase64Type4View());
        comboBox_base64Type.getSelectionModel().selectLast();

        comboBox_encoding.setItems(Encoding.getEncodings4View());
        comboBox_encoding.getSelectionModel().selectFirst();

        t_left.setWrapText(true);
        t_right.setWrapText(true);
    }

    @FXML
    private void onClear() {
        t_left.clear();
        t_right.clear();
    }

    @FXML
    private void onEncode() {
        String leftInput = t_left.getText().trim();
        if (StringUtils.isEmpty(leftInput)) {
            return;
        }
        Base64Type type = Base64Type.get(comboBox_base64Type.getSelectionModel().getSelectedItem());
        String encoded = StringUtils.EMPTY;
        if (Base64Type.HEX_BASE64 == type) {
            try {
                encoded = Base64Service.getInstance().encodeHex(leftInput);
            } catch (DecoderException e) {
                logger.error("输入byte数组的16进制字符串不符合规范, (长度必须为偶数,且字符范围[0-9a-z])");
                DialogUtil.showErrorAlert("输入错误", "输入byte数组的16进制字符串不符合规范, (长度必须为偶数,且字符范围[0-9a-z])");
            }
        } else {
            Encoding encoding = comboBox_encoding.getSelectionModel().getSelectedItem();
            encoded = Base64Service.getInstance().encode(leftInput, encoding);
        }
        t_right.setText(encoded);
    }

    @FXML
    private void onDecode() {
        String rightInput = t_right.getText().trim();
        if (StringUtils.isEmpty(rightInput)) {
            return;
        }
        Base64Type type = Base64Type.get(comboBox_base64Type.getSelectionModel().getSelectedItem());
        String decoded;
        if (Base64Type.HEX_BASE64 == type) {
            decoded = Base64Service.getInstance().decodeHex(rightInput);
        } else {
            decoded = Base64Service.getInstance().decode(rightInput);
        }
        t_left.setText(decoded);
    }

}
