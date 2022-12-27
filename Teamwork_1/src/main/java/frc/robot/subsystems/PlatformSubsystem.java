
package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 計分版 (即時顯示)
import edu.wpi.first.wpilibj2.command.SubsystemBase; // 寫 Subsystems 需引入的模板
import frc.robot.Constants.PlatformConstants;
import frc.robot.RobotMap.PlatformPort;

public class PlatformSubsystem extends SubsystemBase {
    private final PlatformModule LeftMotor = new PlatformModule(PlatformPort.kLeftPlatformPort, false); // 設定平台控制器
    private final PlatformModule RightMotor = new PlatformModule(PlatformPort.kRightPlatformPort, true); //
    

    public PlatformSubsystem() { // 建構函式
        new Thread(() -> {}).start();
    }

    public void runModules(boolean run, boolean reverse) { // 進行轉動 (雙臂同時開或合)
        LeftMotor.Running(run, reverse);
        RightMotor.Running(run, reverse);
    }
 
    @Override
    public void periodic() {
        LeftMotor.putDashboard(); // 上傳左電梯高度 Encoder
        RightMotor.putDashboard(); 
    }

    public void stopModules() { // 停止所有模組
        LeftMotor.stop();
        RightMotor.stop();
    }
}
