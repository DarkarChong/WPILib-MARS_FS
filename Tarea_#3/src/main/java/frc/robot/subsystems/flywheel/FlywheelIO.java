package frc.robot.subsystems.flywheel;

public interface FlywheelIO {

    class FlywheelIOInputs {
        public double velocityRPS = 0.0;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(FlywheelIOInputs inputs) {}

    default void setVoltage(double volts) {}

    default void stop() {}
}
