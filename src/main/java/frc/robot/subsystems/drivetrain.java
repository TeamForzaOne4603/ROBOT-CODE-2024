// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort.Port;

import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.chassis;

public class drivetrain extends SubsystemBase {
  CANSparkMax rightLeader = new CANSparkMax(MotorControllers.rightLeader, MotorType.kBrushless);
  CANSparkMax leftLeader = new CANSparkMax(MotorControllers.leftLeader, MotorType.kBrushless);
  CANSparkMax rightFollower = new CANSparkMax(MotorControllers.rightFollower, MotorType.kBrushless);
  CANSparkMax leftFollower = new CANSparkMax(MotorControllers.leftFollower, MotorType.kBrushless);

  AHRS gyro = new AHRS(Port.kUSB1);

  RelativeEncoder leftEncoder = leftLeader.getEncoder();
  RelativeEncoder rightEncoder = rightLeader.getEncoder();

  SparkPIDController leftController = leftLeader.getPIDController();
  SparkPIDController rightController = rightLeader.getPIDController();

  DifferentialDrive drive = new DifferentialDrive(leftLeader, rightLeader);
  
  public drivetrain() {
    leftLeader.setSmartCurrentLimit(40);
    rightLeader.setSmartCurrentLimit(40);
    leftFollower.setSmartCurrentLimit(40); 
    rightFollower.setSmartCurrentLimit(40);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    leftLeader.setInverted(false);
    rightLeader.setInverted(true);


    leftLeader.setOpenLoopRampRate(.08); 
    leftFollower.setOpenLoopRampRate(.08);
    rightFollower.setOpenLoopRampRate(.08);
    rightLeader.setOpenLoopRampRate(.08);

    leftEncoder.setPositionConversionFactor(chassis.conversionFac);
    rightEncoder.setPositionConversionFactor(chassis.conversionFac);

    leftEncoder.setVelocityConversionFactor(chassis.conversionFac);
    leftEncoder.setVelocityConversionFactor(chassis.conversionFac);

    resetEncoders();

    gyro.reset();

    leftController.setP(chassis.kP);
    leftController.setI(chassis.kI);
    leftController.setD(chassis.kD);
    leftController.setFF(chassis.kFF);

    rightController.setP(chassis.kP);
    rightController.setI(chassis.kI);
    rightController.setD(chassis.kD);
    rightController.setFF(chassis.kFF);

    /*SmartDashboard.putNumber("kP chassis", chassis.kP);
    SmartDashboard.putNumber("kFF chassis", chassis.kFF);
    SmartDashboard.putNumber("kD chassis", chassis.kD);
    SmartDashboard.putNumber("kI chassis", chassis.kI);*/
  }

  public void arcadeDrive(double speed, double rotation){
    drive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
  SmartDashboard.putNumber("llanta izquierda", leftEncoder.getPosition());
  SmartDashboard.putNumber("llanta derecha", rightEncoder.getPosition());
  }

  public void resetEncoders(){
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  
}
