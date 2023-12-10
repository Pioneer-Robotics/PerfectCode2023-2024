package org.firstinspires.ftc.teamcode.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface DriverAnnotations {
    @interface Driver1 {
        @interface Seth{}
    }
    @interface Driver2{
        @interface Henry{}
    }
    @interface Coach{
        @interface Zak{}
        @interface Spencer{}
        @interface Christian{}
    }

}


