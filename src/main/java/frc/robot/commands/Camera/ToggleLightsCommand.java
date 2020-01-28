/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Camera;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.lib.Camera;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ToggleLightsCommand extends InstantCommand {
  Camera m_camera;
  boolean m_on;
  public ToggleLightsCommand(Camera camera, boolean on) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_camera = camera;
    m_on = on;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_camera.toggleLights(m_on);
  }
}
