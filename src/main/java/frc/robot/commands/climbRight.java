// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climberRight;

public class climbRight extends Command {
  /** Creates a new climbRight. */
  Timer timer = new Timer();
  climberRight climberRight;
  
  public climbRight(climberRight climberRight) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climberRight = climberRight;
      addRequirements(climberRight);
    }
  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climberRight.rightMove(-.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climberRight.rightStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
