// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.take;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.taker;

public class atras extends Command {
  /** Creates a new atras. */
  Timer timer = new Timer();
  drivetrain drivetrain;
  taker taker;
  public atras(drivetrain drivetrain, taker taker) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.taker = taker;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() > 2.4 & timer.get() < 6) {
      drivetrain.arcadeDrive(-.5, 0);
    }
    if(timer.get() > 3.1 & timer.get() < 6) {
      taker.take(.6);
    }

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
