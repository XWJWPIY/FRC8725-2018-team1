
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax; // Can for spark
import com.revrobotics.RelativeEncoder; // 相對編碼器
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // 馬達類型
import com.revrobotics.CANSparkMax.IdleMode; // 引入閒置函式庫
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 記分板(即時顯示數值)
// import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.FixtureConstants;

public class FixtureModule {

    private final CANSparkMax HandMotor; // 收方塊的模組
    private final CANSparkMax ArmMotor; // 手臂旋轉的模組

    private final boolean HandMotorReversed; // 紀錄 Neo 馬達是否需要反轉
    private final boolean ArmMotorReversed;

    private final RelativeEncoder HandMotorEncoder; // 收方塊用馬達編碼器紀錄
    private final RelativeEncoder ArmMotorEncoder; // 旋轉用馬達編碼器紀錄



    public FixtureModule(int HandMotorId, boolean HandMotorReversed, int ArmMotorId, Boolean ArmMotorReversed) {
        HandMotor = new CANSparkMax(HandMotorId, MotorType.kBrushless);
        ArmMotor = new CANSparkMax(ArmMotorId, MotorType.kBrushless);
        this.HandMotorReversed = HandMotorReversed;
        this.ArmMotorReversed = ArmMotorReversed;

        HandMotor.setIdleMode(IdleMode.kCoast); // 設為無動力時以鎖定
        ArmMotor.setIdleMode(IdleMode.kCoast);

        HandMotor.setInverted(HandMotorReversed); // 是否反轉
        ArmMotor.setInverted(ArmMotorReversed);

        HandMotorEncoder = HandMotor.getEncoder(); // 取得現在編碼器的值
        ArmMotorEncoder = ArmMotor.getEncoder();

        HandMotorEncoder.setPositionConversionFactor(FixtureConstants.kFrontFixtureEncoderRot2Meter); // 轉換單位 to (m)
        ArmMotorEncoder.setPositionConversionFactor(FixtureConstants.kBackFixtureEncoderRot2Rad); // 轉換單位 to (度)
    }

    public double getHandPosition() { // 取得收方塊收進的距離
        return HandMotorEncoder.getPosition();
    }

    public double getArmPosition() { // 取得手臂旋轉的角度
        return ArmMotorEncoder.getPosition();
    }

    public void stop() { // 停止轉動
        HandMotor.set(0);
        ArmMotor.set(0);
    }



}
