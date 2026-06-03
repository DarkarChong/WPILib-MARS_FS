package frc.robot.subsystems.conveyor;

import edu.wpi.first.math.MathUtil;

public class ConveyorIOSim implements ConveyorIO {

    private double appliedVolts = 0.0;

    @Override
    public void updateInputs(ConveyorInputs inputs) {
        appliedVolts = MathUtil.clamp(appliedVolts, -12.0, 12.0);
        inputs.appliedVolts = appliedVolts;
        inputs.velocityRPM  = appliedVolts * 500.0;   
    }

    @Override
    public void setSpeed(double dutyCycle) {
        appliedVolts = dutyCycle * 12.0;             
    }

    @Override
    public void stop() {
        appliedVolts = 0.0;
    }
}