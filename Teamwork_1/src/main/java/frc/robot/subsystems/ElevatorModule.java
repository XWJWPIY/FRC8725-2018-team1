
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax; // Can for spark
import com.revrobotics.RelativeEncoder; // 相對編碼器
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // 馬達類型
import com.revrobotics.CANSparkMax.IdleMode; // 引入閒置函式庫
import edu.wpi.first.math.controller.PIDController; // PID
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 記分板(即時顯示數值)
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ElevatorModuleConstants;

public class ElevatorModule {    
    private final CANSparkMax Motor; // 升降馬達

    private final RelativeEncoder Encoder; // 前進用馬達編碼器紀錄

    private final PIDController turningPidController; // 

    public ElevatorModule (int MotorId, boolean MotorReversed) {
        Motor = new CANSparkMax(MotorId, MotorType.kBrushless);

        Motor.setIdleMode(IdleMode.kBrake); // 設為無動力時以慣性滑行

        Motor.setInverted(MotorReversed); // 是否反轉

        Encoder = Motor.getEncoder(); // 取得現在編碼器的值
    }
}
