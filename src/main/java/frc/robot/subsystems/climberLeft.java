// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class climberLeft extends SubsystemBase {
   CANSparkMax leftMotor = new CANSparkMax(12, MotorType.kBrushless);
  public climberLeft() {
    leftMotor.setSmartCurrentLimit(40);

    leftMotor.setInverted(false);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void leftMove(double speed){
    leftMotor.set(speed);
  }
  public void leftStop(){
    leftMotor.stopMotor();
  }
}
