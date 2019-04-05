/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.substationdiagram.svg;

import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import com.powsybl.substationdiagram.model.Edge;
import com.powsybl.substationdiagram.model.Graph;
import com.powsybl.substationdiagram.model.Node;

import static com.powsybl.substationdiagram.svg.SubstationDiagramStyles.*;

/**
 * @author Giovanni Ferrari <giovanni.ferrari at techrain.eu>
 */
public class DefaultSubstationDiagramStyleProvider implements SubstationDiagramStyleProvider {

    @Override
    public Optional<String> getGlobalStyle(Graph graph) {
        StringBuilder style = new StringBuilder();
        style.append(".").append(SUBSTATION_STYLE_CLASS).append(" {fill:rgb(255,255,255);stroke-width:1;fill-opacity:0;}");
        style.append(".").append(WIRE_STYLE_CLASS).append(" {stroke:rgb(200,0,0);stroke-width:1;}");
        style.append(".").append(GRID_STYLE_CLASS).append(" {stroke:rgb(0,55,0);stroke-width:1;stroke-dasharray:1,10;}");
        style.append(".").append(BUS_STYLE_CLASS).append(" {stroke:rgb(0,0,0);stroke-width:3;}");
        style.append(".").append(LABEL_STYLE_CLASS).append(" {fill: black;color:black;stroke:none;fill-opacity:1;}");
        return Optional.of(style.toString());
    }

    @Override
    public Optional<String> getNodeStyle(Node node) {
        Objects.requireNonNull(node);
        if (node.getType() == Node.NodeType.SWITCH) {
            try {
                StringBuilder style = new StringBuilder();
                style.append(".").append(escapeClassName(URLEncoder.encode(node.getId(), StandardCharsets.UTF_8.name())))
                        .append(" .open { visibility: ").append(node.isOpen() ? "visible;" : "hidden;}");

                style.append(".").append(escapeClassName(URLEncoder.encode(node.getId(), StandardCharsets.UTF_8.name())))
                        .append(" .closed { visibility: ").append(node.isOpen() ? "hidden;" : "visible;}");

                return Optional.of(style.toString());
            } catch (UnsupportedEncodingException e) {
                throw new UncheckedIOException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getWireStyle(Edge edge) {
        return  Optional.empty();
    }

}