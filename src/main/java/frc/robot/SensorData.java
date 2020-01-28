package frc	.robot;

public interface SensorData {
	double getLeftEncoderPos(); //must return in feet
	double getRightEncoderPos(); //must return in feet
	double getLeftEncoderVel(); //must return in feet
	double getRightEncoderVel(); //must return in feet
	double getAngle();
}
