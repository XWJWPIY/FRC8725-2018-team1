
package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder; // Can for Phoenix
import com.revrobotics.CANSparkMax; // Can for spark
import com.revrobotics.RelativeEncoder; // 相對編碼器
import com.revrobotics.CANSparkMax.IdleMode; // 引入閒置函式庫
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // 馬達類型
import edu.wpi.first.math.controller.PIDController; // PID
import edu.wpi.first.math.geometry.Rotation2d; // 2D旋轉函式庫
import edu.wpi.first.math.kinematics.SwerveModuleState; // 旋轉狀態
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 記分板(即時顯示數值)
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

public class SwerveModule {

    private final CANSparkMax driveMotor; // 前進用馬達(輪子直接與地面接觸)
    private final CANSparkMax turningMotor; // 旋轉行徑方向馬達

    private final RelativeEncoder driveEncoder; // 前進用馬達編碼器紀錄
    private final RelativeEncoder turningEncoder; // 旋轉方向用馬達編碼器紀錄

    private final PIDController turningPidController; // 

    private final CANCoder absoluteEncoder; // 絕對定向編碼器(在Swerve的最上方的位置)
    private final boolean absoluteEncoderReversed; // 角度是否要加負號 (?
    private final double absoluteEncoderOffsetRad; // 抵消用角度 (?


    public SwerveModule(int driveMotorId, int turningMotorId, boolean driveMotorReversed, boolean turningMotorReversed,
            int absoluteEncoderId, double absoluteEncoderOffset, boolean absoluteEncoderReversed) {
        this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
        this.absoluteEncoderReversed = absoluteEncoderReversed;

        absoluteEncoder = new CANCoder(absoluteEncoderId); // 訂定物件絕對位置編碼器的 Can (Phoenix)

        absoluteEncoder.setPositionToAbsolute(); // 將現在位置設為絕對位置(0?)
        
        driveMotor = new CANSparkMax(driveMotorId, MotorType.kBrushless); // 設定前行馬達，並設為無刷
        turningMotor = new CANSparkMax(turningMotorId, MotorType.kBrushless); // 設定轉向馬達，並設為無刷
        
        driveMotor.setIdleMode(IdleMode.kCoast); // 設為無動力時以慣性滑行
        turningMotor.setIdleMode(IdleMode.kCoast); // 設為無動力時以慣性滑行
                
        driveMotor.setInverted(driveMotorReversed); // 是否反轉
        turningMotor.setInverted(turningMotorReversed);

        driveEncoder = driveMotor.getEncoder(); // 取得現在編碼器的值
        turningEncoder = turningMotor.getEncoder();

        driveEncoder.setPositionConversionFactor(ModuleConstants.kDriveEncoderRot2Meter); // 轉換單位 to (m)
        driveEncoder.setVelocityConversionFactor(ModuleConstants.kDriveEncoderRPM2MeterPerSec); // 轉換速度單位為 度/分秒
        turningEncoder.setPositionConversionFactor(ModuleConstants.kTurningEncoderRot2Rad); // 轉換單位 to (m)
        turningEncoder.setVelocityConversionFactor(ModuleConstants.kTurningEncoderRPM2RadPerSec); // 轉換速度單位為 度/分秒

        turningPidController = new PIDController(ModuleConstants.kPTurning, 0, 0); // PID 控制(導入kp)
        turningPidController.enableContinuousInput(-Math.PI, Math.PI); // 設定輸入範圍(min, max)
        resetEncoders(); // 功能在底下
        putDashboard(); // 功能在底下

    }

    public double getDrivePosition() { // 取得現在位置
        return driveEncoder.getPosition();
    }

    public double getTurningPosition() { // 取得現在偏轉角度
        return turningEncoder.getPosition();
    }

    public double getDriveVelocity() { // 獲取運轉速度
        return driveEncoder.getVelocity();
    }

    public double getAbsoluteEncoderRad() { // 調整旋轉角度
        double angle = (absoluteEncoder.getAbsolutePosition() - absoluteEncoderOffsetRad) / 360.;
        angle *= 2.0 * Math.PI;
        return angle * (absoluteEncoderReversed ? -1.0 : 1.0);
    }

    public void resetEncoders() { // 編碼器歸零
        driveEncoder.setPosition(0);
        turningEncoder.setPosition(getAbsoluteEncoderRad());
    }

    public SwerveModuleState getState() { // 獲取現在狀態 (
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
    }

    public void stop() { // 停止轉動
        driveMotor.set(0);
        turningMotor.set(0);
    }

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
    }

    public void ResetTurningMotor() { // 重製轉向馬達的位置
        if (Math.abs(turningEncoder.getPosition()) < 0.1) {
            stop();
            return;
        }
        turningMotor.set(turningPidController.calculate(turningEncoder.getPosition(), 0));
        driveMotor.set(.0);
        putDashboard(); // 功能在底下
    }

    public void putDashboard () { // 記分板及時顯示數值
        SmartDashboard.putNumber("ABS angle " + absoluteEncoder.getDeviceID(), getAbsoluteEncoderRad());
        SmartDashboard.putNumber("Abs Position " + absoluteEncoder.getDeviceID(), absoluteEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("Position " + absoluteEncoder.getDeviceID(), absoluteEncoder.getPosition());
        SmartDashboard.putNumber("Turing position " + turningMotor.getDeviceId(), turningEncoder.getPosition());
    }

}
