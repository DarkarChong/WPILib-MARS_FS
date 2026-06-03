package frc.robot.configuration;

import com.stzteam.mars.builder.Environment;
import com.stzteam.mars.builder.Environment.RunMode;
import com.stzteam.mars.builder.Injector;
import com.stzteam.mars.operator.ControllerOI;
import com.stzteam.mars.operator.PS5OI;
import com.stzteam.mars.operator.XboxOI;

import frc.robot.subsystems.conveyor.Conveyor;
import frc.robot.subsystems.conveyor.ConveyorIO;
import frc.robot.subsystems.conveyor.ConveyorIOFallback;
import frc.robot.subsystems.conveyor.ConveyorIOSim;
import frc.robot.subsystems.conveyor.ConveyorIOSparkMax;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmIO;
import frc.robot.subsystems.arm.ArmIOFallback;
import frc.robot.subsystems.arm.ArmIOSim;
import frc.robot.subsystems.arm.ArmIOSparkMax;

public class Manifest {

    public static final RunMode CURRENT_MODE = RunMode.SIM;
    static { Environment.setMode(CURRENT_MODE); }

    public static final boolean HAS_CONVEYOR = true;
    public static final boolean HAS_ARM      = true;

    private static final int CONVEYOR_MOTOR_ID = 10;
    private static final int ARM_MOTOR_ID      = 11;

    public enum ControllerType { PS5, XBOX }
    public static final ControllerType OPERATOR_CONTROLLER = ControllerType.XBOX;
    private static final int OPERATOR_PORT = 1;

    public static Conveyor buildConveyor() {
        ConveyorIO io = Injector.createIO(HAS_CONVEYOR,
            ConveyorIOFallback::new,
            () -> new ConveyorIOSparkMax(CONVEYOR_MOTOR_ID),
            ConveyorIOSim::new);
        return new Conveyor(io);
    }

    public static Arm buildArm() {
        ArmIO io = Injector.createIO(HAS_ARM,
            ArmIOFallback::new,
            () -> new ArmIOSparkMax(ARM_MOTOR_ID),
            ArmIOSim::new);
        return new Arm(io);
    }

    public static class ControlsBuilder {
        public static ControllerOI buildOperator() {
            return OPERATOR_CONTROLLER == ControllerType.PS5
                ? new PS5OI(OPERATOR_PORT)
                : new XboxOI(OPERATOR_PORT);
        }
    }
}