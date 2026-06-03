package frc.robot.bindings;



import com.stzteam.mars.models.containers.Binding;
import com.stzteam.mars.operator.ControllerOI;

import edu.wpi.first.math.geometry.Rotation2d;

import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmRequest;
import frc.robot.subsystems.conveyor.Conveyor;
import frc.robot.subsystems.conveyor.ConveyorRequest;

public class OperatorBindings implements Binding {

    private final ControllerOI operator;
    private final Conveyor conveyor;
    private final Arm arm;

    private OperatorBindings(ControllerOI operator, Conveyor conveyor, Arm arm) {
        this.operator = operator;
        this.conveyor = conveyor;
        this.arm = arm;
    }

    public static OperatorBindings create(ControllerOI operator, Conveyor conveyor, Arm arm) {
        return new OperatorBindings(operator, conveyor, arm);
    }

    @Override
    public void bind() {
        var buttons = operator.getActionButtons();   
        var pov     = operator.getDPadTriggers();     

        buttons.left().whileTrue(   
            conveyor.setControl(() -> new ConveyorRequest.Feed().withReverse(false)));
        buttons.top().whileTrue(    
            conveyor.setControl(() -> new ConveyorRequest.Feed().withReverse(true)));

        pov.up().onTrue(
            arm.setControl(() -> new ArmRequest.ToAngle().withAngle(Rotation2d.fromDegrees(90))));
        pov.down().onTrue(
            arm.setControl(() -> new ArmRequest.ToAngle().withAngle(Rotation2d.fromDegrees(0))));
    }
}