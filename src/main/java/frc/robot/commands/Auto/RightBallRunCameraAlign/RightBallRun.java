/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.RightBallRunCameraAlign;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.lib.Camera.BallSide;
import frc.robot.commands.Auto.FireCommandAuto;
import frc.robot.commands.Auto.TurnToBallsCommand;
import frc.robot.commands.Camera.BallDriveCommand;
import frc.robot.commands.Camera.ToggleLightsCommand;
import frc.robot.commands.Drive.TurnToAngleCommand;
import frc.robot.commands.Drive.TurnToAngleCommand.Direction;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Shooter.SetTrimCommand;
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

  public RightBallRun(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, TurretSubsystem turretSubsystem,
      Camera turretCamera, ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, double shooterSpeed,
      ThroatSubsystem throatSubsystem, Camera backCamera) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(new IntakeCommand(intakeSubsystem, 0.85),
        new SpecialSpinUpCommand(shooterSubsystem, indexerSubsystem, 39000), new ToggleLightsCommand(backCamera, true),
        new TurretTrackingCommand(turretSubsystem, turretCamera),
        new SequentialCommandGroup(new TwoBallRun(driveSubsystem, backCamera),
            new BallDriveCommand(driveSubsystem, backCamera, 0.35, BallSide.LEFT, true, 5), new WaitCommand(0.5),
            new DriveToCenter(driveSubsystem),
            new ParallelDeadlineGroup(new WaitCommand(2),
                new FireCommandAuto(throatSubsystem, turretSubsystem, shooterSubsystem, turretCamera, 50)),
            new TurnToAngleCommand(driveSubsystem, -70, Direction.LEFT, 0.3), new WaitCommand(0.25),
            new BallDriveCommand(driveSubsystem, backCamera, 0.35, BallSide.RIGHT, true, 3),
            new TurnToAngleCommand(driveSubsystem, -115, Direction.RIGHT, 0.3), new WaitCommand(0.25),
            new FireCommandAuto(throatSubsystem, turretSubsystem, shooterSubsystem, turretCamera, 50)));
    // new FireFromCenter(throatSubsystem, turretSubsystem, shooterSubsystem, 50,
    // turretCamera),
    // new TurnToBallsCommand(driveSubsystem, turretCamera, power)));
  }
}
