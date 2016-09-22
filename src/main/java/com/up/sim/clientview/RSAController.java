package com.up.sim.clientview;

import com.up.sim.clientview.utils.DialogUtil;
import com.up.sim.commons.Encoding;
import com.up.sim.security.cipher.CipherAlgorithm;
import com.up.sim.security.cipher.commons.Function;
import com.up.sim.security.cipher.customization.RSACipher;
import com.up.sim.security.key.utils.RSAKeyUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ResourceBundle;

/**
 * @author paranoidq
 * @since 0.1
 */
public class RSAController implements Initializable {

    private static final Logger logger = Logger.getLogger(RSAController.class);

    @FXML
    private ComboBox<Encoding> comboBox_encoding;

    @FXML
    private Button btn_genKeyPair;

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

    @FXML
    private ComboBox<String> combox_keySize;

    @FXML
    private TextField t_publicKey;

    @FXML
    private TextField t_privateKey;

    @FXML
    private RadioButton radioButton_encodeByPrivate;

    @FXML
    private RadioButton radioButton_encodeByPublic;

    @FXML
    private ToggleGroup radio_group = new ToggleGroup();

    @FXML
    private Button btn_loadPrivateKey;

    @FXML
    private Button btn_loadPublicKey;

    @FXML
    private Button btn_saveKeys;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox_encoding.setItems(Encoding.getEncodings4View());
        comboBox_encoding.getSelectionModel().select(Encoding.UTF_8);
        t_left.setWrapText(true);
        t_right.setWrapText(true);

        radio_group.getToggles().add(radioButton_encodeByPrivate);
        radio_group.getToggles().add(radioButton_encodeByPublic);
        radioButton_encodeByPrivate.setSelected(true);

        ObservableList<String> keySizeItems = FXCollections.observableArrayList("1024", "2048", "3096");
        combox_keySize.setItems(keySizeItems);
        combox_keySize.getSelectionModel().select(0);
    }

    @FXML
    private void onClear() {
        t_left.clear();
        t_right.clear();
    }

    @FXML
    private void onEncode() {
        Encoding encoding = comboBox_encoding.getSelectionModel().getSelectedItem();

        if (radioButton_encodeByPrivate.isSelected()) {
            PrivateKey privateKey = getPrivateKey(encoding);
            /** 私钥加密公钥解密 */
            if (privateKey != null) {
                /** get data */
                String data = t_left.getText().trim();
                if (StringUtils.isEmpty(data)) {
                    return;
                }
                /** encode and present results */
                try {
                    String cipherKey = CipherAlgorithm.get(Function.RSA);
                    byte[] encoded = RSACipher.getInstance().encrypt(cipherKey, privateKey, data.getBytes(encoding.value()));
                    t_right.setText(Hex.encodeHexString(encoded));
                } catch (Exception e) {
                    logger.error("私钥加密报文错误", e);
                    DialogUtil.showErrorAlert("错误", "私钥加密报文错误: [" + e + "]");
                    return;
                }
            }
        } else if (radioButton_encodeByPublic.isSelected()){
            PublicKey publicKey = getPublicKey(encoding);
            /** 公钥加密私钥解密*/
            if (publicKey != null) {
                /** get data */
                String data = t_left.getText().trim();
                if (StringUtils.isEmpty(data)) {
                    return;
                }
                /** encode and present results */
                try {
                    String cipherKey = CipherAlgorithm.get(Function.RSA);
                    byte[] encoded = RSACipher.getInstance().encrypt(cipherKey, publicKey, data.getBytes(encoding.value()));
                    t_right.setText(Hex.encodeHexString(encoded));
                } catch (Exception e) {
                    logger.error("公钥加密报文错误", e);
                    DialogUtil.showErrorAlert("错误", "公钥加密报文错误: [" + e + "]");
                    return;
                }
            }
        }
    }

    @FXML
    private void onDecode() {
        Encoding encoding = comboBox_encoding.getSelectionModel().getSelectedItem();

        if (radioButton_encodeByPrivate.isSelected()) {
            PublicKey publicKey = getPublicKey(encoding);
            /** 私钥加密公钥解密 */
            if (publicKey != null) {
                /** get sign */
                String hexSign = t_right.getText().trim();
                if (StringUtils.isEmpty(hexSign)) {
                    return;
                }
                /** decode and present results */
                try {
                    String cipherKey = CipherAlgorithm.get(Function.RSA);
                    byte[] decoded = RSACipher.getInstance().decrypt(cipherKey, publicKey, Hex.decodeHex(hexSign.toCharArray()));
                    t_left.setText(new String(decoded, encoding.value()));
                } catch (Exception e) {
                    logger.error("公钥解密报文错误", e);
                    DialogUtil.showErrorAlert("错误", "公钥解密报文错误: [" + e + "]");
                    return;
                }
            }
        } else if (radioButton_encodeByPublic.isSelected()){
            PrivateKey privateKey = getPrivateKey(encoding);
            /** 公钥加密私钥解密*/
            if (privateKey != null) {
                /** get sign */
                String hexSign = t_right.getText().trim();
                if (StringUtils.isEmpty(hexSign)) {
                    return;
                }
                /** decode and present results */
                try {
                    String cipherKey = CipherAlgorithm.get(Function.RSA);
                    byte[] decoded = RSACipher.getInstance().decrypt(cipherKey, privateKey, Hex.decodeHex(hexSign.toCharArray()));
                    t_left.setText(new String(decoded, encoding.value()));
                } catch (Exception e) {
                    logger.error("私钥解密报文错误", e);
                    DialogUtil.showErrorAlert("错误", "私钥解密报文错误: [" + e + "]");
                    return;
                }
            }
        }
    }


    @FXML
    private void onGenerateKeyPair() {
        String keySizeInput = combox_keySize.getSelectionModel().getSelectedItem();
        if (!NumberUtils.isNumber(keySizeInput) || Integer.parseInt(keySizeInput) <=0 ) {
            DialogUtil.showErrorAlert("输入错误", "KeySize输入不合法");
            return;
        }
        int keySize = Integer.parseInt(keySizeInput);
        KeyPair keyPair = null;
        try {
            keyPair = RSAKeyUtil.generateKeyPair(keySize);
        } catch (Exception e) {
            logger.error("生成秘钥对错误", e);
            DialogUtil.showErrorAlert("错误", "生成秘钥对错误: [" + e + "]");
            return;
        }

        if (keyPair != null) {
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            t_privateKey.setText(Base64.encodeBase64String(privateKey.getEncoded()));
            t_publicKey.setText(Base64.encodeBase64String(publicKey.getEncoded()));
        }
    }


    @FXML
    private void onPrivateKeyFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择私钥文件");
        File file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        try {
            PrivateKey privateKey = RSAKeyUtil.getPrivateKeyFromPemFile(file.getAbsolutePath());
            if (privateKey != null) {
                t_privateKey.setText(Base64.encodeBase64String(privateKey.getEncoded()));
            }
        } catch (Exception e) {
            logger.error("加载私钥失败", e);
            DialogUtil.showErrorAlert("错误", "无法加载私钥文件: [" + e + "]");
            return;
        }
    }

    @FXML
    private void onPublicKeyFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择公钥文件");
        File file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        try {
            PublicKey publicKey = RSAKeyUtil.getPublicKeyFromPemFile(file.getAbsolutePath());
            if (publicKey != null) {
                t_publicKey.setText(Base64.encodeBase64String(publicKey.getEncoded()));
            }
        } catch (Exception e) {
            logger.error("加载公钥失败", e);
            DialogUtil.showErrorAlert("错误", "无法加载公钥文件: [" + e + "]");
            return;
        }
    }


    private String lastDirectoryPath;

    /**
     * TODO: saveKeyAsPem本身不强制秘钥格式, 来者即存, 但函数中的description写死了,因此暂时只支持PKCS8的格式
     * TODO: 如果是其他格式,会导致BEGIN部分的标识与key本身的内容不一致的问题
     */
    @FXML
    private void onSaveKeys() {
        String privateText = t_privateKey.getText().trim();
        String publicText = t_publicKey.getText().trim();

        if (StringUtils.isEmpty(privateText) || StringUtils.isEmpty(publicText)) {
            DialogUtil.showErrorAlert("错误", "公私钥为空! 请输入或生成公私钥");
            return;
        }


        if (lastDirectoryPath == null) {
            lastDirectoryPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择公私钥存放路径");
        File defaultDirectory = new File(lastDirectoryPath);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(new Stage());

        if (selectedDirectory == null) {
            return;
        }

        String directory = selectedDirectory.getAbsolutePath();
        String privatePath = FilenameUtils.concat(directory, "rsa_private.key");
        String publicPath = FilenameUtils.concat(directory, "rsa_public.key");
        lastDirectoryPath = directory;

        if (new File(privatePath).exists() || new File(publicPath).exists()) {
            boolean overwrite = DialogUtil.showConformation("确认", "已经存在公私钥, 是否覆盖?");
            if (!overwrite) {
                return;
            }
        }

        if (!StringUtils.isEmpty(privateText)) {
            try {
                RSAKeyUtil.saveKeyAsPem("PRIVATE KEY", Base64.decodeBase64(privateText), privatePath);
            } catch (Exception e) {
                logger.error("无法存储私钥", e);
                DialogUtil.showErrorAlert("错误", "无法存储私钥");
                return;
            }
        }

        if (!StringUtils.isEmpty(publicText)) {
            try {
                RSAKeyUtil.saveKeyAsPem("PUBLIC KEY", Base64.decodeBase64(publicText), publicPath);
            } catch (Exception e) {
                logger.error("无法存储公钥", e);
                DialogUtil.showErrorAlert("错误", "无法存储公钥");
                return;
            }
        }

        if (!StringUtils.isEmpty(publicText) || !StringUtils.isEmpty(publicText)) {
            DialogUtil.showInfo("提示", "公私钥已存储, 存储目录: " + directory);
        } else {
            DialogUtil.showErrorAlert("提示", "请输入公私钥");
        }
    }



    private PrivateKey getPrivateKey(Encoding encoding) {
        String privateKeyBase64 = t_privateKey.getText().trim();
        if (StringUtils.isEmpty(privateKeyBase64)) {
            return null;
        }
        PrivateKey privateKey;
        try {
            privateKey = RSAKeyUtil.getPrivateKey(Base64.decodeBase64(privateKeyBase64));
            return privateKey;
        } catch (Exception e) {
            logger.error("解析私钥错误", e);
            DialogUtil.showErrorAlert("错误", "解析私钥错误: [" + e + "]");
            return null;
        }
    }

    private PublicKey getPublicKey(Encoding encoding) {
        String publicKeyBase64 = t_publicKey.getText().trim();
        if (StringUtils.isEmpty(publicKeyBase64)) {
            return null;
        }
        PublicKey publicKey ;
        try {
            publicKey = RSAKeyUtil.getPublicKey(Base64.decodeBase64(publicKeyBase64));
            return publicKey;
        } catch (Exception e) {
            logger.error("解析公钥错误", e);
            DialogUtil.showErrorAlert("错误", "解析公钥错误: [" + e + "]");
            return null;
        }
    }
}
