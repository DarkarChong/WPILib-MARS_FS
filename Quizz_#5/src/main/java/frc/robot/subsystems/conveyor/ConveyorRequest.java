package frc.robot.subsystems.conveyor;

import com.stzteam.features.marsprocessor.CreateCommand;
import com.stzteam.features.marsprocessor.RequestFactory;
import com.stzteam.mars.diagnostics.ActionStatus;
import com.stzteam.mars.requests.Request;

import frc.robot.subsystems.conveyor.ConveyorIO.ConveyorInputs;

@RequestFactory
public interface ConveyorRequest extends Request<ConveyorInputs, ConveyorIO> {

    @CreateCommand(name = "stop")
    class Idle implements ConveyorRequest {
        @Override
        public ActionStatus apply(ConveyorInputs data, ConveyorIO actor) {
            actor.stop();
            return ActionStatus.of(Conveyor.IDLE, "Idle");
        }
    }

    @CreateCommand(name = "feed")
    class Feed implements ConveyorRequest {
        private boolean reverse = false;

        public Feed withReverse(boolean reverse) {
            this.reverse = reverse;
            return this;
        }

        @Override
        public ActionStatus apply(ConveyorInputs data, ConveyorIO actor) {
            actor.setSpeed(reverse ? -0.8 : 0.8);
            return reverse
                ? ActionStatus.of(Conveyor.REJECTING, "Reversa")
                : ActionStatus.of(Conveyor.FEEDING,  "Avanzando");
        }
    }
}