package frc.robot.subsystems.arm;

import com.stzteam.features.marsprocessor.Fallback;
import com.stzteam.mars.models.singlemodule.Data;
import com.stzteam.mars.models.singlemodule.IO;

import edu.wpi.first.math.geometry.Rotation2d;


@Fallback
public interface ArmIO extends IO<ArmIO.ArmInputs> {
    
     class ArmInputs extends Data<ArmInputs> {
          public Rotation2d angle       = new Rotation2d();
          public Rotation2d targetAngle = new Rotation2d();
          public double appliedVolts    = 0.0;
          public double currentAmps     = 0.0;
     }
    
     public void setPosition(Rotation2d angle);   
     public void setVoltage(double volts);
     public void stop();
}
