
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

    // private final PIDController turningPidController; // 

    public ElevatorModule(int MotorId, boolean MotorReversed) {
        Motor = new CANSparkMax(MotorId, MotorType.kBrushless);

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

    /* 搖桿控制
    public void setDesiredState(SwerveModuleState state) { // 設定階段(輸入值)
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop(); // 停止轉動
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        driveMotor.set(state.speedMetersPerSecond / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        turningMotor.set(turningPidController.calculate(getTurningPosition(), state.angle.getRadians()));
        SmartDashboard.putString("Swerve[" + absoluteEncoder.getDeviceID() + "] state", state.toString());
        putDashboard(); // 功能在底下
    }*/

    public void putDashboard () { // 記分板及時顯示數值
        SmartDashboard.putNumber("High: " + Motor.getDeviceId(), Encoder.getPosition());
    }
}
