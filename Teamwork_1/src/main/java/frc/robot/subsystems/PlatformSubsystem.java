
package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 計分版 (即時顯示)
import edu.wpi.first.wpilibj2.command.SubsystemBase; // 寫 Subsystems 需引入的模板
import frc.robot.Constants.PlatformConstants;
import frc.robot.RobotMap.PlatformPort;

public class PlatformSubsystem extends SubsystemBase {
    private final PlatformModule Platform = new PlatformModule(PlatformPort.kPlatformPort, false); // 設定平台控制器


    public PlatformSubsystem() { // 建構函式
        new Thread(() -> {}).start();
    }

    public void runModules(boolean run, boolean reverse) { // 進行轉動 (雙臂同時開或合)
        Platform.Running(run, reverse);
    }
 
    @Override
    public void periodic() {
        Platform.putDashboard(); // 上傳左電梯高度 Encoder
    }

    public void stopModules() { // 停止所有模組
        Platform.stop();
    }
}
