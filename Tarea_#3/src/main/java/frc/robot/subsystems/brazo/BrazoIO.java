package frc.robot.subsystems.brazo;

public interface BrazoIO {

    class BrazoIOInputs {
        public double currentAngleDeg = 0.0;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(BrazoIOInputs inputs) {}

    default void setVoltage(double volts) {}

    default void stop() {}
}
