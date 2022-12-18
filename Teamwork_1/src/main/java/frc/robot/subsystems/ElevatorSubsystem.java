
package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 計分版 (即時顯示)
import edu.wpi.first.wpilibj2.command.SubsystemBase; // 寫 Subsystems 需引入的模板
import frc.robot.Constants.ElevatorConstants;
import frc.robot.RobotMap.ElevatorPort;


public class ElevatorSubsystem extends SubsystemBase {
    private final ElevatorModule LeftMotor = new ElevatorModule(ElevatorPort.kLeftMotorPort, false); // 設定左馬達模組
    private final ElevatorModule RightMotor = new ElevatorModule(ElevatorPort.kRightMotorPort, true); // 設定右馬達模組


    public ElevatorSubsystem() { // 建構函式
        new Thread(() -> {}).start();
    }

    public void runModules(boolean run, boolean reverse) { // 執行模組
        LeftMotor.Running(run, reverse);
        RightMotor.Running(run, !reverse);
    }
 
    @Override
    public void periodic() {
        LeftMotor.putDashboard(); // 上傳左電梯高度 Encoder
        RightMotor.putDashboard(); // 上傳右電梯高度 Encoder
    }

    public void stopModules() { // 停止所有模組
        LeftMotor.stop();
        RightMotor.stop();
    }
}
