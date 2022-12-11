
package frc.robot;

import edu.wpi.first.math.util.Units; // 常用單位之間轉換
import edu.wpi.first.math.geometry.Translation2d; // 轉為 2D 坐標系
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class Constants {

    public static final class Joystick {
        
    }

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
        public static final boolean kFrontRightDriveEncoderReversed = false;
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
    }
}