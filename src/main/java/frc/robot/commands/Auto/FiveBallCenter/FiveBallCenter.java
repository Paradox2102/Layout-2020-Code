/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.FiveBallCenter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.robot.commands.Auto.AlignWithVisionCommand;
import frc.robot.commands.Auto.FireCommandAuto;
import frc.robot.commands.Drive.TinyTurnCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Teleop.FireCommand;
import frc.robot.commands.Teleop.SpinUpCommand;
import frc.robot.commands.Turret.TurretTrackingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ThroatSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class FiveBallCenter extends ParallelCommandGroup {
  /**
   * Creates a new FiveBallCenter.
   */
  double k_offset = 0;

  public FiveBallCenter(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, Camera turretCamera,
      double power, TurretSubsystem turretSubsystem, ShooterSubsystem shooterSubsystem,
      IndexerSubsystem indexerSubsystem, double speed, ThroatSubsystem throatSubsystem) {
    addCommands(new SpinUpCommand(turretSubsystem, turretCamera, shooterSubsystem, indexerSubsystem, speed),
        new IntakeCommand(intakeSubsystem, 0.6),
        new SequentialCommandGroup(new FrontBallsRun(driveSubsystem),
            new AlignWithVisionCommand(driveSubsystem, turretCamera, power), new WaitCommand(0.5),
            new ParallelCommandGroup(new TurretTrackingCommand(turretSubsystem, turretCamera, k_offset),
                new FireCommandAuto(throatSubsystem, shooterSubsystem, turretCamera, 50, k_offset))));
  }
}
