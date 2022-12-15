
package frc.robot;

import edu.wpi.first.math.util.Units; // 常用單位之間轉換
import edu.wpi.first.math.geometry.Translation2d; // 轉為 2D 坐標系
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile; // 軌跡運算.梯形輪廓

public final class Constants {

    public static final class ModuleConstants {
        public static final double kWheelDiameterMeters = Units.inchesToMeters(4); // inch -> m
        public static final double kDriveMotorGearRatio = 1 / 8.14; // 齒輪轉動比
        public static final double kTurningMotorGearRatio = 7. / 150.; // 齒輪轉動比
        public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters; // 以 Encorder 的值轉換為車輪行走之距離，單位為 m
        public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI; // 以 Encorder 的值轉換為角度
        public static final double kDriveEncoderRPM2MeterPerSec = kDriveEncoderRot2Meter / 60; // 將單位轉換為 m/s
        public static final double kTurningEncoderRPM2RadPerSec = kTurningEncoderRot2Rad / 60; // 將單位轉換為 m/s
        public static final double kPTurning = 0.5; // kP
    }

    public static final class DriveConstants {

        public static final double kTrackWidth = 0.58;
        // 左右輪間距
        public static final double kWheelBase = 0.58;
        // 前後輪間距
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        public static final boolean kFrontLeftTurningEncoderReversed = true;
        public static final boolean kBackLeftTurningEncoderReversed = true;
        public static final boolean kFrontRightTurningEncoderReversed = true;
        public static final boolean kBackRightTurningEncoderReversed = true;

        public static final boolean kFrontLeftDriveEncoderReversed = true;
        public static final boolean kBackLeftDriveEncoderReversed = true;
        public static final boolean kFrontRightDriveEncoderReversed = false; // 理論上要反轉的 Neo
        public static final boolean kBackRightDriveEncoderReversed = false;

        public static final boolean kFrontLeftDriveAbsoluteEncoderReversed = false;
        public static final boolean kBackLeftDriveAbsoluteEncoderReversed = false;
        public static final boolean kFrontRightDriveAbsoluteEncoderReversed = false;
        public static final boolean kBackRightDriveAbsoluteEncoderReversed = false;

        public static final double kFrontLeftDriveAbsoluteEncoderOffsetRad = 297.5;
        public static final double kBackLeftDriveAbsoluteEncoderOffsetRad = 90.08;   
        public static final double kFrontRightDriveAbsoluteEncoderOffsetRad = 290.39;
        public static final double kBackRightDriveAbsoluteEncoderOffsetRad = 31.2;

        public static final double kPhysicalMaxSpeedMetersPerSecond = Units.feetToMeters(12); // 紀錄 feet 轉換 Meter 倍率
        public static final double kPhysicalMaxAngularSpeedRadiansPerSecond = 2 * 2 * Math.PI;

        public static final double kTeleDriveMaxSpeedMetersPerSecond = kPhysicalMaxSpeedMetersPerSecond / 2; // 遠程驅動最大前進速度值
        public static final double kTeleDriveMaxAngularSpeedRadiansPerSecond = // 遠程驅動最大旋轉速度值
                kPhysicalMaxAngularSpeedRadiansPerSecond / 4 * 1.5;
        public static final double kTeleDriveMaxAccelerationUnitsPerSecond = 3; // 遠程驅動最大前進加速度
        public static final double kTeleDriveMaxAngularAccelerationUnitsPerSecond = 3; // 遠程驅動最大角加速度
    }

    // 自動用常數
    // public static final class AutoConstants {
    //     public static final double kMaxSpeedMetersPerSecond = DriveConstants.kPhysicalMaxSpeedMetersPerSecond / 4;
    //     public static final double kMaxAngularSpeedRadiansPerSecond = //
    //         DriveConstants.kPhysicalMaxAngularSpeedRadiansPerSecond / 10;
    //     public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    //     public static final double kMaxAngularAccelerationRadiansPerSecondSquared = Math.PI / 4;
    //     public static final double kPXController = 1.5;
    //     public static final double kPYController = 1.5;
    //     public static final double kPThetaController = 3;

    //     public static final TrapezoidProfile.Constraints kThetaControllerConstraints = // 角度控制
    //         new TrapezoidProfile.Constraints(
    //             kMaxAngularSpeedRadiansPerSecond,
    //             kMaxAngularAccelerationRadiansPerSecondSquared);
    // }

    public static final class ElevatorModuleConstants {
        public static final double kElevatorMotorWheelRadius = 2; // 馬達輪半徑
        public static final double kElevatorUpperWheelRadius = 2; // 升降輪半徑
        public static final double kElevatorMotorWheelDiameterMeters = Units.inchesToMeters(kElevatorMotorWheelRadius * 2); // 馬達輪 inch -> m
        public static final double kElevatorUpperWheelDiameterMeters = Units.inchesToMeters(kElevatorUpperWheelRadius * 2); // 升降輪 inch -> m
        public static final double kElevatorMotorGearRatio = 1 / 8.14; // 齒輪轉動比
        public static final double kElevatorEncoderRot2Meter = kElevatorMotorGearRatio * Math.PI * kElevatorMotorWheelDiameterMeters; // 以 Encorder 的值轉換為升降之位置，單位為 m
        public static final double kElevatorEncoderRPM2MeterPerSec = kElevatorEncoderRot2Meter / 60; // 將單位轉換為 m/s
    }

    public static final class ElevatorConstants {
        
    }


}
