
package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.math.filter.SlewRateLimiter;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.FixtureConstants;
import frc.robot.GamepadJoystick;
import frc.robot.subsystems.FixtureSubsystem;

public class FixtureJoystickCmd extends CommandBase {
    private final FixtureSubsystem fixtureSubsystem;
    private final Supplier<Double> ySpdFunction;
    private final Supplier<Boolean> PullButton; // 收方塊
    private final Supplier<Boolean> PushButton; // 吐方塊


    public FixtureJoystickCmd(FixtureSubsystem fixtureSubsystem, Supplier<Double> ySpdFunction, 
            Supplier<Boolean> PullButton, Supplier<Boolean> PushButton) {
        this.fixtureSubsystem = fixtureSubsystem;
        this.ySpdFunction = ySpdFunction;
        this.PullButton = PullButton;
        this.PushButton = PushButton;
        addRequirements(fixtureSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // 1. Get real-time joystick inputs
        double ySpeed = ySpdFunction.get(); // 得到 y 軸的即時值
        boolean Pull = PullButton.get();
        boolean Push = PushButton.get();

        // 2. Apply deadband
        ySpeed = Math.abs(ySpeed) > GamepadJoystick.kDeadband ? ySpeed : 0.0; // 搖桿值轉換
        
        if (ySpeed > 0) {
            fixtureSubsystem.runHandModules(true, false); // 上升
        } else if (ySpeed < 0) {
            fixtureSubsystem.runHandModules(true, true); // 下降
        } else {
            fixtureSubsystem.runHandModules(false, false); // 停止
        }

        if (Pull == true) {
            fixtureSubsystem.runHandModules(true, false); // 手收方塊
        } else if (Push == true) {
            fixtureSubsystem.runHandModules(true, true); // 手吐方塊
        } else {
            fixtureSubsystem.runHandModules(false, false); // 手部停止收放
        }
    }

    @Override
    public void end(boolean interrupted) {
        fixtureSubsystem.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
