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

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.armConstants;

public class intake extends ProfiledPIDSubsystem {
  /** Creates a new intake. */
  CANSparkMax intakeLeft = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax intakeRight = new CANSparkMax(6, MotorType.kBrushless);

  RelativeEncoder leftEncoder = intakeLeft.getEncoder();
  RelativeEncoder rightEncoder = intakeRight.getEncoder();

  SparkPIDController leftController;
  SparkPIDController rightController;

  DutyCycleEncoder dutyEncoder = new DutyCycleEncoder(0);

   private final ArmFeedforward m_feedforward = new ArmFeedforward(armConstants.kS, 
   armConstants.kG, 
   armConstants.kV, 
   armConstants.kA);


  public intake() {
    super(new ProfiledPIDController(
              armConstants.kP, 
              armConstants.ki, 
              armConstants.kD, 
            new TrapezoidProfile.Constraints(
              armConstants.kMaxVelocity, 
              armConstants.kMaxAcceleration)), 
            (armConstants.SHOOTER_POSITION));

    dutyEncoder.setDistancePerRotation(360);
    setGoal(armConstants.SHOOTER_POSITION);

    dutyEncoder.setDutyCycleRange(0, 1);

    dutyEncoder.setPositionOffset(armConstants.OFFSET / 360);

    intakeLeft.setSmartCurrentLimit(40);
    intakeRight.setSmartCurrentLimit(40);

    intakeRight.setInverted(false);
    intakeLeft.follow(intakeRight, true);

    intakeLeft.setIdleMode(IdleMode.kCoast);
    intakeRight.setIdleMode(IdleMode.kCoast);

    leftEncoder.setPosition((-dutyEncoder.get()  ) / armConstants.FACTOR_CONVERSION_INTAKE);
    rightEncoder.setPosition((-dutyEncoder.get() ) / armConstants.FACTOR_CONVERSION_INTAKE);

    leftController = intakeLeft.getPIDController();
    rightController = intakeRight.getPIDController();

    leftController.setP(armConstants.kP);
    leftController.setI(0);
    leftController.setD(armConstants.kD);
    leftController.setFF(0);

    rightController.setP(0);
    rightController.setI(0);
    rightController.setD(0);
    rightController.setFF(0);

    SmartDashboard.putNumber("kP Brazo", armConstants.kP);
    SmartDashboard.putNumber("kFF Brazo", armConstants.kFF);
    SmartDashboard.putNumber("kD Brazo", armConstants.kD);
    SmartDashboard.putNumber("kI Brazo", armConstants.ki);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    super.periodic();
    SmartDashboard.putNumber("el angulo actual es", getMeasurement());
    SmartDashboard.putNumber("angulo izquierdo", leftEncoder.getPosition() * armConstants.FACTOR_CONVERSION_INTAKE);
    SmartDashboard.putNumber("angulo derecho", rightEncoder.getPosition() * armConstants.FACTOR_CONVERSION_INTAKE);

    double kP = 0, kFF = 0, kI = 0, kD = 0;
    double p = SmartDashboard.getNumber("kP Brazo", 0);
    double i = SmartDashboard.getNumber("kI Brazo", 0);
    double d = SmartDashboard.getNumber("kD Brazo", 0);
    double ff = SmartDashboard.getNumber("kFF Brazo", 0);

    if((p != kP)) { leftController.setP(p); rightController.setP(p); kP = p; }
    if((i != kI)) { leftController.setI(i); rightController.setP(i);kI = i; }
    if((d != kD)) { leftController.setD(d); rightController.setP(d);kD = d; }
    if((ff != kFF)) { leftController.setFF(ff); rightController.setP(ff);kFF = ff; }
    }

  @Override
  public void useOutput(double output, State setpoint) { 
    // Calculate the feedforward from the sepoint
    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
    // Add the feedforward to the PID output to get the motor output
    intakeLeft.setVoltage(output + feedforward);

  }

  @Override
  public double getMeasurement() {
    return dutyEncoder.getDistance();
  }

  public Command doNothing(){
    Command ejecutable = Commands.run(
      () -> {
        intakeLeft.set(0);
        intakeRight.set(0);
      },
    this);
    return ejecutable;
  }

  public void goToPosition(double position){
    leftController.setReference(position / armConstants.FACTOR_CONVERSION_INTAKE, ControlType.kPosition);
    rightController.setReference(position / armConstants.FACTOR_CONVERSION_INTAKE, ControlType.kPosition);
  }

  public Command goToPositionIntake(double position){
    Command ejecutable = Commands.run(
      () -> {
        this.goToPosition(position);
      }, this);
      return ejecutable;
  }
}
