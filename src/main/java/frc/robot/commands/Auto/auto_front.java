// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;

public class auto_front extends Command {
  /** Creates a new auto. */
  shooter shooter;
  taker taker;
  drivetrain drivetrain;
  intake intake;
  Timer timer = new Timer();
  public auto_front(shooter shooter, taker taker, drivetrain drivetrain , intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.shooter = shooter;
    this.taker = taker;
    this.intake = intake;
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
    while(timer.get() > 5 & timer.get() < 8){
      drivetrain.arcadeDrive(.5, 0);
      intake.goToPosition(219);
      taker.take(.6);
    }
    while(timer.get() > 8.2 & timer.get() < 11.2){
      drivetrain.arcadeDrive(-.5, 0);
      intake.goToPosition(0);
    }
    if(timer.get() > 11.2 & timer.get() < 14){
      shooter.shooterMove(.85);
    }
    if(timer.get() > 12 & timer.get() < 14){
      taker.take(-.6);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.shooterMove(0);
      taker.take(0);
      drivetrain.arcadeDrive(0, 0);
      timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
