# Joystick layout

<img src="Joystick.png" align="right">

## Joystick 0: stick

* __Button 1__: fire: _whileHeld_: ```new FireCommand(m_throatSubsystem, m_shooterSubsystem, m_intakeSubsystem)```

* __Button 2__:

  * spinUp: _toggleWhenPressed_: ```new SpinUpCommand(m_turretSubsystem, m_turretCamera, m_shooterSubsystem, m_indexerSubsystem, m_shooterSpeed)```

  * spinUpTrack: _toggleWhenPressed_: ```new TurretTrackingCommand(m_turretSubsystem, m_turretCamera)```

* __Button 3__: intake: _toggleWhenPressed_: ```new IntakeCommand(m_intakeSubsystem, 0.9)```

* __Button 4__: turretTrack: _toggleWhenPressed_: ```new TurretTrackingCommand(m_turretSubsystem, m_turretCamera)```

* __Button 5__: outtake: _toggleWhenPressed_: ```new IntakeCommand(m_intakeSubsystem, -0.75)```

* __Button 6__: feederIntake: _whileHeld_: ```new AmbientIntakePowerCommand(m_intakeSubsystem, -0.5)```

* __Button 7__: moveTurrentL: _whileHeld_: ```new TurretMoveCommand(m_turretSubsystem, -0.35)```

* __Button 8__: moveTurrentR: _whileHeld_: ```new TurretMoveCommand(m_turretSubsystem, 0.35)```

## Joystick 1: climbStick

* __Button 2__:

  * spinUpClimb: _toggleWhenPressed_: ```new SpinUpCommand(m_turretSubsystem, m_turretCamera, m_shooterSubsystem, m_indexerSubsystem, m_shooterSpeed)```

  * spinUpTrackClimb: _toggleWhenPressed_: ```new TurretTrackingCommand(m_turretSubsystem, m_turretCamera)```

* __Button 3__: outtakeClimb: _whileHeld_: ```new IntakeCommand(m_intakeSubsystem, -0.75)```

* __Button 4__: intakeClimb: _whileHeld_: ```new IntakeCommand(m_intakeSubsystem, 0.9)```

* __Button 5__: manualControlPanel: _whenPressed_: ```new FixedRotationCommand(m_snootSubsystem, 0.25, 3.2)```

* __Button 6__:

  * controlPanel

  * unJumble: _whileHeld_: ```new UnJumbleCommand(m_intakeSubsystem, m_throatSubsystem, m_serializerSubsystem)```

* __Button 7__: climb: _whileHeld_: ```new MoveClimberCommand(m_climberSubsystem, () -> -m_climbStick.getY())```

* __Button 8__: feederIntakeClimb: _whileHeld_: ```new AmbientIntakePowerCommand(m_intakeSubsystem, -0.5)```

## Joystick 2: calibStick

* __Button 1__: calibrateSpeed: _whileHeld_: ```new FireCommand(m_throatSubsystem, m_shooterSubsystem, m_intakeSubsystem)```

* __Button 2__: calibrateSpeedShooter:

  * _toggleWhenPressed_: ```new frc.robot.commands.Shooter.CalibrateSpeedCommand(m_shooterSubsystem, () -> getThrottleCalib())```

  * _toggleWhenPressed_: ```new IndexCommand(m_indexerSubsystem, 0.5)```

* __Button 3__: snootTesting: _whileHeld_: ```new SnootTesting(m_snootSubsystem, 0.25)```

* __Button 4__: turretTrackCalib: _toggleWhenPressed_: ```new TurretTrackingCommand(m_turretSubsystem, m_turretCamera)```

* __Button 5__: snootSetRotation: _whenPressed_: ```new FixedRotationCommand(m_snootSubsystem, 0.25, 3.2)```

* __Button 6__: trackBalls: _whileHeld_: ```new BallDriveCommand(m_driveSubsystem, m_backCamera, -0.25)```

