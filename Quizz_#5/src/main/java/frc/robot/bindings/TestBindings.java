package frc.robot.bindings;

import com.stzteam.forgemini.io.SmartChooser;
import com.stzteam.mars.models.containers.Binding;
import com.stzteam.mars.test.TestRoutine;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.conveyor.Conveyor;
import frc.tests.ArmTest;

public class TestBindings implements Binding {
    
    private SendableChooser<TestRoutine> tests = new SendableChooser<>();
    private Arm a;


    private TestBindings(Arm arm) {
        this.a = arm;
    }

    public static TestBindings create(Arm arm) {
        return new TestBindings(arm);
    }

    @Override
    public void bind() {
        tests.addOption("Arm Test", new ArmTest(a));

        SmartDashboard.putData("Test Routine", tests);
    }

    public TestRoutine getSelectedTest() {
        return tests.getSelected();
    }
}
