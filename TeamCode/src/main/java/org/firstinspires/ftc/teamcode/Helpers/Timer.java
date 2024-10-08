package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class Timer extends AbstractHardwareComponent {
    public boolean firsttime = true;
    public ElapsedTime time = new ElapsedTime();

    public void sleep(long seconds){
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.reset();
        while(elapsedTime.seconds() < seconds &&  (bot.opmode.opModeIsActive() && !bot.opmode.isStopRequested())){
            telemetry.addData("In sleep thread", elapsedTime.seconds());
            bot.update();
        }
    }

    public void sleepHalfSeconds(long seconds){
        try{
            Thread.sleep((Long) seconds * 100);
        }
        catch (InterruptedException e){
            System.out.println("something failed");
        }
    }

    public boolean timer(double seconds){
        if(firsttime) {
            firsttime = false;
            time.reset();
        }
        if(time.seconds() <= seconds){
            return false;
        }
        firsttime = true;
        return true;
    }

    public double getTime(){
        if(firsttime) {
            firsttime = false;
            time.reset();
        }
        return time.milliseconds();
    }
    public void reset(){time.reset();}
}
