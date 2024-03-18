// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;

public class ampShooter extends Command {
  /** Creates a new ampShooter. */
  Timer timer = new Timer();
  shooter shooter;
  taker taker;
  public ampShooter(shooter shooter, taker taker) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
   this.taker = taker;
   addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }
  @Override
  public void execute() {
    shooter.shooterMove(.2);
    if(timer.get() > 1.6){
      taker.take(-.8);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    taker.stop();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
