/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.RightBallRunCameraAlign;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.robot.commands.Auto.FireCommandAuto;
import frc.robot.commands.Teleop.FireCommand;
import frc.robot.commands.Throat.ThroatPowerCommand;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ThroatSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class FireFromCenter extends ParallelDeadlineGroup {
  /**
   * Creates a new FireFromCenter.
   */
  public FireFromCenter(ThroatSubsystem throatSubsystem, TurretSubsystem turretSubsystem, ShooterSubsystem shooterSubsystem, double deadzone,
      Camera turretCamera) {
    // Add your commands in the super() call. Add the deadline first.
    super(new FireCommandAuto(throatSubsystem, turretSubsystem, shooterSubsystem, turretCamera, deadzone));
  }
}
