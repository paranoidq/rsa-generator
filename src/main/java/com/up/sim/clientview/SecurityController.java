package com.up.sim.clientview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author paranoidq
 * @since 0.1
 */
public class SecurityController implements Initializable {

    @FXML
    private MenuButton menu_encodingTool;

    @FXML
    private MenuButton menu_cipherTool;

    @FXML
    private MenuItem menuItem_base64;

    @FXML
    private MenuItem menuItem_md5;

    @FXML
    private MenuItem menuItem_urlencode;

    @FXML
    private MenuItem menuItem_RSA;

    @FXML
    private MenuItem menuItem_DESede;

    @FXML
    private Label label_tool;


    private ClientMain main;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTools();
    }


    public void linkMain(ClientMain main) {
        this.main = main;
    }

    private static final String[] encodingToolIds = {"BASE64", "MD5", "URLEncode"};
    private static final Map<String, String> cipherToolIds = new HashMap<String, String>();
    static {
        cipherToolIds.put("RSA", "RSA");
    }



    @FXML
    private void initTools() {
        for (final String id : encodingToolIds) {
            MenuItem item = new MenuItem();
            item.setText(id);
            item.setId(id);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    main.loadSecurityTool(id + "Layout.fxml");
                    label_tool.setText(id);
//                    main.setToolTitle(id);
                }
            });
            menu_encodingTool.getItems().add(item);
        }

        for (final String id : cipherToolIds.keySet()) {
            MenuItem item = new MenuItem();
            item.setText(cipherToolIds.get(id));
            item.setId(id);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    main.loadSecurityTool(id + "Layout.fxml");
                    label_tool.setText(cipherToolIds.get(id));
                }
            });
            menu_cipherTool.getItems().add(item);
        }
    }

    public void initView() {
        main.loadSecurityTool(cipherToolIds.get("RSA")+ "Layout.fxml");
        label_tool.setText(cipherToolIds.get("RSA"));
    }

}
