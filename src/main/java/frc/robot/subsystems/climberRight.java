// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class climberRight extends SubsystemBase {
  CANSparkMax rightMotor = new CANSparkMax(12, MotorType.kBrushless);
  
  public climberRight() {
    rightMotor.setSmartCurrentLimit(40);

    rightMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void rightMove(double speed){
    rightMotor.set(speed);
  }

  public void rightStop(){
    rightMotor.stopMotor();
  }
}
