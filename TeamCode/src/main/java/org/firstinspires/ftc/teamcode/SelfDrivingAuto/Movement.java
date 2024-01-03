package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * Abstract class which you should make multiple objects of. These objects are input inside of
 * AutoDrive. These objects are what make the robot move around the field
 */
public abstract class Movement extends AbstractHardwareComponent {
    private PIDCoefficients coefficients;//PIDcoef object set in constructor
    private PIDCoefficients pidXY, pidTheta;
    /**
     * Used in AutoDive, this method will be called every iteration in the loop.
     * Put actions in here to run even inside of while loop
     */
    public abstract void doWhileMoving();
    private double dX, dY, dTheta, speed;//desired values for AutoDrive

    /**
     * Constructor that will tell what AutoDrive to do.
     * ALL VALUES ARE ABSOLUTE. For example, going to 30,30,0 and then calling 30,30,0 again will keep you
     * in the same spot.
     * @param dX desired x absolute position
     * @param dY desired y absolute position
     * @param dTheta desired theta absolute position
     * @param pidXY used to input PIDF coefficients and do PID calculations
     * @param pidTheta used to input PIDF coefficents
     */
    public Movement(double dX, double dY, double dTheta, PIDCoefficients pidXY, PIDCoefficients pidTheta){
        this.dX = dX;
        this.dY = dY;
        this.dTheta = dTheta;
        speed = 0.5;
        this.pidXY = pidXY;
        this.pidTheta = pidTheta;
        if(bot.isRed()){
            this.dX *= -1;
        }
    }

    //setters
    public void setdX(double dX){this.dX = dX;}
    public void setdY(double dY){this.dY = dY;}
    public void setdTheta(double dTheta){this.dTheta = dTheta;}
    public void setPosition(double dX, double dY, double dTheta){
        setdX(dX);
        setdY(dY);
        setdTheta(dTheta);
    }
    //getters
    public PIDCoefficients getCoefficients() {return coefficients;}
    public PIDCoefficients getPidXY(){return pidXY;}
    public PIDCoefficients getPidTheta(){return pidTheta;}
    public double getdX(){return dX;}
    public double getdY(){return dY;}
    public double getdTheta(){return dTheta;}
    public double getSpeed(){return speed;}
}
