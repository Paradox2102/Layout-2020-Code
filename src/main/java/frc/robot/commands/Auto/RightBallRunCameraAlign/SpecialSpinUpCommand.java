/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.RightBallRunCameraAlign;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Indexer.IndexCommand;
import frc.robot.commands.Shooter.ShooterSpeedCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SpecialSpinUpCommand extends ParallelCommandGroup {
  /**
   * Creates a new SpecialSpinUpCommand.
   */
  public SpecialSpinUpCommand(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, double shooterSpeed) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new ShooterSpeedCommand(shooterSubsystem, shooterSpeed), new IndexCommand(indexerSubsystem, 0.5));
  }
}
