
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax; // Can for spark
import com.revrobotics.RelativeEncoder; // 相對編碼器
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // 馬達類型
import com.revrobotics.CANSparkMax.IdleMode; // 引入閒置函式庫
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 記分板(即時顯示數值)
// import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.PlatformConstants;

public class PlatformModule {
    private final CANSparkMax Motor; // 收方塊的模組

    private final boolean MotorReversed; // 紀錄 Neo 馬達是否需要反轉

    private final RelativeEncoder MotorEncoder; // 收方塊用馬達編碼器紀錄



    public PlatformModule(int MotorId, boolean MotorReversed) { // 桿部伸縮
        Motor = new CANSparkMax(MotorId, MotorType.kBrushless);
        this.MotorReversed = MotorReversed;

        Motor.setIdleMode(IdleMode.kCoast); // 設為無動力時鎖定

        Motor.setInverted(MotorReversed); // 是否反轉

        MotorEncoder = Motor.getEncoder(); // 取得現在編碼器的值
        
        MotorEncoder.setPositionConversionFactor(PlatformConstants.kBackPlatformEncoderRot2Rad); // 轉換單位 to (度)
    }

    public double getPosition() { // 取得收方塊收進的距離
        return MotorEncoder.getPosition();
    }

    public void stop() { // 停止轉動
        Motor.set(0);
    }

    public void Running(boolean Running, boolean reverse) {  // 調整角度
        if (Running == true) { // 若是啟動
            if (reverse) { // 方向是否反轉
                Motor.set(PlatformConstants.kPlatformTurningSpeed); // 吸
            } else {
                Motor.set(-PlatformConstants.kPlatformTurningSpeed); // 吐
            }
        }
        getPosition();
        putDashboard(); // 功能在底下
    }


    public void putDashboard () { // 記分板及時顯示數值
        SmartDashboard.putNumber("Platform Angle : " + Motor.getDeviceId(), MotorEncoder.getPosition());
    }
}
