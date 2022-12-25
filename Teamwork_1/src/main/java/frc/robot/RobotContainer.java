// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.SwerveJoystickCmd;
import frc.robot.commands.ElevatorJoystickCmd;
import frc.robot.commands.FixtureJoystickCmd;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FixtureSubsystem;
import frc.robot.GamepadJoystick;

public class RobotContainer {

    private final SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem();
    private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
    private final FixtureSubsystem m_fixtureSubsystem = new FixtureSubsystem();

    private final Joystick driverJoytick = new Joystick(GamepadJoystick.kDriverControllerPort);

    public RobotContainer() {
        m_swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(
                m_swerveSubsystem,
                () -> driverJoytick.getRawAxis(GamepadJoystick.kDriverYAxis),
                () -> driverJoytick.getRawAxis(GamepadJoystick.kDriverXAxis),
                () -> driverJoytick.getRawAxis(GamepadJoystick.kDriverRotAxis),
                () -> !driverJoytick.getRawButton(GamepadJoystick.kDriverFieldOrientedButtonIdx)));

        m_elevatorSubsystem.setDefaultCommand(new ElevatorJoystickCmd(m_elevatorSubsystem, () -> driverJoytick.getRawAxis(GamepadJoystick.kElevatorAxis)));
        m_fixtureSubsystem.setDefaultCommand(new FixtureJoystickCmd(m_fixtureSubsystem, () -> driverJoytick.getRawAxis(GamepadJoystick.kFixtureArmAxis))); // 夾子開合

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        new JoystickButton(driverJoytick, GamepadJoystick.kDriverFieldOrientedButtonIdx).whenPressed(() -> m_swerveSubsystem.zeroHeading()); // 歸零
        if (driverJoytick.getRawButtonPressed(GamepadJoystick.kFixturePullButtonIdx) == true) {
            m_fixtureSubsystem.runHandModules(true, false); // 手收方塊
        } else if (driverJoytick.getRawButtonPressed(GamepadJoystick.kFixturePushButtonIdx) == true) {
            m_fixtureSubsystem.runHandModules(true, true); // 手吐方塊
        } else {
            m_fixtureSubsystem.runHandModules(false, false); // 手部停止收放
        }
    }

    /*
    public Command getAutonomousCommand() {

    }
    */
}
