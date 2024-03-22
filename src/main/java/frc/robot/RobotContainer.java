// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.arcadeDrive;
import frc.robot.commands.shoot;
import frc.robot.commands.shootaIntake;
import frc.robot.commands.take;
import frc.robot.commands.wheels;
import frc.robot.commands.Auto.auto_front;
import frc.robot.subsystems.climberLeft;
import frc.robot.subsystems.climberRight;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final CommandXboxController joy_system = new CommandXboxController(0);
  private final CommandXboxController joy_drive = new CommandXboxController(1);

  private final drivetrain drivetrainS = new drivetrain();
  private climberRight climberRight = new climberRight();
  private climberLeft climberLeft = new climberLeft();
  private intake intake = new intake();
  private taker taker = new taker();
  private shooter shooter = new shooter();

  private final arcadeDrive arcadeDriveC = new arcadeDrive(drivetrainS, joy_drive);
  private final auto_front auto_front = new auto_front(shooter, taker, drivetrainS, intake);

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    drivetrainS.setDefaultCommand(arcadeDriveC);
    
    joy_system.rightBumper().whileTrue(intake.goToPositionIntake(219).alongWith(new take(taker)))
    .whileFalse(intake.goToPositionIntake(60));
    joy_system.leftBumper().whileTrue(intake.goToPositionIntake(0).alongWith(new shoot(shooter, taker)))
    .whileFalse(intake.goToPositionIntake(60));
    joy_system.b().whileTrue(intake.goToPositionIntake(95).alongWith(new shootaIntake(taker)))
    .whileFalse(intake.goToPositionIntake(60));
    joy_system.a().whileTrue(new wheels(taker));

    joy_drive.leftBumper().whileTrue(climberLeft.leftMove(170)).whileFalse(climberLeft.leftStop());
    joy_drive.leftBumper().and(joy_drive.a()).whileTrue(climberLeft.leftMove(0)).whileFalse(climberLeft.leftStop());
    joy_drive.rightBumper().whileTrue(climberRight.rightMove(170)).whileFalse(climberRight.rightStop());
    joy_drive.rightBumper().and(joy_drive.a()).whileTrue(climberRight.rightMove(0)).whileFalse(climberRight.rightStop());

    //joy_drive.leftBumper().whileTrue(new climbLeft(climberLeft));
    //joy_drive.leftBumper().and(joy_drive.a()).whileTrue(new downLeft(climberLeft));
    //joy_drive.rightBumper().whileTrue(new climbRight(climberRight));
    //joy_drive.rightBumper().and(joy_drive.a()).whileTrue(new downRight(climberRight));
  }

  public Command getAutonomousCommand() {
    return auto_front;
  }
}
