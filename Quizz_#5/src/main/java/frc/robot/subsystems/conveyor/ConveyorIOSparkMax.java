package frc.robot.subsystems.conveyor;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ConveyorIOSparkMax implements ConveyorIO {

    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;

    public ConveyorIOSparkMax(int id) {
        m_motor   = new SparkMax(id, MotorType.kBrushless);
        m_encoder = m_motor.getEncoder();

        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kCoast);             
        m_motor.configure(config, ResetMode.kResetSafeParameters,
                                  PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs(ConveyorInputs inputs) {
        inputs.velocityRPM  = m_encoder.getVelocity();   // RPM 
        inputs.appliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
    }

    @Override
    public void setSpeed(double dutyCycle) {
        m_motor.set(dutyCycle);
    }

    @Override
    public void stop() {
        m_motor.stopMotor();
    }
}