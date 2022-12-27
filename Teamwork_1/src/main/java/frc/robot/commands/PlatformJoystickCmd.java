
package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.math.filter.SlewRateLimiter;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.PlatformConstants;
import frc.robot.GamepadJoystick;
import frc.robot.subsystems.PlatformSubsystem;

public class PlatformJoystickCmd extends CommandBase {
    private final PlatformSubsystem platformSubsystem;
    private final Supplier<Boolean> PullButton; // 順轉
    private final Supplier<Boolean> PushButton; // 逆轉


    public PlatformJoystickCmd(PlatformSubsystem platformSubsystem, Supplier<Boolean> PullButton, Supplier<Boolean> PushButton) {
        this.platformSubsystem = platformSubsystem;
        this.PullButton = PullButton;
        this.PushButton = PushButton;
        addRequirements(platformSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // 1. Get real-time joystick inputs
        boolean Pull = PullButton.get();
        boolean Push = PushButton.get();

        // 2. Apply deadband

        if (Pull == true) {
            platformSubsystem.runModules(true, false); // 順轉
        } else if (Push == true) {
            platformSubsystem.runModules(true, true); // 逆轉
        } else {
            platformSubsystem.runModules(false, false); // 停止
        }
    }

    @Override
    public void end(boolean interrupted) {
        platformSubsystem.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
