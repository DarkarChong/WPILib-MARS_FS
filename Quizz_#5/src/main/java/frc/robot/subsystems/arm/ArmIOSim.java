package frc.robot.subsystems.arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class ArmIOSim implements ArmIO {

    private static final double kGearRatio    = 1.0;
    private static final double kLengthMeters = 1.0;   
    private static final double kMassKg       = 0.5;   
    private static final double kMinAngleRad  = Units.degreesToRadians(0);
    private static final double kMaxAngleRad  = Units.degreesToRadians(90);

    private final SingleJointedArmSim sim = new SingleJointedArmSim(
        DCMotor.getNEO(1),
        kGearRatio,
        SingleJointedArmSim.estimateMOI(kLengthMeters, kMassKg),
        kLengthMeters,
        kMinAngleRad,
        kMaxAngleRad,
        true,            
        kMinAngleRad);   

    private final PIDController controller = new PIDController(0.5, 0.0, 0.0); 

    private double appliedVolts = 0.0;
    private boolean closedLoop  = false;

    private final Mechanism2d canvas = new Mechanism2d(4, 4);
    private final MechanismRoot2d root = canvas.getRoot("ArmRoot", 2, 2);
    private final MechanismLigament2d ligament = root.append(
        new MechanismLigament2d("Arm", 1.0, 0.0, 6, new Color8Bit(Color.kAqua)));

    public ArmIOSim() {
        SmartDashboard.putData("Arm/Mechanism2d", canvas);  
    }

    @Override
    public void updateInputs(ArmInputs inputs) {
        if (closedLoop) {
            appliedVolts = controller.calculate(sim.getAngleRads());  
        }
        appliedVolts = MathUtil.clamp(appliedVolts, -12.0, 12.0);
        sim.setInputVoltage(appliedVolts);
        sim.update(0.02);

        inputs.angle        = Rotation2d.fromRadians(sim.getAngleRads());      
        inputs.targetAngle  = Rotation2d.fromRadians(controller.getSetpoint());
        inputs.appliedVolts = appliedVolts;
        inputs.currentAmps  = sim.getCurrentDrawAmps();

        ligament.setAngle(inputs.angle.getDegrees());   
    }

    @Override
    public void setPosition(Rotation2d angle) {
        closedLoop = true;
        controller.setSetpoint(angle.getRadians());     
    }

    @Override
    public void setVoltage(double volts) {
        closedLoop = false;
        appliedVolts = volts;
    }

    @Override
    public void stop() {
        closedLoop = false;
        appliedVolts = 0.0;
    }
}