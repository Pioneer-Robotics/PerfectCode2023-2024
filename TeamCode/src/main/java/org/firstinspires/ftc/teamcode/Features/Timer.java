package org.firstinspires.ftc.teamcode.Features;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class Timer extends AbstractHardwareComponent {
    static boolean firsttime = true;
    static ElapsedTime time = new ElapsedTime();

    public static void sleep(long seconds){
        try{
            Thread.sleep((Long) seconds * 1000);
        }
        catch (InterruptedException e){
            System.out.println("something failed");
        }
    }
    public static void sleepHalfSeconds(long seconds){
        try{
            Thread.sleep((Long) seconds * 100);
        }
        catch (InterruptedException e){
            System.out.println("something failed");
        }
    }

    public static boolean timer(double seconds){
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

    public static double getTime(){
        if(firsttime) {
            firsttime = false;
            time.reset();
        }
        return time.milliseconds();
    }
    public static void reset(){time.reset();}
}
