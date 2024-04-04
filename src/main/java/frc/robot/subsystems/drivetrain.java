// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.ReplanningConfig;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort.Port;

import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.chassis;

public class drivetrain extends SubsystemBase {
  CANSparkMax rightLeader = new CANSparkMax(MotorControllers.rightLeader, MotorType.kBrushless);
  CANSparkMax leftLeader = new CANSparkMax(MotorControllers.leftLeader, MotorType.kBrushless);
  CANSparkMax rightFollower = new CANSparkMax(MotorControllers.rightFollower, MotorType.kBrushless);
  CANSparkMax leftFollower = new CANSparkMax(MotorControllers.leftFollower, MotorType.kBrushless);

  //AHRS gyro = new AHRS(Port.kUSB);

  RelativeEncoder leftEncoder = leftLeader.getEncoder();
  RelativeEncoder rightEncoder = rightLeader.getEncoder();

 /*  PIDController lefController = new PIDController(chassis.kPA, chassis.kIA, chassis.kDA);
  PIDController righController = new PIDController(chassis.kPG, chassis.kIG, chassis.kDG);*/

  DifferentialDrive drive = new DifferentialDrive(leftLeader, rightLeader);

  SparkPIDController leftcController = leftLeader.getPIDController();
  SparkPIDController righController = rightLeader.getPIDController();


  DifferentialDriveOdometry odometry;

  DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(chassis.kTrack);

  SimpleMotorFeedforward feedforward;

  
  
  public drivetrain() {

    leftLeader.setSmartCurrentLimit(40);
    rightLeader.setSmartCurrentLimit(40);
    leftFollower.setSmartCurrentLimit(40); 
    rightFollower.setSmartCurrentLimit(40);

    leftLeader.setIdleMode(IdleMode.kBrake);
    rightLeader.setIdleMode(IdleMode.kBrake);
    leftFollower.setIdleMode(IdleMode.kBrake);
    rightFollower.setIdleMode(IdleMode.kBrake);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    leftLeader.setInverted(false);
    rightLeader.setInverted(true);

    leftLeader.setOpenLoopRampRate(.08); 
    leftFollower.setOpenLoopRampRate(.08);
    rightFollower.setOpenLoopRampRate(.08);
    rightLeader.setOpenLoopRampRate(.08);

    leftEncoder.setPositionConversionFactor((1 / chassis.kGearRatio) * (chassis.wheelDiameter * Math.PI));
    rightEncoder.setPositionConversionFactor((1 / chassis.kGearRatio) * (chassis.wheelDiameter * Math.PI));

    leftEncoder.setVelocityConversionFactor(((1 / chassis.kGearRatio) * (chassis.wheelDiameter * Math.PI)) / 60);
    leftEncoder.setVelocityConversionFactor(((1 / chassis.kGearRatio) * (chassis.wheelDiameter * Math.PI)) / 60);

    resetEncoders();

    //resetYaw();

   /* odometry = new DifferentialDriveOdometry(
    Rotation2d.fromDegrees(gyro.getAngle()), 
    leftEncoder.getPosition(), 
    rightEncoder.getPosition());*/

    feedforward = new SimpleMotorFeedforward(
    chassis.kS, 
    chassis.kV, 
    chassis.kA);

    leftcController.setP(3.2581);

    righController.setP(3.2581);

    /*righController.setP(chassis.kPG);
    righController.setI(chassis.kIG);
    righController.setD(chassis.kDG);

    lefController.setP(chassis.kPA);
    lefController.setI(chassis.kIA);
    lefController.setD(chassis.kDA);

    SmartDashboard.putNumber("kPy", chassis.kPG);
    SmartDashboard.putNumber("kDy", chassis.kDG);
    SmartDashboard.putNumber("kIy", chassis.kIG);

    SmartDashboard.putNumber("kPx", chassis.kPA);
    SmartDashboard.putNumber("kDx", chassis.kDA);
    SmartDashboard.putNumber("kIx", chassis.kIA);

    AutoBuilder.configureRamsete(
     this::getPose,
     this::resetOdometry, 
     this::getChassisSpeeds, 
     this::driveChassisSpeeds, 
     new ReplanningConfig(),
     () -> {
      var alliance = DriverStation.getAlliance();
      if(alliance.isPresent()){
        return alliance.get() == DriverStation.Alliance.Red;
      }
      return false;
     }, this
     );*/
  }

  public void arcadeDrive(double speed, double rotation){
    drive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
  SmartDashboard.putNumber("llanta izquierda", leftEncoder.getPosition());
  SmartDashboard.putNumber("llanta derecha", rightEncoder.getPosition());

  /*double kPx = 0, kIx = 0, kDx = 0;
    double px = SmartDashboard.getNumber("kPx", 0);
    double ix = SmartDashboard.getNumber("kIx", 0);
    double dx = SmartDashboard.getNumber("kDx", 0);

    if((px != kPx)) {lefController.setP(px);kPx = px; }
    if((ix != kIx)) {lefController.setP(ix);kIx = ix; }
    if((dx != kDx)) {lefController.setP(dx);kDx = dx; }

  double kPy = 0, kIy = 0, kDy = 0;
    double py = SmartDashboard.getNumber("kPy", 0);
    double iy = SmartDashboard.getNumber("kIy", 0);
    double dy = SmartDashboard.getNumber("kDy", 0);

    if((py != kPy)) {righController.setP(py); kPy = py; }
    if((iy != kIy)) {righController.setP(iy);kIy = iy; }
    if((dy != kDy)) {righController.setP(dy);kDy = dy; }

  odometry.update(
  Rotation2d.fromDegrees(gyro.getAngle()), 
  leftEncoder.getPosition(), 
  rightEncoder.getPosition());*/
  }

  public void resetEncoders(){
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  /*public void resetOdometry(Pose2d pose){
    resetEncoders();
    gyro.reset();
    odometry.resetPosition(
      Rotation2d.fromDegrees(gyro.getAngle()), 
      leftEncoder.getPosition(), 
      rightEncoder.getPosition(), 
      pose);
  }*/

  public void setOdometry(){
  
  }

 /* public void resetYaw(){
    gyro.reset();
  }*/


  /*public Pose2d getPose(){
    return odometry.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds(){
    return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
  }

public ChassisSpeeds getChassisSpeeds(){
  return kinematics.toChassisSpeeds(getWheelSpeeds());
} 

public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
  /*DashboardHelper.putNumber(DashboardHelper.LogLevel.Important, "left m/s", speeds.leftMetersPerSecond);
  DashboardHelper.putNumber(DashboardHelper.LogLevel.Important, "right m/s", speeds.rightMetersPerSecond);
  final double leftFeedforward = 
      feedforward.calculate(speeds.leftMetersPerSecond);
  final double rightFeedforward = 
      feedforward.calculate(speeds.rightMetersPerSecond);
  // final double leftFeedforward = 0;
  // final double rightFeedforward = 0;
  final double leftOutput =
      lefController.calculate(getLeftVelocity(), speeds.leftMetersPerSecond);
  final double rightOutput =
      righController.calculate(getRightVelocity(), speeds.rightMetersPerSecond);
  /*DashboardHelper.putNumber(DashboardHelper.LogLevel.Important, "right pid output", rightOutput);
  DashboardHelper.putNumber(DashboardHelper.LogLevel.Important, "left pid output", leftOutput);
  
  setMotorVoltage(leftOutput + leftFeedforward, rightOutput + rightFeedforward);    
}

public void setMotorVoltage(double voltage1, double voltage2){
  rightLeader.setVoltage(voltage1);
  leftLeader.setVoltage(voltage2);
}

public double getLeftVelocity(){
  return leftEncoder.getVelocity();
}
public double getRightVelocity(){
  return rightEncoder.getVelocity();
}
public void driveChassisSpeeds(ChassisSpeeds chassisSpeeds) {
  setSpeeds(kinematics.toWheelSpeeds(chassisSpeeds));
}*/

public void leftGo(double position){
  leftcController.setReference(position, ControlType.kPosition);
}

public void rightGo(double position){
  righController.setReference(position, ControlType.kPosition);
}

}
