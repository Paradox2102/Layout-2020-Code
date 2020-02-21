/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.CSVWriter;
import frc.lib.PIDController;
import frc.lib.CSVWriter.Field;
import frc.pathfinder.Pathfinder.Path;
import frc.robot.Constants;
import frc.robot.PositionTracker;
import frc.robot.PurePursuit;
import frc.robot.Sensor;
import frc.robot.SparkMaxWrapperVel;
import frc.robot.PositionTracker.PositionContainer;
import frc.robot.PurePursuit.PathConfig;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.PurePursuit.VelocityContainer;

public class DriveSubsystem extends SubsystemBase {
  // creating drive controllers
  CANSparkMax m_leftDrive = new CANSparkMax(Constants.k_leftDrive, MotorType.kBrushless);
  CANSparkMax m_leftFollower = new CANSparkMax(Constants.k_leftFollower, MotorType.kBrushless);
  CANSparkMax m_rightDrive = new CANSparkMax(Constants.k_rightDrive, MotorType.kBrushless);
  CANSparkMax m_rightFollower = new CANSparkMax(Constants.k_rightFollower, MotorType.kBrushless);

  // creating drive PID controllers
  PIDController m_leftPIDController;
  PIDController m_rightPIDController;

  // creating wrappers allowing use of PIDController with Sparks
  SparkMaxWrapperVel m_leftWrapper;
  SparkMaxWrapperVel m_rightWrapper;

  // Creating encoders
  CANEncoder m_leftSparkEncoder;
  CANEncoder m_rightSparkEncoder;

  // Creating gyro
  PigeonIMU m_gyro = new PigeonIMU(0);

  // setting PID terms for 4500
  double k_fLeft = 0.000195;
  double k_pLeft = 0.000025;
  double k_iLeft = 0.000004;
  double k_dLeft = 0.0;
  double k_fRight = 0.000189;
  double k_pRight = 0.000025;
  double k_iRight = 0.000004;
  double k_dRight = 0.0;

  double k_iRange = 150;
  double k_minOutput = -1;
  double k_maxOutput = 1;

  // Creating tracking and pure pursuit objects
  private final PositionTracker m_posTracker;

  public final PurePursuit m_pursuitFollower;

  private final Sensor m_sensors;

  private final FollowThread followThread = new FollowThread();

  private boolean m_runFollowThread;

  public class FollowThread extends Thread {
    public void run() {
      final long step = k_mpPeriod;
      for (long nextRun = System.currentTimeMillis();; nextRun += step) {
        if (m_runFollowThread) {
          m_velContainer = m_pursuitFollower.followPath();
          SmartDashboard.putNumber("Ideal Left Vel", m_velContainer.leftVel);
          SmartDashboard.putNumber("Ideal Right Vel", m_velContainer.rightVel);

          setSpeedFeet(m_velContainer.leftVel, m_velContainer.rightVel);
        }
        try {
          final long sleepTime = nextRun - System.currentTimeMillis();
          if (sleepTime > 0) {
            sleep(sleepTime);
          }
        } catch (final InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }

  private VelocityContainer m_velContainer;

  private final long k_mpPeriod = 20; // Milliseconds

  private final double k_ticksFootSpark = 6.28;

  public DriveSubsystem() {
    // setting followers and idle modes
    m_rightFollower.follow(m_rightDrive);
    m_leftFollower.follow(m_leftDrive);

    m_leftDrive.setIdleMode(IdleMode.kBrake);
    m_leftFollower.setIdleMode(IdleMode.kBrake);
    m_rightDrive.setIdleMode(IdleMode.kBrake);
    m_rightFollower.setIdleMode(IdleMode.kBrake);

    m_rightDrive.setInverted(false);
    m_leftDrive.setInverted(true);

    // optional PID SmartDashboard Control
    SmartDashboard.setDefaultNumber("leftP", k_pLeft);
    SmartDashboard.setDefaultNumber("rightP", k_pRight);
    SmartDashboard.setDefaultNumber("leftI", k_iLeft);
    SmartDashboard.setDefaultNumber("rightI", k_iRight);
    SmartDashboard.setDefaultNumber("leftF", k_fLeft);
    SmartDashboard.setDefaultNumber("rightF", k_fRight);
    SmartDashboard.setDefaultNumber("leftD", k_dLeft);
    SmartDashboard.setDefaultNumber("rightD", k_dRight);
    SmartDashboard.setDefaultNumber("Izone", k_iRange);

    // PID controller setup
    m_leftSparkEncoder = m_leftDrive.getEncoder();
    m_rightSparkEncoder = m_rightDrive.getEncoder();

    m_leftWrapper = new SparkMaxWrapperVel(m_leftDrive, m_leftSparkEncoder, false);
    m_rightWrapper = new SparkMaxWrapperVel(m_rightDrive, m_rightSparkEncoder, false);

    m_leftPIDController = new PIDController(k_iLeft, 0, k_pLeft, k_fLeft, m_leftWrapper, m_leftWrapper, 0.02);
    m_leftPIDController.setIRange(k_iRange);
    m_rightPIDController = new PIDController(k_iRight, 0, k_pRight, k_fRight, m_rightWrapper, m_rightWrapper, 0.02);
    m_rightPIDController.setIRange(k_iRange);

    m_leftDrive.setOpenLoopRampRate(0.3);
    m_rightDrive.setOpenLoopRampRate(0.3);
    m_leftFollower.setOpenLoopRampRate(0.3);
    m_rightFollower.setOpenLoopRampRate(0.3);
    
    //Tracking and Pure Pursuit Setup
    m_sensors = new Sensor(m_leftSparkEncoder, m_rightSparkEncoder, m_gyro, k_ticksFootSpark);

    m_posTracker = new PositionTracker(0, 0, false, m_sensors);

    m_pursuitFollower = new PurePursuit(m_sensors, m_posTracker);

    followThread.start();
  }

  @Override
  public void periodic() {
  }

  // used for tuning PID on Smartdashboard
  public void configPID() {
    disablePID();
    k_pLeft = SmartDashboard.getNumber("leftP", k_pLeft);
    k_pRight = SmartDashboard.getNumber("rightP", k_pRight);
    k_iLeft = SmartDashboard.getNumber("leftI", k_iLeft);
    k_iRight = SmartDashboard.getNumber("rightI", k_iRight);
    k_fLeft = SmartDashboard.getNumber("leftF", k_fLeft);
    k_fRight = SmartDashboard.getNumber("rightF", k_fRight);
    k_iRange = (int) (SmartDashboard.getNumber("Izone", k_iRange));

    m_leftPIDController.setF(k_fLeft);
    m_leftPIDController.setP(k_pLeft);
    m_leftPIDController.setI(k_iLeft);
    m_leftPIDController.setIRange(k_iRange);

    m_rightPIDController.setF(k_fRight);
    m_rightPIDController.setP(k_pRight);
    m_rightPIDController.setI(k_iRight);
    m_rightPIDController.setIRange(k_iRange);
    enablePID();
  }

  // setting basic power(checks for disabling PID controller)
  public void setPower(double leftPower, double rightPower) {
    if (m_leftPIDController.isEnabled()) {
      disablePID();
    }

    m_leftDrive.set(leftPower);
    m_rightDrive.set(rightPower);
  }

  // set speed using PID controllers
  public void setSpeed(double leftSpeed, double rightSpeed) {
    if (!m_leftPIDController.isEnabled() || !m_rightPIDController.isEnabled()) {
      enablePID();
    }

    m_leftPIDController.setSetpoint(leftSpeed);
    m_rightPIDController.setSetpoint(rightSpeed);
  }

  // set speed in feet using PID controllers
  public void setSpeedFeet(double leftSpeed, double rightSpeed) {
    if (!m_leftPIDController.isEnabled() || !m_rightPIDController.isEnabled()) {
      enablePID();
    }

    m_leftPIDController.setSetpoint(feetToTicks(leftSpeed * 60.0));
    m_rightPIDController.setSetpoint(feetToTicks(rightSpeed * 60.0));
  }

  /// stop power output and speed control
  public void stop() {
    m_leftDrive.set(0);
    m_rightDrive.set(0);

    if (m_leftPIDController.isEnabled()) {
      disablePID();
    }
  }

  public void disablePID() {
    m_leftPIDController.disable();
    m_rightPIDController.disable();
  }

  public void enablePID() {
    m_leftPIDController.enable();
    m_rightPIDController.enable();
  }

  public void startFollow() {
    m_runFollowThread = true;
    m_pursuitFollower.startPath();
  }

  public void stopFollow() {
    m_runFollowThread = false;
    m_pursuitFollower.stopFollow();
  }

  public void loadFollowPath(final Path path, final boolean isReversed, final boolean isExtended) {
    m_pursuitFollower.loadPath(path, isReversed, isExtended);
  }

  public boolean isFollowFinished() {
    return m_pursuitFollower.isFinished();
  }

  public void startPosUpdate() {
    m_posTracker.startPosUpdate();
  }

  public void stopPosUpdate() {
    m_posTracker.stopPosUpdate();
  }

  public PositionContainer getPos() {
    return m_posTracker.getPos();
  }

  public void setPos(double x, double y) {
    m_posTracker.setXY(x, y);
  }

  public double getLeftVel() {
    return m_leftSparkEncoder.getVelocity();
  }

  public double getRightVel() {
    return m_rightSparkEncoder.getVelocity();
  }

  public double getLeftPos() {
    return m_leftSparkEncoder.getPosition();
  }

  public double getRightPos() {
    return m_rightSparkEncoder.getPosition();
  }

  private double feetToTicks(double feet) {
    return feet * k_ticksFootSpark;
  }

  public void resetAngle(double angle) {
    m_gyro.setYaw(angle);
    m_posTracker.setAngle(angle);
  }

  public double getAngle() {
    double[] ypr = new double[3];
    m_gyro.getYawPitchRoll(ypr);
    return ypr[0];
  }

  public PathConfig getPathConfig(PathConfigs config) {
    return m_pursuitFollower.getConfig(config);
  }

  public boolean printPath(Path path) {
    final Field[] k_fields = { new Field("x", 'f'), new Field("Y", 'f'), new Field("Left Velocity", 'f'),
        new Field("Right Velocity", 'f'),

    };
    final CSVWriter writer = new CSVWriter("Path", k_fields);
    writer.start();
    System.out.println(String.format("printPath: length = %d", path.m_centerPath.length));
    for (int i = 0; i < path.m_centerPath.length; i++) {
      writer.write(path.m_centerPath[i].x, path.m_centerPath[i].y, path.m_leftPath[i].velocity,
          path.m_rightPath[i].velocity);
    }
    writer.finish();
    return true;
  }

  public void setCoastMode() {
    m_leftDrive.setIdleMode(IdleMode.kCoast);
    m_leftFollower.setIdleMode(IdleMode.kCoast);
    m_rightDrive.setIdleMode(IdleMode.kCoast);
    m_rightFollower.setIdleMode(IdleMode.kCoast);
  }

  public void setBrakeMode() {
    m_leftDrive.setIdleMode(IdleMode.kBrake);
    m_leftFollower.setIdleMode(IdleMode.kBrake);
    m_rightDrive.setIdleMode(IdleMode.kBrake);
    m_rightFollower.setIdleMode(IdleMode.kBrake);
  }
}
