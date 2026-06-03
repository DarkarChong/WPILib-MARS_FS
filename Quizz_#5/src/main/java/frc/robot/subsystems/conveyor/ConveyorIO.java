package frc.robot.subsystems.conveyor;

import com.stzteam.features.marsprocessor.Fallback;
import com.stzteam.mars.models.singlemodule.Data;
import com.stzteam.mars.models.singlemodule.IO;

@Fallback
public interface ConveyorIO extends IO<ConveyorIO.ConveyorInputs> {
    
     class ConveyorInputs extends Data<ConveyorInputs> {
        public double appliedVolts = 0.0;
        public double velocityRPM  = 0.0;
       
    }

    public void setSpeed(double dutyCycle);
    public void stop();
    
    
}
