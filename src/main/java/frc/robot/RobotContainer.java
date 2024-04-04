// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.arcadeDrive;
import frc.robot.commands.shoot;
import frc.robot.commands.shootaIntake;
import frc.robot.commands.take;
import frc.robot.commands.wheels;
import frc.robot.commands.Auto.LineaBlanca;
import frc.robot.commands.Auto.auto_front;
import frc.robot.commands.Auto.speaker;
import frc.robot.subsystems.climberLeft;
import frc.robot.subsystems.climberRight;
import frc.robot.subsystems.drivetrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.taker;


public class RobotContainer {
  
  private final CommandXboxController joy_system = new CommandXboxController(0);
  private final CommandXboxController joy_drive = new CommandXboxController(1);

  private final drivetrain drivetrainS = new drivetrain();
  private climberRight climberRight = new climberRight();
  private climberLeft climberLeft = new climberLeft();
  private intake intake = new intake();
  private taker taker = new taker();
  private shooter shooter = new shooter();

  private final arcadeDrive arcadeDriveC = new arcadeDrive(drivetrainS, joy_drive);
  
  private final SendableChooser<String> autoChooser = new SendableChooser<>();
  private final String defaultAuto = "SPEAKER NOMAS";
  private String autoSelected;
  private final String[] autoNames = {
    "NO AUTO", "SPEAKER AVANZA", "SPEAKER NOMAS", "4 NOTAS CENTRO","PIDTUNNER",
    "LINEA RECTA","TIMER"};

  public RobotContainer() {
    autoChooser.setDefaultOption("speaker nomas", defaultAuto);
    autoChooser.addOption("no auto", autoNames[0]);
    autoChooser.addOption("speaker avanza", autoNames[1]);
    autoChooser.addOption("Notas centro", autoNames[3]);
    autoChooser.addOption("PIDtunner", autoNames[4]);
    autoChooser.addOption("Linea Recta", autoNames[5]);
    autoChooser.addOption("doble nota", autoNames[6]);
    
    NamedCommands.registerCommand("intakeGoIntake", intake.goToPositionIntake(219));
    NamedCommands.registerCommand("intakeGoShooter", intake.goToPositionIntake(0));
    NamedCommands.registerCommand("intakeNeutro", intake.goToPositionIntake(60));
    NamedCommands.registerCommand("take", new take(taker));
    NamedCommands.registerCommand("shoot", new shoot(shooter, taker));

    SmartDashboard.putData("seleccion de auto", autoChooser);
    configureBindings();
  }

  private void configureBindings() {

    drivetrainS.setDefaultCommand(arcadeDriveC);
    
    joy_system.rightBumper().whileTrue(intake.goToPositionIntake(219).alongWith(new take(taker)))
    .whileFalse(intake.goToPositionIntake(60));
    joy_system.leftBumper().whileTrue(intake.goToPositionIntake(0).alongWith(new shoot(shooter, taker)))
    .whileFalse(intake.goToPositionIntake(60));
    joy_system.b().whileTrue(intake.goToPositionIntake(95).alongWith(new shootaIntake(taker)))
    .whileFalse(intake.goToPositionIntake(60));
    joy_system.a().whileTrue(new wheels(taker));

    joy_drive.leftBumper().whileTrue(climberLeft.leftMove(170)).whileFalse(climberLeft.leftStop());
    joy_drive.leftBumper().and(joy_drive.a()).whileTrue(climberLeft.leftMove(0)).whileFalse(climberLeft.leftStop());
    joy_drive.rightBumper().whileTrue(climberRight.rightMove(170)).whileFalse(climberRight.rightStop());
    joy_drive.rightBumper().and(joy_drive.a()).whileTrue(climberRight.rightMove(0)).whileFalse(climberRight.rightStop());
  }

  public Command getAutonomousCommand() {
    Command autonomusCommand = null;
    autoSelected = autoChooser.getSelected();
    System.out.println("auto selected: " + autoSelected);
    switch (autoSelected) {
      case "SPEAKER NOMAS":
        autonomusCommand = new speaker(shooter, taker, intake);
      break;
      case "NO AUTO" :
      autonomusCommand = null;
      break;
      case "SPEAKER AVANZA":
      autonomusCommand = new auto_front(shooter, taker, drivetrainS, intake);
      break;
      case "4 NOTAS CENTRO":
      autonomusCommand = new PathPlannerAuto("4NotasCentro");
      break;
      case "PIDTUNNER":
      autonomusCommand = new PathPlannerAuto("PIDTunner");
      break;
      case "LINEA RECTA":
      autonomusCommand = new PathPlannerAuto("Linea Recta");
      break;
      case "TIMER":
      autonomusCommand = new LineaBlanca(drivetrainS, shooter, intake, taker);
    }
    return autonomusCommand;

  }
}
