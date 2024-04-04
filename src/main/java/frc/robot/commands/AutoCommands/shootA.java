// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;

public class shootA extends Command {
  /** Creates a new shootA. */
  Timer timer = new Timer();
  shooter shooter = new shooter();
  taker taker = new taker();
  public shootA(shooter shooter, taker taker) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
    this.taker = taker;
    addRequirements(shooter,taker);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer.get() > 2.4){
      shooter.shooterMove(.8);
    }
    if(timer.get() < 2.4 & timer.get() > 1.5){
      taker.take(-.6);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > 2.4;
  }
}
