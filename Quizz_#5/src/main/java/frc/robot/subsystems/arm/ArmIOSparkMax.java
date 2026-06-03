package frc.robot.subsystems.arm;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation2d;

public class ArmIOSparkMax implements ArmIO {

    private static final double kGearRatio = 1.0;   

    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;
    private final SparkClosedLoopController m_pid;

    public ArmIOSparkMax(int id) {
        m_motor   = new SparkMax(id, MotorType.kBrushless);   
        m_encoder = m_motor.getEncoder();
        m_pid     = m_motor.getClosedLoopController();

        SparkMaxConfig config = new SparkMaxConfig();

        config.encoder.positionConversionFactor(1.0 / kGearRatio);

        config.closedLoop.pid(0.05, 0.0, 0.0001);

        config.softLimit
              .forwardSoftLimit(90.0 / 360.0).forwardSoftLimitEnabled(true)
              .reverseSoftLimit(0.0).reverseSoftLimitEnabled(true);

        m_motor.configure(config, ResetMode.kResetSafeParameters,
                                  PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs(ArmInputs inputs) {
        inputs.angle        = Rotation2d.fromRotations(m_encoder.getPosition());
        inputs.appliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
        inputs.currentAmps  = m_motor.getOutputCurrent();
    }

    @Override
    public void setPosition(Rotation2d angle) {
        m_pid.setReference(angle.getRotations(), ControlType.kPosition); 
    }

    @Override
    public void setVoltage(double volts) {
        m_motor.setVoltage(volts);
    }

    @Override
    public void stop() {
        m_motor.stopMotor();
    }
}