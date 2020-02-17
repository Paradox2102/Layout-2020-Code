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
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.Camera;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PositionTracker.PositionContainer;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.commands.Auto.TrenchRun.TrenchRun;
import frc.robot.commands.Camera.BallDriveCommand;
import frc.robot.commands.Climber.MoveClimberCommand;
import frc.robot.commands.Drive.ArcadeDriveCommand;
import frc.robot.commands.Drive.CalibrateSpeedCommand;
import frc.robot.commands.Drive.SpeedCommand;
import frc.robot.commands.Intake.AmbientIntakePowerCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Serializer.SerializeCommand;
import frc.robot.commands.Shooter.PowerCommand;
import frc.robot.commands.Shooter.ShooterSpeedCommand;
import frc.robot.commands.Snoot.SnootCommand;
import frc.robot.commands.Teleop.ShootAllCommand;
import frc.robot.commands.Teleop.ShootCommand;
import frc.robot.commands.Teleop.SpinUpCommand;
import frc.robot.commands.Throat.ThroatAtSpeedCommand;
import frc.robot.commands.Throat.ThroatPowerCommand;
import frc.robot.commands.Turret.TurretMoveCommand;
import frc.robot.commands.Turret.TurretTrackingCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SnootSubsystem;
import frc.robot.subsystems.ThroatSubsystem;
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
  ThroatSubsystem m_throatSubsystem = new ThroatSubsystem();
  ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  SerializerSubsystem m_serializerSubsystem = new SerializerSubsystem();
  IndexerSubsystem m_indexerSubsystem = new IndexerSubsystem();
  IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
  SnootSubsystem m_snootSubsystem = new SnootSubsystem();

  Camera m_camera = new Camera();

  Joystick m_stick = new Joystick(0);
  Joystick m_climbStick = new Joystick(1);
  Joystick m_calibStick = new Joystick(2);

  // JoystickButton m_calibrateBtn = new JoystickButton(m_calibStick, 1);
  // JoystickButton m_trackBalls = new JoystickButton(m_stick, 2);
  // JoystickButton m_snoot = new JoystickButton(m_stick, 5);

  // JoystickButton m_shoot = new JoystickButton(m_stick, 5);
  // JoystickButton m_throat = new JoystickButton(m_stick, 6);

  JoystickButton m_spinUp = new JoystickButton(m_climbStick, 3);
  JoystickButton m_spinUpTrack = new JoystickButton(m_climbStick, 3);
  JoystickButton m_fire = new JoystickButton(m_climbStick, 1);

  JoystickButton m_intake = new JoystickButton(m_climbStick, 2);

  JoystickButton m_moveTurrentL = new JoystickButton(m_climbStick, 9);
  JoystickButton m_moveTurrentR = new JoystickButton(m_climbStick, 10);

  JoystickButton m_turretTrack = new JoystickButton(m_climbStick, 8);

  JoystickButton m_controlPanel = new JoystickButton(m_climbStick, 6);

  JoystickButton m_manualControlPanel = new JoystickButton(m_climbStick, 5);

  JoystickButton m_outtake = new JoystickButton(m_climbStick, 4);

  JoystickButton m_climb = new JoystickButton(m_climbStick, 7);

  JoystickButton m_unJumble = new JoystickButton(m_climbStick, 11);

  JoystickButton m_calibrateSpeed = new JoystickButton(m_calibStick, 1);

  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    m_camera.connect("10.21.2.111");

    configureButtonBindings();

    m_driveSubsystem.setDefaultCommand(new ArcadeDriveCommand(m_driveSubsystem, () -> m_stick.getX(),() ->  -m_stick.getY(), () -> m_stick.getThrottle()));
    m_serializerSubsystem.setDefaultCommand(new SerializeCommand(m_serializerSubsystem, 0.3, () -> m_throatSubsystem.GetTopBreak()));
    m_throatSubsystem.setDefaultCommand(new ThroatAtSpeedCommand(m_throatSubsystem, 0.4));
    // m_intakeSubsystem.setDefaultCommand(new AmbientIntakePowerCommand(m_intakeSubsystem, 0.25));
    m_chooser.setDefaultOption("Do Nothing", new Command(){
    
      @Override
      public Set<Subsystem> getRequirements() {
        // TODO Auto-generated method stub
        return null;
      }
    });
    
    Waypoint[] k_10ft = {
      new Waypoint(0, 0, Math.toRadians(90), 5),
      new Waypoint(0, 10, Math.toRadians(90))
    };

    m_chooser.setDefaultOption("Do Nothing", new Command(){
    
      @Override
      public Set<Subsystem> getRequirements() {
        // TODO Auto-generated method stub
        return null;
      }
    });
    m_chooser.addOption("Trench Run", new TrenchRun(m_driveSubsystem, m_intakeSubsystem, 
                                                    m_shooterSubsystem, m_turretSubsystem, m_throatSubsystem, m_indexerSubsystem, m_camera,
                                                    35000, () -> getPos().x, () -> getPos().y));
    m_chooser.addOption("10 ft", new CreatePathCommand(m_driveSubsystem, k_10ft, PathConfigs.fast));
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
    double shooterSpeed = 33000;//31000; //36000;
    // m_calibrateBtn.whileHeld(new SpeedCommand(m_driveSubsystem, 11.94));
    // m_trackBalls.toggleWhenPressed(new BallDriveCommand(m_driveSubsystem, m_camera, -0.25));
    // m_shoot.toggleWhenPressed(new ShootAllCommand(m_throatSubsystem, m_shooterSubsystem, m_serializerSubsystem, m_indexerSubsystem, m_intakeSubsystem, () -> getThrottle()));
    m_intake.whileHeld(new IntakeCommand(m_intakeSubsystem, 0.5));
    m_outtake.whileHeld(new IntakeCommand(m_intakeSubsystem, -0.5));
    m_spinUp.toggleWhenPressed(new SpinUpCommand(m_turretSubsystem, m_camera, m_shooterSubsystem, m_indexerSubsystem, shooterSpeed));
    // m_spinUpTrack.toggleWhenPressed(new TurretTrackingCommand(m_turretSubsystem, m_camera));
    m_fire.whileHeld(new ThroatPowerCommand(m_throatSubsystem, () -> m_shooterSubsystem.getSpeed(), () -> m_shooterSubsystem.getSetpoint() - 1500, 0.4));
    m_moveTurrentL.whileHeld(new TurretMoveCommand(m_turretSubsystem, 0.5));
    m_moveTurrentR.whileHeld(new TurretMoveCommand(m_turretSubsystem, -0.5));

    m_turretTrack.toggleWhenPressed(new TurretTrackingCommand(m_turretSubsystem, m_camera));

    m_climb.whileHeld(new MoveClimberCommand(m_climberSubsystem, () -> -m_climbStick.getY()));
    m_calibrateSpeed.whileHeld(new CalibrateSpeedCommand(m_driveSubsystem, 3000));
    
    // m_throat.toggleWhenPressed(new ParallelDeadlineGroup(new ThroatAtSpeedCommand(m_throatSubsystem, 0.75), new IntakeCommand(m_intakeSubsystem, 0.5)));
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

  public double getThrottle(){
    return (-m_climbStick.getThrottle() + 1)/2.0;
  }

  public double getTargetHeight(){
    return m_camera.createData().getTargetHeight();
  }
}
