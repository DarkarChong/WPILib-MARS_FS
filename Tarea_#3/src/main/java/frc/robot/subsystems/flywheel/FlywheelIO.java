package frc.robot.subsystems.flywheel;

import com.stzteam.features.marsprocessor.Fallback;
import com.stzteam.mars.models.singlemodule.Data;
import com.stzteam.mars.models.singlemodule.IO;

@Fallback   
public interface FlywheelIO extends IO<FlywheelIO.FlywheelInputs> {

    class FlywheelInputs extends Data<FlywheelInputs> {
        public double velocityRPS = 0.0;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(FlywheelInputs inputs) {}

    
    default void setVoltage(double volts) {}

    default void stop() {}
}
