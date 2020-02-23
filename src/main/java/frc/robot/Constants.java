/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static PracticeType m_pidTerms = new PracticeType();
    // public static FinalType m_pidTerms = new FinalType();
    public static int k_leftDrive = 1;
    public static int k_leftFollower = 2;
    public static int k_rightDrive = 3;
    public static int k_rightFollower = 4;
    public static int k_intake = 5;
    public static int k_serializer = 6;
    public static int k_throat = 7;
    public static int k_turret = 8;
    public static int k_indexer = 9;
    public static int k_shooter = 10;
    public static int k_shooterFollower = 11;
    public static int k_snoot = 12;
    public static int k_climb = 14;
    public static int k_climbFollower = 13;

    public static int k_brake = 6;
    public static int k_intakeDeploy = 7;

    public static final int k_dioTop = 2;
    public static final int k_dioBottom = 3;
}
