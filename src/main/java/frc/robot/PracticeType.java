package frc.robot;

public class PracticeType {
    public final double k_driveLeftF = 0.000195;
    public final double k_driveLeftP = 0.000025;
    public final double k_driveLeftI = 0.000004;
    public final double k_driveLeftD = 0;
    
    public final double k_driveRightF = 0.000189;
    public final double k_driveRightP = 0.000025;
    public final double k_driveRightI = 0.000004;
    public final double k_driveRightD = 0;
    
    public final double k_driveIRange = 150;
    
    public final double k_shooterF = 0.025500;
    public final double k_shooterP = 0.4;
    public final double k_shooterI = 0.032;
    public final double k_shooterD = 0;

    public final int k_shooterIRange = 100;

    public final String k_ipAddress = "10.21.2.111";

    //Shooter Equation Coefficients
    public final double k_squareConst = 3.433;
    public final double k_linearConst = 487.948;
    public final double k_const = 47550.336;
}