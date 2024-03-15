// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooter extends SubsystemBase {
   CANSparkMax leftMotor = new CANSparkMax(10, MotorType.kBrushless);
   CANSparkMax rightMotor = new CANSparkMax(9, MotorType.kBrushless);
  public shooter() {
    leftMotor.setSmartCurrentLimit(40);
    rightMotor.setSmartCurrentLimit(40);

    rightMotor.setInverted(false);
    leftMotor.follow(rightMotor, true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shooterMove(double speed){
    rightMotor.set(speed);
  }

  public void stop(){
    rightMotor.stopMotor();
  }
}
