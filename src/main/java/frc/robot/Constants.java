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

    public static final double FACTOR_CONVERSION = 4.809105190124189;

    public static final double OFFSET = 164;
    
    public static final double iVelocity = .1;
    public static final double sVelocity = -.1;
  }

  public static class velocity {
    
  }

  }
