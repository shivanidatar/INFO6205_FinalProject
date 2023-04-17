package com.example.info6205_team02.Christofides;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// Benchmark class added to calculate the total  
public class Benchmark
{
    private LocalDateTime start;
    private LocalDateTime end;
    private long time = 0;
    private boolean startCheck = false;
    private boolean endCheck = false;

public Benchmark()
{
   
}
   
    void startMark()
    {
        startCheck = true;
        start = LocalDateTime.now();
    }
   
    void endMark()
    {
        endCheck = true;
        end = LocalDateTime.now();
    }
   
    long currentTime()
    {
    	return ChronoUnit.SECONDS.between(start, LocalDateTime.now());
    }
    
    long resultTime()
    {
        if(startCheck && endCheck) {
            return ChronoUnit.MILLIS.between(start, end);
        } else {
            System.out.print("BenchMark Failed. ");
        }
        return time;
    }
   
}