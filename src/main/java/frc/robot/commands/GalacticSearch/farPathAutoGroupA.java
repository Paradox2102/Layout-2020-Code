/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.GalacticSearch;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.robot.commands.Camera.ToggleLightsCommand;
import frc.robot.commands.Drive.SmoothTurnCommand;
import frc.robot.commands.Drive.TurnByAngleCommand;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class farPathAutoGroupA extends SequentialCommandGroup {
  /**
   * Creates a new farPathAuto.
   */
  public farPathAutoGroupA(Camera camera, DriveSubsystem driveSubsystem, double searchPower, double turnPower) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(new ToggleLightsCommand(camera, true), new driveToBallCommand(camera, driveSubsystem, searchPower),
        // new TurnByAngleCommand(driveSubsystem, -60, turnPower),
        new SmoothTurnCommand(driveSubsystem, -60, -turnPower, 0.25),
        new driveToBallCommand(camera, driveSubsystem, searchPower),
        // new TurnByAngleCommand(driveSubsystem, 30, turnPower),
        new SmoothTurnCommand(driveSubsystem, 75, -turnPower, 0.28),
        new driveToBallCommand(camera, driveSubsystem, searchPower),
        new driveToBallCommand(camera, driveSubsystem, searchPower));
  }
}
