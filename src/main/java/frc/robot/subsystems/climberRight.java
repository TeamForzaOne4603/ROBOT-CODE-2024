// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.climber;

public class climberRight extends SubsystemBase {
  CANSparkMax rightMotor = new CANSparkMax(11, MotorType.kBrushless);

  RelativeEncoder rightEncoder = rightMotor.getEncoder();
  
  SparkPIDController rightController;

  public climberRight() {
    rightMotor.setSmartCurrentLimit(40);

    rightMotor.setInverted(true);

    rightMotor.setIdleMode(IdleMode.kBrake);

    rightController = rightMotor.getPIDController();

    resetEncoder();

    rightEncoder.setPositionConversionFactor(climber.FACTOR_CONVERSION_CLIMBER);
    rightEncoder.setVelocityConversionFactor(climber.FACTOR_CONVERSION_CLIMBER);

    rightController.setP(climber.kP);
    rightController.setI(climber.kI);
    rightController.setD(climber.kD);
    rightController.setFF(climber.kFF);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("climber derecho", rightEncoder.getPosition());
  }

  public void goToPosition(double position){
    rightController.setReference(position, ControlType.kPosition);
  }
  public Command rightStop(){
    Command ejecutable = Commands.run(
      () -> {
        rightMotor.set(0);
      }, this);
      return ejecutable;
  }
  
  public Command rightMove(double postion){
    Command ejecutable = Commands.run(
      () -> {
        this.goToPosition(postion);
      }, this);
      return ejecutable;
  }
  public void resetEncoder(){
    rightEncoder.setPosition(0.75);
  }
}
