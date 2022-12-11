package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS; // 處理導航傳感器偵測到的資料傳送作業 (航資參考系統)
import edu.wpi.first.wpilibj.SPI; // 串和外設介面 (同步串行通信)
import edu.wpi.first.math.geometry.Pose2d; // 紀錄二維的平移和旋轉
import edu.wpi.first.math.geometry.Rotation2d; // 角度分量，機器人相對於二維坐標系上的軸的旋轉
import edu.wpi.first.math.kinematics.SwerveDriveKinematics; // Swerve 類可接收的可變數量的值 (?
import edu.wpi.first.math.kinematics.SwerveDriveOdometry; // 里程紀錄
import edu.wpi.first.math.kinematics.SwerveModuleState; // 方向紀錄
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // 計分版 (即時顯示)
import edu.wpi.first.wpilibj2.command.SubsystemBase; // 寫 Subsystems 需引入的模板
import frc.robot.Constants.DriveConstants;
import frc.robot.RobotMap.DriverPort;

public class SwerveSubsystem extends SubsystemBase {
    // 左前方 Swerve 物件
    private final SwerveModule frontLeft = new SwerveModule(
        DriverPort.kFrontLeftDriveMotorPort,
        DriverPort.kFrontLeftTurningMotorPort,
        DriveConstants.kFrontLeftDriveEncoderReversed,
        DriveConstants.kFrontLeftTurningEncoderReversed,
        DriverPort.kFrontLeftDriveAbsoluteEncoderPort,
        DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad,
        DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);
    // 右前方 Swerve 物件
    private final SwerveModule frontRight = new SwerveModule(
        DriverPort.kFrontRightDriveMotorPort,
        DriverPort.kFrontRightTurningMotorPort,
        DriveConstants.kFrontRightDriveEncoderReversed,
        DriveConstants.kFrontRightTurningEncoderReversed,
        DriverPort.kFrontRightDriveAbsoluteEncoderPort,
        DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad,
        DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);
    // 左後方 Swerve 物件
    private final SwerveModule backLeft = new SwerveModule(
        DriverPort.kBackLeftDriveMotorPort,
        DriverPort.kBackLeftTurningMotorPort,
        DriveConstants.kBackLeftDriveEncoderReversed,
        DriveConstants.kBackLeftTurningEncoderReversed,
        DriverPort.kBackLeftDriveAbsoluteEncoderPort,
        DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad,
        DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);
    // 右後方 Swerve 物件
    private final SwerveModule backRight = new SwerveModule(
        DriverPort.kBackRightDriveMotorPort,
        DriverPort.kBackRightTurningMotorPort,
        DriveConstants.kBackRightDriveEncoderReversed,
        DriveConstants.kBackRightTurningEncoderReversed,
        DriverPort.kBackRightDriveAbsoluteEncoderPort,
        DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad,
        DriveConstants.kBackRightDriveAbsoluteEncoderReversed);

    private final AHRS gyro = new AHRS(SPI.Port.kMXP); // 建立陀螺儀物件

    private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(DriveConstants.kDriveKinematics,
        new Rotation2d(0)); // 建立里程表
    
    public SwerveSubsystem() { // 建構函式
        new Thread(() -> {
            try {
                Thread.sleep(1000); // (?
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
        
    }
    
    public void zeroHeading() { // gyro 歸零
        gyro.reset();
    }

    public double getHeading() { // 輸入角 % 360
        return Math.IEEEremainder(-gyro.getAngle(), 360);
    }

    public Rotation2d getRotation2d() { // 現在陀螺儀朝向的方位角
        return Rotation2d.fromDegrees(getHeading());
    }

    public Pose2d getPose() { // 定位座標
        return odometer.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) { // 定位與方向歸零(處理偏移角度) (?
        odometer.resetPosition(pose, getRotation2d());
    }

    // 定期執行
    @Override
    public void periodic() {
        odometer.update(getRotation2d(), frontLeft.getState(), frontRight.getState(), backLeft.getState(),
                backRight.getState()); // 上傳座標位置
        SmartDashboard.putNumber("Robot Heading", getHeading());
        SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());
        backLeft.putDashboard(); // 上傳左後輪值
        backRight.putDashboard(); // 上傳右後輪值
        frontLeft.putDashboard(); // 上傳左前輪值
        frontRight.putDashboard(); // 上傳右前輪值
    }

    public void stopModules() { // 停止所有模組
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }
    // TO-DO
    public void setModuleStates(SwerveModuleState[] desiredStates) { // 設定模組狀態
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    public void resetTurningmotor () {
        frontLeft.ResetTurningMotor(); // 重製轉動的馬達
        frontRight.ResetTurningMotor();
        backLeft.ResetTurningMotor();
        backRight.ResetTurningMotor();
    }


}
