// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.take;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;

public class LineaBlanca extends Command {
  /** Creates a new LineaBlanca. */
  Timer timer = new Timer();
  drivetrain drivetrain;
  shooter shooter;
  intake intake;
  taker taker;

  public LineaBlanca(drivetrain drivetrain, shooter shooter, intake intake, taker taker) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.intake = intake;
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
    if(timer.get() < 1.8){
      shooter.shooterMove(.85);
    }
    if(timer.get() > .9 & timer.get() < 1.8){
      taker.take(-.6);
    }
    if(timer.get() > 2){
      shooter.shooterMove(0);
      taker.take(0);
    } 
    while(timer.get() > 2 & timer.get() < 5){
   drivetrain.arcadeDrive(.6, -0.14);
    }
    while(timer.get() > 4 & timer.get() < 5.5){

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
