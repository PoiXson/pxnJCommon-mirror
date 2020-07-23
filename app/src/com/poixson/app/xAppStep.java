package com.poixson.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface xAppStep {


	public enum StepType {STARTUP, SHUTDOWN};

	StepType type();
	int      step()  default 100;
	String   title() default "";

	boolean  multi() default false;

	public enum PauseWhen {NONE, BEFORE, AFTER};
	PauseWhen pause() default PauseWhen.NONE;


}
