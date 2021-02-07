/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.GalacticSearch;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.robot.commands.Camera.ToggleLightsCommand;
import frc.robot.commands.Drive.DriveByDistanceCommand;
import frc.robot.commands.Drive.TurnByAngleCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class closePathAutoGroupA extends ParallelCommandGroup {
  /**
   * Creates a new driveToBallAuto.
   */
  public closePathAutoGroupA(Camera camera, DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem,
      double searchPower, double turnPower) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(/* new IntakeCommand(intakeSubsystem, 0.9), */
        new SequentialCommandGroup(new driveToBallCommand(camera, driveSubsystem, searchPower), new WaitCommand(3),
            new driveToBallCommand(camera, driveSubsystem, searchPower), new WaitCommand(3),
            new TurnByAngleCommand(driveSubsystem, -60, turnPower),
            new driveToBallCommand(camera, driveSubsystem, searchPower), new WaitCommand(3),
            new TurnByAngleCommand(driveSubsystem, 45, turnPower),
            new DriveByDistanceCommand(driveSubsystem, 3, -searchPower),
            new driveToBallCommand(camera, driveSubsystem, searchPower)));
  }
}
