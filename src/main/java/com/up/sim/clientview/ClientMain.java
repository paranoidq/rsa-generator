package com.up.sim.clientview;

import com.up.sim.clientview.utils.DialogUtil;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientMain extends Application {
    private static Logger logger = Logger.getLogger(ClientMain.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            showSecurityMain();
            logger.info("启动软件成功");
        } catch (Exception e) { // TODO
            logger.error("在目录XXX中找不到运行时配置文件.", e);
        }
    }

    private BorderPane securityToolLayout;
    private Stage securityMainStage;
    private SecurityController securityController;
    private Map<String, AnchorPane> toolPanes = new HashMap<String, AnchorPane>();

    public void showSecurityMain() {
        if (securityMainStage != null) {
            securityMainStage.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientMain.class.getResource("SecurityLayout.fxml"));
            try {
                securityToolLayout = loader.load();
                securityMainStage = new Stage();
                securityMainStage.setResizable(true);
                securityMainStage.setTitle("工具");
                securityMainStage.initModality(Modality.NONE);
                securityMainStage.setAlwaysOnTop(false);
                securityMainStage.setScene(new Scene(securityToolLayout));
                securityMainStage.setWidth(950);
                securityMainStage.setHeight(579);
                securityMainStage.setMinWidth(950);
                securityMainStage.setMinHeight(579);

                securityMainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        securityMainStage.hide();
                    }
                });

                securityController = loader.getController();
                securityController.linkMain(this);
                securityController.initView();

                securityMainStage.showAndWait();
            } catch (IOException e) {
                logger.error("无法加载工具窗口", e);
                DialogUtil.showErrorAlert("加载错误", "无法加载工具窗口");
            }
        }
    }

    public void loadSecurityTool(String fxmlPath) {
        securityToolLayout.setCenter(null);

        if (!toolPanes.containsKey(fxmlPath)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientMain.class.getResource(fxmlPath));
            try {
                AnchorPane toolLayout = loader.load();
                securityToolLayout.setCenter(toolLayout);

                toolPanes.put(fxmlPath, toolLayout);
                toolLayout.visibleProperty().set(true);
                toolLayout.setManaged(true);
            } catch (IOException e) {
                logger.error("无法加载工具界面", e);
            }
        } else {
            securityToolLayout.setCenter(toolPanes.get(fxmlPath));
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}
