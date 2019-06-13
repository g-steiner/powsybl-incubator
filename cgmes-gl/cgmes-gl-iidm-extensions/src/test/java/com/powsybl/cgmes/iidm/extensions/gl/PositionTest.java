/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.iidm.extensions.gl;

import com.google.common.collect.ImmutableList;
import com.powsybl.iidm.network.Line;
import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.network.Substation;
import org.junit.Test;

import static com.powsybl.cgmes.iidm.extensions.gl.GLTestUtils.*;

/**
 *
 * @author Massimo Ferraro <massimo.ferraro@techrain.eu>
 */
public class PositionTest {

    @Test
    public void test() {
        Network network = GLTestUtils.getNetwork();
        Substation substation1 = network.getSubstation("Substation1");
        SubstationPosition substationPosition1 = new SubstationPosition(substation1, SUBSTATION_1);
        substation1.addExtension(SubstationPosition.class, substationPosition1);

        Substation substation2 = network.getSubstation("Substation2");
        SubstationPosition substationPosition2 = new SubstationPosition(substation2, SUBSTATION_2);
        substation2.addExtension(SubstationPosition.class, substationPosition2);

        Line line = network.getLine("Line");
        line.addExtension(LinePosition.class, new LinePosition<>(line, ImmutableList.of(SUBSTATION_1, LINE_1, LINE_2, SUBSTATION_2)));

        GLTestUtils.checkNetwork(network);
    }

}
