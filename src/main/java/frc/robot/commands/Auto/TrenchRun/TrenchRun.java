/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.TrenchRun;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.lib.Camera.BallSide;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.AlignWithVisionCommand;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.commands.Auto.DriveToCenterBallsCommand;
import frc.robot.commands.Auto.TurnToBallsCommand;
import frc.robot.commands.Auto.TurnToCenterBallsCommand;
import frc.robot.commands.Auto.WaitForBallCommand;
import frc.robot.commands.Auto.WaitForDistanceCommand;
import frc.robot.commands.Auto.WaitForShooterSpeedCommand;
import frc.robot.commands.Camera.BallDriveCommand;
import frc.robot.commands.Camera.ToggleLightsCommand;
import frc.robot.commands.Drive.TurnToAngleCommand;
import frc.robot.commands.Drive.TurnToAngleCommand.Direction;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Serializer.PowerSerializeCommand;
import frc.robot.commands.Serializer.SerializeCommand;
import frc.robot.commands.Shooter.CaseShootingCommand;
import frc.robot.commands.Teleop.ShootCommand;
import frc.robot.commands.Teleop.SpinUpCommand;
import frc.robot.commands.Throat.ThroatPowerCommand;
import frc.robot.commands.Turret.SetOffsetCommand;
import frc.robot.commands.Turret.TurretTrackingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ThroatSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TrenchRun extends ParallelCommandGroup {
  /**
   * Creates a new DriveToTrenchConmand.
   */

  final Waypoint[] k_2balls = { new Waypoint(-11, 15, Math.toRadians(90), 1, 2, 3, 2, 6),
      new Waypoint(-5, 17, Math.toRadians(40)) };

  public TrenchRun(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem,
      TurretSubsystem turretSubsystem, ThroatSubsystem throatSubsystem, IndexerSubsystem indexerSubsystem,
      SerializerSubsystem serializerSubsystem, Camera turretCamera, double shooterSpeed, DoubleSupplier getPosX,
      DoubleSupplier getPosY, Camera backCamera) {
    addCommands(
        new SpinUpCommand(turretSubsystem, turretCamera, shooterSubsystem, indexerSubsystem, shooterSpeed),
        new TurretTrackingCommand(turretSubsystem, turretCamera), new IntakeCommand(intakeSubsystem, 0.9),
        new PowerSerializeCommand(serializerSubsystem, 0.75), new ToggleLightsCommand(backCamera, true),
        new SequentialCommandGroup(
            new BackTrenchRunCommand(driveSubsystem, intakeSubsystem, shooterSubsystem, turretSubsystem,
                throatSubsystem, getPosX, getPosY, turretCamera),
            new SetOffsetCommand(turretSubsystem, 0),
            new FrontTrenchRunCommand(driveSubsystem, turretSubsystem, throatSubsystem, shooterSubsystem,
                intakeSubsystem, turretCamera),
            new ToggleLightsCommand(backCamera, true), new TurnToAngleCommand(driveSubsystem, -152, Direction.RIGHT, 0.25), 
            new WaitCommand(0.25), new BallDriveCommand(driveSubsystem, backCamera, 0.25, BallSide.LEFT, true, 38.5), new WaitCommand(1), new TurnToAngleCommand(driveSubsystem, -90, Direction.LEFT, 0.25)));
    // // new ToggleLightsCommand(frontCamera, true),
    // new ParallelDeadlineGroup(new WaitForBallCommand(frontCamera), new
    // CreatePathCommand(driveSubsystem, k_2balls, PathConfigs.fast, true, false,
    // true)),
    // new BallDriveCommand(driveSubsystem, frontCamera, -0.25)

  }
}
