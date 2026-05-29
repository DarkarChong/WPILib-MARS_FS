package frc.robot.subsystems.brazo;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class BrazoIOTalonFX implements BrazoIO {

    private static final double ROTATIONS_TO_DEGREES = 360.0;

    private final TalonFX m_motor;

    public BrazoIOTalonFX(int motorId) {
        m_motor = new TalonFX(motorId);

        TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();
        talonFXConfigs.Feedback.SensorToMechanismRatio = 75.0;
        m_motor.getConfigurator().apply(talonFXConfigs);
    }

    @Override
    public void updateInputs(BrazoIOInputs inputs) {
        inputs.currentAngleDeg = m_motor.getPosition().getValueAsDouble() * ROTATIONS_TO_DEGREES;
        inputs.appliedVolts = m_motor.getMotorVoltage().getValueAsDouble();
        inputs.currentAmps = m_motor.getStatorCurrent().getValueAsDouble();
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
