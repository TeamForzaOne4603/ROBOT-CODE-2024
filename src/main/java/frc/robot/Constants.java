// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class MotorControllers{
    public static final int leftFollower = 5;
    public static final int leftLeader = 4;
    public static final int rightLeader = 2;
    public static final int rightFollower = 3;
  }

  public static class armConstants {
    public static final double kMaxVelocity = 0,
                               kMaxAcceleration = 0,
                               kP = 0.00,
                               ki = 0,
                               kD = 0,  
                               kFF = 0.035,
                               kS = 0,
                               kV = 0,
                               kA = 0,
                               kG = 0;
    public static final double SHOOTER_POSITION = 0;
    public static final double AMP_POSITION = 0;               
    public static final double INTAKE_POSITION = 0;

    public static final double FACTOR_CONVERSION_INTAKE = 4.809105190124189;

    public static final double OFFSET = 164;
    
    public static final double iVelocity = .1;
    public static final double sVelocity = -.1;
  }

  public static class climber {
    public static final double FACTOR_CONVERSION_CLIMBER = 1 / 36;

    public static final double kP = 0.018,
                               kI = 0,
                               kD = 0.015,
                               kFF = .0036;
  }

  public static class chassis {
    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kFF = 0,
                                vueltaPorVueltas = 6.56;

    public static final double conversionFac = (1 / 8.45) / vueltaPorVueltas;
  }
  

  }
