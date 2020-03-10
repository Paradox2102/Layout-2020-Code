/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.InnerPortRun;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.Camera;
import frc.robot.commands.Auto.FireCommandAuto;
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
public class InnerPortRunCommand extends ParallelCommandGroup {
  /**
   * Creates a new InnerPortRunCommand.
   */
  public InnerPortRunCommand(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, 
                             IndexerSubsystem indexerSubsystem, TurretSubsystem turretSubsystem, ThroatSubsystem throatSubsystem,
                             Camera turretCamera, Camera backCamera, double shooterSpeed) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new IntakeCommand(intakeSubsystem, 0.85),
          new SpinUpCommand(turretSubsystem, turretCamera, shooterSubsystem, indexerSubsystem, shooterSpeed),
          new SetTrimCommand(shooterSubsystem, 1000),
          new TurretTrackingCommand(turretSubsystem, turretCamera), 
          new SequentialCommandGroup(new BackUpCommand(driveSubsystem),
          new FireCommandAuto(throatSubsystem, turretSubsystem, shooterSubsystem, turretCamera, 50)));
  }
}
