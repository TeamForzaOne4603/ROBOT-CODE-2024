// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.arcadeDrive;
import frc.robot.commands.climbLeft;
import frc.robot.commands.climbRight;
import frc.robot.commands.downLeft;
import frc.robot.commands.downRight;
import frc.robot.commands.take;
import frc.robot.subsystems.climberLeft;
import frc.robot.subsystems.climberRight;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.taker;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final CommandXboxController joy = new CommandXboxController(0);
  private final drivetrain drivetrainS = new drivetrain();
  private climberRight climberRight = new climberRight();
  private climberLeft climberLeft = new climberLeft();
  private intake intake = new intake();
  private taker taker = new taker();

  private final arcadeDrive arcadeDriveC = new arcadeDrive(drivetrainS, joy);

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    drivetrainS.setDefaultCommand(arcadeDriveC);
    
    joy.rightBumper().whileTrue(intake.goToPositionIntake(200).alongWith(new take(taker)))
    .whileFalse(intake.goToPositionIntake(60));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
