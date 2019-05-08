/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.loadflow.simple.ac;

import com.powsybl.iidm.network.Bus;
import com.powsybl.loadflow.simple.equations.EquationContext;
import com.powsybl.loadflow.simple.equations.EquationType;
import com.powsybl.loadflow.simple.equations.Variable;
import com.powsybl.loadflow.simple.equations.VariableType;
import com.powsybl.loadflow.simple.network.BranchCharacteristics;

import java.util.Objects;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
public class OpenBranchSide1ReactiveFlowEquationTerm extends AbstractOpenBranchAcEquationTerm {

    private final OpenBranchSide1AcContext branchContext;

    public OpenBranchSide1ReactiveFlowEquationTerm(OpenBranchSide1AcContext branchContext, Bus bus2, EquationContext equationContext) {
        super(EquationType.BUS_Q, VariableType.BUS_V, bus2, equationContext);
        this.branchContext = Objects.requireNonNull(branchContext);
    }

    @Override
    public double eval(double[] x) {
        return branchContext.q2(x);
    }

    @Override
    public double der(Variable variable, double[] x) {
        if (variable.equals(branchContext.getV2Var())) {
            BranchCharacteristics bc = branchContext.getBc();
            double v2 = x[branchContext.getV2Var().getColumn()];
            return -2 * v2 * bc.r2() * bc.r2() * (bc.b2() + bc.y() * bc.y() * bc.b1() / branchContext.getShunt()
                    - (bc.b1() * bc.b1() + bc.g1() * bc.g1()) * bc.y() * Math.cos(bc.ksi()) / branchContext.getShunt());
        } else {
            throw new IllegalStateException("Unknown variable: " + variable);
        }
    }
}