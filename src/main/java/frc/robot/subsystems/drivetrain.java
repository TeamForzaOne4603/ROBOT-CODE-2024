// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorControllers;

public class drivetrain extends SubsystemBase {
  CANSparkMax rightLeader = new CANSparkMax(MotorControllers.rightLeader, MotorType.kBrushless);
  CANSparkMax leftLeader = new CANSparkMax(MotorControllers.leftLeader, MotorType.kBrushless);
  CANSparkMax rightFollower = new CANSparkMax(MotorControllers.rightFollower, MotorType.kBrushless);
  CANSparkMax leftFollower = new CANSparkMax(MotorControllers.leftFollower, MotorType.kBrushless);
  
  RelativeEncoder leftEncoder = leftLeader.getEncoder();
  RelativeEncoder rightEncoder = rightLeader.getEncoder();

  PIDController PID = new PIDController(0, 0, 0);

  DifferentialDrive drive = new DifferentialDrive(leftLeader, rightLeader);
  /*     (double output) -> {
      leftLeader.set(output);
      leftFollower.set(output);
  },
  (double output) -> {
      rightLeader.set(output);
      rightFollower.set(output);
  });*/
  

  public drivetrain() {
    leftLeader.setSmartCurrentLimit(35);
    rightLeader.setSmartCurrentLimit(35);
    leftFollower.setSmartCurrentLimit(35);
    rightFollower.setSmartCurrentLimit(35);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    leftLeader.setInverted(false);
    rightLeader.setInverted(true);


    leftLeader.setOpenLoopRampRate(.08); 
    leftFollower.setOpenLoopRampRate(.08);
    rightFollower.setOpenLoopRampRate(.08);
    rightLeader.setOpenLoopRampRate(.08);
  }

  public void arcadeDrive(double speed, double rotation){
    drive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("voltaje adelante", leftLeader.getBusVoltage());
    SmartDashboard.putNumber("voltaje detras", leftFollower.getBusVoltage());
    SmartDashboard.putNumber("corriente adelante", leftFollower.getOutputCurrent());
    SmartDashboard.putNumber("corriente detras", leftFollower.getOutputCurrent());
  }
}
