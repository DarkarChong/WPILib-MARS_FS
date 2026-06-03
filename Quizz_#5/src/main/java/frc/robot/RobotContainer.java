// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.stzteam.mars.models.containers.IRobotContainer;
import com.stzteam.mars.operator.ControllerOI;
import com.stzteam.mars.test.TestRoutine;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import frc.robot.bindings.OperatorBindings;
import frc.robot.bindings.TestBindings;
import frc.robot.configuration.Manifest;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.conveyor.Conveyor;
import frc.tests.EmptyTest;

public class RobotContainer implements IRobotContainer {

    public final ControllerOI operator;
    public final Conveyor conveyor;
    public final Arm arm;
    public final TestBindings test;

    public RobotContainer() {
        this.conveyor = Manifest.buildConveyor();
        this.arm      = Manifest.buildArm();
        this.operator = Manifest.ControlsBuilder.buildOperator();
        this.test = TestBindings.create(arm);
        test.bind();

        OperatorBindings.create(operator, conveyor, arm).bind();
    }

    @Override
    public void updateNodes() {}

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    @Override
    public TestRoutine getTestRoutine() {
        return test.getSelectedTest();
    }
}