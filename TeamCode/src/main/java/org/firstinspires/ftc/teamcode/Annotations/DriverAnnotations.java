package org.firstinspires.ftc.teamcode.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DriverAnnotations {
    @Retention(RetentionPolicy.RUNTIME)
    @interface Driver1 {
        @interface Seth{}

        String name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Driver2{
        @interface Henry{}

        String name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Coach{
        @interface Zak{}

        @interface Spencer{}

        @interface Christian{}

        String name();
    }

}


