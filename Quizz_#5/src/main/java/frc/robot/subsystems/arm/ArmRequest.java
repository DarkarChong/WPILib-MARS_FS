package frc.robot.subsystems.arm;



import com.stzteam.features.marsprocessor.CreateCommand;
import com.stzteam.features.marsprocessor.RequestFactory;
import com.stzteam.mars.diagnostics.ActionStatus;
import com.stzteam.mars.requests.Request;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.arm.ArmIO.ArmInputs;

@RequestFactory
public interface ArmRequest extends Request<ArmInputs, ArmIO> {

    @CreateCommand(name = "stop")
    class Idle implements ArmRequest {
        @Override
        public ActionStatus apply(ArmInputs data, ArmIO actor) {
            actor.stop();
            data.targetAngle = data.angle;
            return ActionStatus.of(Arm.IDLE, "Idle");
        }
    }

    @CreateCommand(name = "toAngle")
    class ToAngle implements ArmRequest {
        private Rotation2d target = new Rotation2d();
        private double toleranceDeg = 2.0;

        public ToAngle withAngle(Rotation2d angle) {
            this.target = angle;
            return this;
        }

        public ToAngle withTolerance(double toleranceDegrees) {
            this.toleranceDeg = toleranceDegrees;
            return this;
        }

        @Override
        public ActionStatus apply(ArmInputs data, ArmIO actor) {
            data.targetAngle = target;
            actor.setPosition(target);

            boolean reached =
                MathUtil.isNear(target.getDegrees(), data.angle.getDegrees(), toleranceDeg);

            return reached
                ? ActionStatus.of(Arm.AT_SETPOINT, "En objetivo")
                : ActionStatus.of(Arm.MOVING,
                      "Moviendo a " + Math.round(target.getDegrees()) + "°");
        }
    }
}