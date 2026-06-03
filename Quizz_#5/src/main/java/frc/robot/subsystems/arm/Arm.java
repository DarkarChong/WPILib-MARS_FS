package frc.robot.subsystems.arm;

import java.util.function.Supplier;

import com.stzteam.forgemini.io.NetworkIO;
import com.stzteam.mars.diagnostics.ModuleColorCode;
import com.stzteam.mars.diagnostics.StatusColorCode.Severity;
import com.stzteam.mars.models.SubsystemBuilder;
import com.stzteam.mars.models.Telemetry;
import com.stzteam.mars.models.singlemodule.ModularSubsystem;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.configuration.KeyManager;
import frc.robot.subsystems.arm.ArmIO.ArmInputs;

public class Arm extends ModularSubsystem<ArmInputs, ArmIO>
        implements ArmCommands {                             

    public static final ModuleColorCode IDLE =
        ModuleColorCode.solid("IDLE", Severity.OK, Color.kDarkGreen, "Reposo");
    public static final ModuleColorCode MOVING =
        ModuleColorCode.solid("MOVING", Severity.WARNING, Color.kYellow, "Moviendo");
    public static final ModuleColorCode AT_SETPOINT =
        ModuleColorCode.solid("AT_SETPOINT", Severity.OK, Color.kFirstBlue, "En objetivo");

    public Arm(ArmIO io) {
        super(SubsystemBuilder.<ArmInputs, ArmIO>setup()
            .key(KeyManager.ARM_KEY)
            .hardware(io, new ArmInputs())
            .request(new ArmRequest.Idle())
            .telemetry(new ArmTelemetry()));

        setDefaultCommand(runRequest(() -> new ArmRequest.Idle()));
    }

    public boolean isAtTarget(double toleranceDegrees) {
        return MathUtil.isNear(
            inputs.targetAngle.getDegrees(),
            inputs.angle.getDegrees(),
            toleranceDegrees);
    }

    @Override
    public ArmInputs getState() {
        return inputs;
    }

    @Override
    public Command setControl(Supplier<ArmRequest> request) {
        return runRequest(request);
    }

    @Override
    public void absolutePeriodic(ArmInputs data) {}

    @Override
    public void simulationPeriodic() {}

    static class ArmTelemetry extends Telemetry<ArmInputs> {
        @Override
        public void telemeterize(ArmInputs d) {
            NetworkIO.set(KeyManager.ARM_KEY, "Angle",  d.angle.getDegrees());
            NetworkIO.set(KeyManager.ARM_KEY, "Target", d.targetAngle.getDegrees());
        }
    }
}