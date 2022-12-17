
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax; // Can for spark
import com.revrobotics.RelativeEncoder; // 相對編碼器
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // 馬達類型
import com.revrobotics.CANSparkMax.IdleMode; // 引入閒置函式庫
// import edu.wpi.first.math.controller.PIDController; // PID
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 記分板(即時顯示數值)
// import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ElevatorModuleConstants;

public class ElevatorModule {    
    private final CANSparkMax Motor; // 升降馬達

    private final RelativeEncoder Encoder; // 前進用馬達編碼器紀錄
    private final boolean MotorReversed;

    // private final PIDController turningPidController; // 

    public ElevatorModule(int MotorId, boolean MotorReversed) {
        Motor = new CANSparkMax(MotorId, MotorType.kBrushless);
        this.MotorReversed = MotorReversed;

        Motor.setIdleMode(IdleMode.kBrake); // 設為無動力時以慣性滑行

        Motor.setInverted(MotorReversed); // 是否反轉

        Encoder = Motor.getEncoder(); // 取得現在編碼器的值

        Encoder.setPositionConversionFactor(ElevatorModuleConstants.kElevatorEncoderRot2Meter); // 轉換單位 to (m)
    }

    public double getDrivePosition() { // 取得現在位置
        return Encoder.getPosition();
    }

    public double getDriveVelocity() { // 獲取運轉速度
        return Encoder.getVelocity();
    }

    public void resetEncoders() { // 編碼器歸零
        Encoder.setPosition(0);
    }


    public void stop() { // 停止轉動
        Motor.set(0);
    }

    // 搖桿控制
    public void Running(boolean Running, boolean reverse) { 
        if (Running == true) { // 若是啟動
            if (reverse) { // 方向是否反轉
                Motor.set(ElevatorModuleConstants.kElevatorEncoderRPM2MeterPerSec); // 向下
            } else {
                Motor.set(-ElevatorModuleConstants.kElevatorEncoderRPM2MeterPerSec); // 向上
            }
        }
        getDrivePosition();
        putDashboard(); // 功能在底下
    }

    public void putDashboard () { // 記分板及時顯示數值
        if (MotorReversed == false) {
            SmartDashboard.putNumber("Left Elevator: " + Motor.getDeviceId(), Encoder.getPosition());
        } else {
            SmartDashboard.putNumber("Right Elevator: " + Motor.getDeviceId(), Encoder.getPosition());
        }
    }
}
