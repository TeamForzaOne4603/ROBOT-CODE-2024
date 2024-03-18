// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;

public class auto extends Command {
  /** Creates a new auto. */
  shooter shooter;
  taker taker;
  drivetrain drivetrain;
  Timer timer = new Timer();
  public auto(shooter shooter, taker taker, drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.shooter = shooter;
    this.taker = taker;
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
    if(timer.get() < 4){
      shooter.shooterMove(.85);
    }
    if(timer.get() > 2 & timer.get() < 4){
      taker.take(-.6);
    }
    if(timer.get() > 5){
      shooter.shooterMove(0);
      taker.take(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.shooterMove(0);
      taker.take(0);
      timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
