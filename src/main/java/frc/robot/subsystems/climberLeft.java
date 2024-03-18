// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.climber;

public class climberLeft extends SubsystemBase {
   CANSparkMax leftMotor = new CANSparkMax(12, MotorType.kBrushless);

   RelativeEncoder leftEncoder = leftMotor.getEncoder();

   SparkPIDController leftController;

  public climberLeft() {
    leftMotor.setSmartCurrentLimit(40);

    leftMotor.setInverted(false);

    leftMotor.setIdleMode(IdleMode.kBrake);

    leftController = leftMotor.getPIDController();

    resetEncoder();

    leftEncoder.setPositionConversionFactor(climber.FACTOR_CONVERSION_CLIMBER);
    leftEncoder.setVelocityConversionFactor(climber.FACTOR_CONVERSION_CLIMBER);

    leftController.setP(climber.kP);
    leftController.setI(climber.kI);
    leftController.setD(climber.kD);
    leftController.setFF(climber.kFF);

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("posicion izquierda", leftEncoder.getPosition());
  }

  public void goToPosition(double position){
    leftController.setReference(position, ControlType.kPosition);
  } 

  public Command leftStop(){
    Command ejecutable = Commands.run(
      () -> {
        leftMotor.set(0);
      }, this);
      return ejecutable;
  }
  
  public Command leftMove(double postion){
    Command ejecutable = Commands.run(
      () -> {
        this.goToPosition(postion);
      }, this);
      return ejecutable;
  }
  public void resetEncoder(){
    leftEncoder.setPosition(8);
  }
}
