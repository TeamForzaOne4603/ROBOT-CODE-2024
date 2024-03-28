// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.taker;

public class shootaIntake extends Command {
  /** Creates a new shootaIntake. */
  Timer timer = new Timer();
  taker taker;
  public shootaIntake(taker taker) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.taker = taker;
    addRequirements(taker);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    if(timer.get() > .7){
      taker.take(-1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    taker.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
