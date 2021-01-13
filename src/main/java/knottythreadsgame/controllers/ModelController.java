package knottythreadsgame.controllers;

import knottythreadsgame.model.GameModel;

import java.awt.geom.Point2D;

public class ModelController {
    private GameModel model;

    public ModelController(GameModel model) {
        this.model = model;
    }

    // ------------ Обработка действий пользователя мышью  ------------
    /**
     * Взять выбранный по координате узел
     *
     * @param position
     */
    public boolean takeKnot(Point2D.Double position) {
        return model.getSchema().getSelectedKnot(position);
    }

    /**
     * Перетаскивать выбранный узел в пределах поля
     *
     * @param position
     */
    public boolean observeKnotDragging(Point2D.Double position) {
        return model.getSchema().dragSelectedKnot(position);
    }

    /**
     * Отпустить узел в заданной позиции
     *
     */
    public void releaseKnot() {
        model.getSchema().releaseSelectedKnot();
    }
}
