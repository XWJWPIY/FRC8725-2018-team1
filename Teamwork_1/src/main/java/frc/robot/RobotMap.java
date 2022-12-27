package frc.robot;

public class RobotMap {
    public class DriverPort {
        // 前進時控制的 Spark
        public static final int kFrontLeftDriveMotorPort = 2;
        public static final int kBackLeftDriveMotorPort = 5;
        public static final int kFrontRightDriveMotorPort = 4;
        public static final int kBackRightDriveMotorPort = 7;

        // 旋轉輪子方向時控制的 Spark
        public static final int kFrontLeftTurningMotorPort = 1;
        public static final int kBackLeftTurningMotorPort = 6;
        public static final int kFrontRightTurningMotorPort = 3;
        public static final int kBackRightTurningMotorPort = 8;
        
        // 絕對定位裝置位置
        public static final int kFrontLeftDriveAbsoluteEncoderPort = 9;
        public static final int kBackLeftDriveAbsoluteEncoderPort = 11;
        public static final int kFrontRightDriveAbsoluteEncoderPort = 10;
        public static final int kBackRightDriveAbsoluteEncoderPort = 12;
    }

    public class ElevatorPort {
        public static final int kLeftMotorPort = 13;
        public static final int kRightMotorPort = 14;
    }

    public class FixturePort {
        public static final int kLeftHandMotorPort = 15;
        public static final int kRightHandMotorPort = 16;
        public static final int kLeftArmMotorPort = 17;
        public static final int kRightArmMotorPort = 18;
    }

    public class PlatformPort {
        public static final int kPlatformPort = 19;
    }
}
