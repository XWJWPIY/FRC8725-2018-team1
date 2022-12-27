
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax; // Can for spark
import com.revrobotics.RelativeEncoder; // 相對編碼器
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // 馬達類型
import com.revrobotics.CANSparkMax.IdleMode; // 引入閒置函式庫
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 記分板(即時顯示數值)
// import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.PlatformConstants;

public class PlatformModule {
    private final CANSparkMax LMotor; // 平台模組
    private final CANSparkMax RMotor;

    private final boolean LeftMotorReversed; // 紀錄 Neo 馬達是否需要反轉
    private final boolean RightMotorReversed; // 紀錄 Neo 馬達是否需要反轉

    private final RelativeEncoder MotorLeftEncoder; // 收方塊用馬達編碼器紀錄
    private final RelativeEncoder MotorRightEncoder; // 收方塊用馬達編碼器紀錄


    public PlatformModule(int LeftMotorId, boolean LeftMotorReversed, int RightMotorId, boolean RightMotorReversed) { // 桿部伸縮
        LMotor = new CANSparkMax(LeftMotorId, MotorType.kBrushless);
        RMotor = new CANSparkMax(RightMotorId, MotorType.kBrushless);
        this.LeftMotorReversed = LeftMotorReversed;
        this.RightMotorReversed = RightMotorReversed;

        LMotor.setIdleMode(IdleMode.kCoast); // 設為無動力時鎖定
        RMotor.setIdleMode(IdleMode.kCoast);

        LMotor.setInverted(LeftMotorReversed); // 是否反轉
        RMotor.setInverted(RightMotorReversed); 

        MotorLeftEncoder = LMotor.getEncoder(); // 取得現在編碼器的值
        MotorRightEncoder = RMotor.getEncoder();
        
        MotorLeftEncoder.setPositionConversionFactor(PlatformConstants.kBackPlatformEncoderRot2Rad); // 轉換單位 to (度)
        MotorRightEncoder.setPositionConversionFactor(PlatformConstants.kBackPlatformEncoderRot2Rad);
    }

    public double getLeftPosition() { // 取得旋轉距離
        return MotorLeftEncoder.getPosition();
    }

    public double getRightPosition() { //
        return MotorRightEncoder.getPosition();
    }

    public void stop() { // 停止轉動
        LMotor.set(0);
        RMotor.set(0);
    }

    public void Running(boolean Running, boolean reverse) {  // 調整角度
        if (Running == true) { // 若是啟動
            if (reverse) { // 方向是否反轉
                LMotor.set(-PlatformConstants.kPlatformTurningSpeed); // 外
                RMotor.set(-PlatformConstants.kPlatformTurningSpeed);
            } else {
                LMotor.set(PlatformConstants.kPlatformTurningSpeed); // 內
                RMotor.set(PlatformConstants.kPlatformTurningSpeed);
            }
        }
        getLeftPosition();
        getRightPosition();
        putDashboard(); // 功能在底下
    }


    public void putDashboard () { // 記分板及時顯示數值
        SmartDashboard.putNumber("Left Platform Angle : " + LMotor.getDeviceId(), MotorLeftEncoder.getPosition());
        SmartDashboard.putNumber("Right Platform Angle : " + RMotor.getDeviceId(), MotorRightEncoder.getPosition());
    }
}
