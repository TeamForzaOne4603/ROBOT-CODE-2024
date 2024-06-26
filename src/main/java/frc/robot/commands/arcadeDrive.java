// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.drivetrain;

public class arcadeDrive extends Command {
  /** Creates a new arcadeDrive. */
  drivetrain drivetrainSubsystem;
  CommandXboxController joy;
  public arcadeDrive(drivetrain drivetrain, CommandXboxController joystick) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrainSubsystem = drivetrain;
    joy = joystick;
    addRequirements(drivetrainSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrainSubsystem.arcadeDrive(joy.getLeftY() * -1, joy.getRightX() * -.7);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
