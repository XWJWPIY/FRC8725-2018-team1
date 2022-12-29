package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.math.filter.SlewRateLimiter;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ElevatorModuleConstants;
import frc.robot.GamepadJoystick;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorJoystickCmd  extends CommandBase{
    private final ElevatorSubsystem elevatorSubsystem;
    private final Supplier<Double> ySpdFunction;
    private final SlewRateLimiter yLimiter;


    public ElevatorJoystickCmd(ElevatorSubsystem elevatorSubsystem, Supplier<Double> ySpdFunction) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.ySpdFunction = ySpdFunction;
        this.yLimiter = new SlewRateLimiter(ElevatorModuleConstants.kElevatorEncoderRPM2MeterPerSec); // y 極限
        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // 1. Get real-time joystick inputs
        double ySpeed = ySpdFunction.get(); // 得到 y 軸的即時值

        // 2. Apply deadband
        ySpeed = Math.abs(ySpeed) > GamepadJoystick.kDeadband ? ySpeed : 0.0; // 搖桿值轉換


        // 3. Make the driving smoother
        ySpeed = yLimiter.calculate(ySpeed) * ElevatorModuleConstants.kElevatorMaxSpeedMetersPerSecond; // 計算速度
        
        if (ySpeed > 0) {
            elevatorSubsystem.runModules(true, false); // 上升
        } else if (ySpeed < 0) {
            elevatorSubsystem.runModules(true, true); // 下降
        } else {
            elevatorSubsystem.runModules(false, false); // 停止
        }
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
