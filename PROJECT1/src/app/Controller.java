package app;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import javafx.event.EventHandler;

import javafx.scene.input.MouseButton;


public class Controller {
    static double initialX;
    static double initialY;


    public static void addDraggableNode(final Node node) {
        node.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    node.getScene().getWindow().setX(me.getScreenX() - initialX);
                    node.getScene().getWindow().setY(me.getScreenY() - initialY);
                }
            }
        });
    }
}
