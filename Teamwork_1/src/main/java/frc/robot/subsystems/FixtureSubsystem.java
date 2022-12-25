
package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 計分版 (即時顯示)
import edu.wpi.first.wpilibj2.command.SubsystemBase; // 寫 Subsystems 需引入的模板
import frc.robot.Constants.FixtureConstants;
import frc.robot.RobotMap.FixturePort;

public class FixtureSubsystem extends SubsystemBase {

    private final FixtureModule LeftArm = new FixtureModule(
        FixturePort.kLeftHandMotorPort, false,
        FixturePort.kLeftArmMotorPort, false); // 設定左馬達模組
    private final FixtureModule RightArm= new FixtureModule(
        FixturePort.kRightHandMotorPort, false,
        FixturePort.kRightArmMotorPort, false); // 設定右馬達模組


    public FixtureSubsystem() { // 建構函式
        new Thread(() -> {}).start();
    }

    public void runArmModules(boolean run, boolean reverse) { // 執行模組 (雙臂同時開或合)
        LeftArm.ArmRunning(run, reverse);
        RightArm.ArmRunning(run, !reverse);
    }

    public void runHandModules(boolean run, boolean reverse) { // 執行模組 (雙手同時收或放)

        LeftArm.HandRunning(run, reverse);
        RightArm.HandRunning(run, !reverse);
        
    }
 
    @Override
    public void periodic() {
        LeftArm.putDashboard(); // 上傳左電梯高度 Encoder
        RightArm.putDashboard(); // 上傳右電梯高度 Encoder
    }

    public void stopModules() { // 停止所有模組
        LeftArm.stop();
        RightArm.stop();
    }

}
