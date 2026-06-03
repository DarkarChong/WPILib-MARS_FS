package frc.tests;

import com.stzteam.mars.test.MARSTest;
import com.stzteam.mars.test.TestRoutine;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmRequestFactory;

@MARSTest(name = "Arm Test")
public class ArmTest extends TestRoutine{
    private final Arm a;

    public ArmTest(Arm arm) {
        this.a = arm;
    }

    @Override
    public Command getRoutineCommand() {
        return Commands.sequence(
            run(
                ArmRequestFactory.toAngle()
                .withAngle(Rotation2d.fromDegrees(90))
                .withTolerance(2)
                ,
            a),
            waitFor(()-> a.isAtTarget(90), 2),
            delay(1),
            run(ArmRequestFactory.idle(), a));
    }
}
