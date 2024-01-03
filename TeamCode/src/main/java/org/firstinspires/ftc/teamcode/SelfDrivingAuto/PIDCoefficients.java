package org.firstinspires.ftc.teamcode.SelfDrivingAuto;


/**
 * PID class that initializes PIDF coefficients and does PID calculations
 */
public class PIDCoefficients {
    /**
     * Doubles for calculation
     */
    private double P, I, D, F;//PIDF constants
    private double errorSum, dError, lastError, lastTime;//variables for I and D
    private double kP, kI, kD, kF;//each variable shows the values each one produces

    /**
     * Constructor to set PIDF coefficients. Implement inside Movement or getPID
     * @param P proportional constant
     * @param I integral constant
     * @param D derivate constant
     * @param F feed-forward constant
     */
    public PIDCoefficients(double P, double I, double D, double F){
        this.P = P;
        this.I = I;
        this.D = D;
        this.F = F;
    }

    /**
     * PID method that scales power based on distance from target
     * @param error distance from target
     * @param target target value
     * @param speed maximum speed value
     * @return proportional value based on error and target
     */
    public double PID(double error, double target, double speed){
        if(Math.abs(error) < AutoConfig.drivingThresholdCM) {return 0;}

        errorSum += error / target;
        dError = error - lastError;
        lastError = error / target; // Update previous error

        kP = P * (error / target);
        kI = I * errorSum;
        kD = D * dError;

        return (kP + kI + kD) * speed;
    }

    //same as above but can input your own PID coef
    public double PID(PIDCoefficients pidCoefficients, double error, double target, double speed){
        if(Math.abs(error) < AutoConfig.turningThresholdDEG) {return 0;}
        errorSum += error / target;
        dError = error - lastError;
        lastError = error / target; // Update previous error

        kP = pidCoefficients.getP() * (error/target);
        kI = pidCoefficients.getI() * errorSum;
        kD = pidCoefficients.getD() * dError;

        return (kP + kI + kD) * speed;
    }

    /**
     * Resets the PID variables for new PID calculations. If not sum gonna be crazy
     */
    public void resetForPID(){
        dError = 0d;
        errorSum = 0d;
    }

    //getters
    public double getP(){return P;}
    public double getI(){return I;}
    public double getD(){return D;}
    public double getF(){return F;}

    public double getkP(){return kP;}
    public double getkI(){return kI;}
    public double getkD(){return kD;}
    public double getkF(){return kF;}

    //setters
    public void setP(double P){this.P = P;}
    public void setI(double I){this.I = I;}
    public void setD(double D){this.D = D;}
    public void setF(double F){this.F = F;}

    public static class ConfigPIDCoefficients{}
}
