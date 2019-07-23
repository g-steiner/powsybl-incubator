/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.substationdiagram.svg;

import java.util.Optional;

import com.powsybl.iidm.network.ThreeWindingsTransformer;
import com.powsybl.iidm.network.TwoWindingsTransformer;
import com.powsybl.substationdiagram.model.Edge;
import com.powsybl.substationdiagram.model.Feeder2WTNode;
import com.powsybl.substationdiagram.model.Fictitious3WTNode;
import com.powsybl.substationdiagram.model.Graph;
import com.powsybl.substationdiagram.model.Node;

/**
 * @author Giovanni Ferrari <giovanni.ferrari at techrain.eu>
 */
public interface SubstationDiagramStyleProvider {

    Optional<String> getGlobalStyle(Graph graph);

    Optional<String> getNodeStyle(Node node);

    Optional<String> getWireStyle(Edge edge);

    Optional<String> getNode3WTStyle(Fictitious3WTNode node, ThreeWindingsTransformer.Side side);

    Optional<String> getNode2WTStyle(Feeder2WTNode node, TwoWindingsTransformer.Side side);
}
