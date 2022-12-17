
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SPI; // 串和外設介面 (同步串行通信)
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 計分版 (即時顯示)
import edu.wpi.first.wpilibj2.command.SubsystemBase; // 寫 Subsystems 需引入的模板
import frc.robot.Constants.ElevatorConstants;
import frc.robot.RobotMap.ElevatorPort;


public class ElevatorSubsystem extends SubsystemBase {
    private final ElevatorModule LeftMotor = new ElevatorModule(ElevatorPort.kLeftMotorPort, false); // 設定左馬達模組
    private final ElevatorModule RightMotor = new ElevatorModule(ElevatorPort.kRightMotorPort, false); // 設定右馬達模組

}
