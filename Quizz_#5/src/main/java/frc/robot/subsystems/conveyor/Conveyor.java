package frc.robot.subsystems.conveyor;

import java.util.function.Supplier;

import com.stzteam.mars.diagnostics.ModuleColorCode;
import com.stzteam.mars.diagnostics.StatusColorCode.Severity;
import com.stzteam.mars.models.SubsystemBuilder;
import com.stzteam.mars.models.singlemodule.ModularSubsystem;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.configuration.KeyManager;
import frc.robot.subsystems.conveyor.ConveyorIO.ConveyorInputs;

public class Conveyor extends ModularSubsystem<ConveyorInputs, ConveyorIO>
        implements ConveyorCommands {                       

    public static final ModuleColorCode IDLE =
        ModuleColorCode.solid("IDLE", Severity.OK, Color.kDarkGreen, "Reposo");
    public static final ModuleColorCode FEEDING =
        ModuleColorCode.solid("FEEDING", Severity.OK, Color.kGreen, "Avanzando");
    public static final ModuleColorCode REJECTING =
        ModuleColorCode.solid("REJECTING", Severity.WARNING, Color.kOrange, "Reversa");

    public Conveyor(ConveyorIO io) {
        super(SubsystemBuilder.<ConveyorInputs, ConveyorIO>setup()
            .key(KeyManager.CONVEYOR_KEY)
            .hardware(io, new ConveyorInputs())
            .request(new ConveyorRequest.Idle())
            );

        setDefaultCommand(runRequest(() -> new ConveyorRequest.Idle()));
    }

    @Override
    public ConveyorInputs getState() {
        return inputs;  
    }

    @Override
    public Command setControl(Supplier<ConveyorRequest> request) {
        return runRequest(request);
    }

    @Override
    public void absolutePeriodic(ConveyorInputs data) {}

    @Override
    public void simulationPeriodic() {}

    
}