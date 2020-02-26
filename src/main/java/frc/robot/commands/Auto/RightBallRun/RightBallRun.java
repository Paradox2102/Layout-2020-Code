/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.RightBallRun;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.robot.commands.Intake.IntakeCommand;
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
public class RightBallRun extends ParallelCommandGroup {
  /**
   * Creates a new RightBallRun.
   */
  double k_offset = 0;

  public RightBallRun(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, double power,
      TurretSubsystem turretSubsystem, Camera turretCamera, ShooterSubsystem shooterSubsystem,
      IndexerSubsystem indexerSubsystem, double shooterSpeed, ThroatSubsystem throatSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(new IntakeCommand(intakeSubsystem, power),
        new SpinUpCommand(turretSubsystem, turretCamera, shooterSubsystem, indexerSubsystem, shooterSpeed),
        new TurretTrackingCommand(turretSubsystem, turretCamera),
        new SequentialCommandGroup(new TwoBallRun(driveSubsystem), new WaitCommand(0.5),
            new ParallelDeadlineGroup(new DriveToCenter(driveSubsystem),
                new FireFromCenter(throatSubsystem, turretSubsystem, shooterSubsystem, 50, turretCamera)),
            new TurnToBallsCommand(driveSubsystem, turretCamera, power)));
  }
}
