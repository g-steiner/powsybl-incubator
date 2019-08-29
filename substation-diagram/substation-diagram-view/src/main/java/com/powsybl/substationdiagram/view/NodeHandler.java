/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.substationdiagram.view;

import com.powsybl.substationdiagram.library.ComponentSize;
import com.powsybl.substationdiagram.library.ComponentType;
import com.powsybl.substationdiagram.model.BaseNode;
import com.powsybl.substationdiagram.model.BusCell;
import com.powsybl.substationdiagram.svg.GraphMetadata;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Benoit Jeanson <benoit.jeanson at rte-france.com>
 * @author Nicolas Duchene
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 * @author Franck Lecuyer <franck.lecuyer at rte-france.com>
 */
public class NodeHandler implements BaseNode {

    private final Node node;

    private ComponentType componentType;

    private Double rotationAngle;

    private final List<WireHandler> wireHandlers = new ArrayList<>();

    private final GraphMetadata metadata;

    private final String vId;

    private double mouseX;
    private double mouseY;

    private BusCell.Direction direction;

    public NodeHandler(Node node, ComponentType componentType, Double rotationAngle,
                       GraphMetadata metadata,
                       String vId, BusCell.Direction direction) {
        this.node = Objects.requireNonNull(node);
        this.componentType = componentType;
        this.rotationAngle = rotationAngle;
        this.metadata = Objects.requireNonNull(metadata);
        this.vId = Objects.requireNonNull(vId);
        this.direction = direction;

        setDragAndDrop();
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String getId() {
        return node.getId();
    }

    public String getVId() {
        return vId;
    }

    public BusCell.Direction getDirection() {
        return direction;
    }

    @Override
    public ComponentType getComponentType() {
        return componentType;
    }

    @Override
    public Double getRotationAngle() {
        return rotationAngle;
    }

    public void addWire(WireHandler w) {
        wireHandlers.add(w);
    }

    public List<WireHandler> getWireHandlers() {
        return wireHandlers;
    }

    @Override
    public boolean isRotated() {
        return rotationAngle != null;
    }

    @Override
    public double getX() {
        ComponentSize size = componentType != null
                ? metadata.getComponentMetadata(componentType).getSize()
                : new ComponentSize(0, 0);
        return node.localToParent(node.getLayoutX() + size.getWidth() / 2,
                                  node.getLayoutY() + size.getHeight() / 2).getX();
    }

    @Override
    public double getY() {
        ComponentSize size = componentType != null
                ? metadata.getComponentMetadata(componentType).getSize()
                : new ComponentSize(0, 0);
        return node.localToParent(node.getLayoutX() + size.getWidth() / 2,
                                  node.getLayoutY() + size.getHeight() / 2).getY();
    }

    public void setDragAndDrop() {
        node.setOnMousePressed(event -> {
            mouseX = event.getSceneX() - node.getTranslateX();
            mouseY = event.getSceneY() - node.getTranslateY();
            event.consume();
        });

        node.setOnMouseDragged(event -> {
            translate(event.getSceneX() - mouseX, event.getSceneY() - mouseY);
            event.consume();
        });
    }

    public void translate(double translateX, double translateY) {
        node.setTranslateX(translateX);
        node.setTranslateY(translateY);
        for (WireHandler w : wireHandlers) {
            w.refresh();
        }
    }
}
