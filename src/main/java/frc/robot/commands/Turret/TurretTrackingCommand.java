/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Camera;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.TurretSubsystem;

public class TurretTrackingCommand extends CommandBase {
  /**
   * Creates a new TurretTrackingCommand.
   */
  TurretSubsystem m_subsystem;
  Camera m_camera;
  boolean regionsSeen = false;
  double k_p = 0.0005;
  double k_x = 1.7;
  double k_deadZone = 0;
  boolean isFinished = false;
  public TurretTrackingCommand(TurretSubsystem subsystem, Camera camera) {
    m_subsystem = subsystem;
    m_camera = camera;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_camera.toggleLights(true);
    regionsSeen = false;
    isFinished = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    CameraData data = m_camera.createData();
    if(!regionsSeen && data.canSee()){
      regionsSeen = true;
    }

    if(regionsSeen && data.canSee()){
      System.out.println(data.centerLine());
      double centerDiff = data.centerDiff(data.centerLine());
      System.out.println(centerDiff);

      if(Math.abs(centerDiff) > k_deadZone){
        double power = k_p * Math.pow(Math.abs(centerDiff), k_x);
        if(centerDiff < 0){
          power *= -1;
        }
        m_subsystem.setPower(power);
      }else{
        m_subsystem.stop();
        isFinished = true;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
    m_camera.toggleLights(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if(regionsSeen){
    //   if(!m_camera.createData().canSee()){
    //   }
    //   return !m_camera.createData().canSee();
    // }
    return false;
  }
}