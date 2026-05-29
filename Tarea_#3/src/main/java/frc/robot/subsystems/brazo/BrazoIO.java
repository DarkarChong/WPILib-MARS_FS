package frc.robot.subsystems.brazo;

import com.stzteam.mars.models.singlemodule.Data;
import com.stzteam.mars.models.singlemodule.IO;

public interface BrazoIO extends IO<BrazoIO.BrazoInputs> {

    class BrazoInputs extends Data<BrazoInputs> {
        public double currentAngleDeg = 0.0;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(BrazoInputs inputs) {}

    default void setVoltage(double volts) {}

    default void stop() {}
}
