/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Set;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.Camera;
import frc.robot.PositionTracker.PositionContainer;
import frc.robot.commands.Auto.TrenchRun;
import frc.robot.commands.Camera.BallDriveCommand;
import frc.robot.commands.Drive.ArcadeDriveCommand;
import frc.robot.commands.Drive.SpeedCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.TurretSubsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  TurretSubsystem m_turretSubsystem = new TurretSubsystem();

  Camera m_camera = new Camera();

  Joystick m_stick = new Joystick(0);

  JoystickButton m_calibrateBtn = new JoystickButton(m_stick, 1);
  JoystickButton m_trackBalls = new JoystickButton(m_stick, 2);

  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    m_camera.connect("10.21.2.12");

    configureButtonBindings();

    m_driveSubsystem.setDefaultCommand(new ArcadeDriveCommand(m_driveSubsystem, () -> m_stick.getX(),() ->  -m_stick.getY()));

    m_chooser.setDefaultOption("Do Nothing", new Command(){
    
      @Override
      public Set<Subsystem> getRequirements() {
        // TODO Auto-generated method stub
        return null;
      }
    });

    m_chooser.addOption("Trench Run", new TrenchRun(m_driveSubsystem, m_camera));
    // m_chooser.addOption("Print 10 ft", new PrintPathCommand(m_driveSubsystem, drive10Ft, PurePursuit.PathConfigs.fast));
    SmartDashboard.putData("Auto mode", m_chooser);  
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_calibrateBtn.whileHeld(new SpeedCommand(m_driveSubsystem, 11.94));
    m_trackBalls.toggleWhenPressed(new BallDriveCommand(m_driveSubsystem, m_camera, -0.25));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }

  public void startPosTracking(){
    m_driveSubsystem.startPosUpdate();
  }
  
  public void stopPosTracking(){
    m_driveSubsystem.stopPosUpdate();
  }
  
  public void setPos(double x, double y){
    m_driveSubsystem.setPos(x, y);
  }

  public void  setAngle(double angle){
    m_driveSubsystem.resetAngle(angle);
  }

  public PositionContainer getPos(){
    return m_driveSubsystem.getPos();
  }
  
  public double getAngle(){
    return m_driveSubsystem.getAngle();
  }
}
