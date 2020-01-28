package frc.robot.commands.Auto;

import frc.robot.PurePursuit.PathConfig;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.pathfinder.Pathfinder;
import frc.pathfinder.Pathfinder.Path;
import frc.pathfinder.Pathfinder.Waypoint;

public class CreatePathCommand extends CommandBase {

	Path m_path;
	boolean m_isReversed;
	boolean m_setXY = true;
    boolean m_isExtended;
    boolean m_cameraCorrection = false;
    boolean m_stop = true;

    DriveSubsystem m_subsystem;
    
    // private Field[] fields = {
    //     new Field("x", 'f'),
    //     new Field("y", 'f'),
    //     new Field("Target Apart", 'f')
    // };

    // CSVWriter writer = new CSVWriter("Target Calibration", fields);

    public CreatePathCommand(DriveSubsystem subsystem, Waypoint[] points, PathConfigs config) {
        m_subsystem = subsystem;
        addRequirements(m_subsystem);

        m_isReversed = false;
        m_setXY = true;
        m_isExtended = true;
        m_stop = true;

        PathConfig pathConfig = m_subsystem.getPathConfig(config);

        m_path = Pathfinder.computePath(points, pathConfig.m_points, pathConfig.m_dt, pathConfig.m_vel, pathConfig.m_accel, pathConfig.m_jerk, pathConfig.m_wheelBase);
    }
	
    public CreatePathCommand(DriveSubsystem subsystem, Waypoint[] points, PathConfigs config, boolean isReversed, boolean setXY, boolean stop) {
        m_subsystem = subsystem;
        addRequirements(m_subsystem);
        
        m_isReversed = isReversed;
        m_setXY = setXY;
        m_isExtended = true;
        m_stop = stop;
        
        PathConfig pathConfig = m_subsystem.getPathConfig(config);

        m_path = Pathfinder.computePath(points, pathConfig.m_points, pathConfig.m_dt, pathConfig.m_vel, pathConfig.m_accel, pathConfig.m_jerk, pathConfig.m_wheelBase);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	if(m_setXY) {
            m_subsystem.setPos(m_path.m_centerPath[0].x, m_path.m_centerPath[0].y);
    	}
    	m_subsystem.loadFollowPath(m_path, m_isReversed, m_isExtended);
        m_subsystem.startFollow();
        
        // writer.start();
        Logger.Log("CreatePathCommand", 3, "Initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        // PositionContainer pos = m_subsystem.getPos();
        
        // writer.write(pos.x, pos.y, Robot.navigator.getTargetPixelsApart());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return m_subsystem.isFollowFinished();
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
        m_subsystem.stopFollow();
        if(m_stop){
            m_subsystem.stop();
        }
        // writer.finish();
        // System.out.println("End Create Path");
        Logger.Log("CreatePathCommand", 3, "End");
    }
}