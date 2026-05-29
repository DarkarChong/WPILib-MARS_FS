package frc.robot.subsystems.flywheel;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class FlywheelIOSparkMax implements FlywheelIO {

    private static final double VELOCITY_CONVERSION_FACTOR = 1.0 / 180.0; 

    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;

    public FlywheelIOSparkMax(int motorId) {
        m_motor = new SparkMax(motorId, MotorType.kBrushless);
        m_encoder = m_motor.getEncoder();

        SparkMaxConfig config = new SparkMaxConfig();
        config.encoder.velocityConversionFactor(VELOCITY_CONVERSION_FACTOR);

        m_motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void updateInputs(FlywheelInputs inputs) {
        inputs.velocityRPS = m_encoder.getVelocity();
        inputs.appliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
        inputs.currentAmps = m_motor.getOutputCurrent();
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
