package impl;

import static org.quartz.TriggerBuilder.newTrigger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import quartz.FirstJob;

import static org.quartz.SimpleScheduleBuilder.*;

import schedule.SchedulerUtil;


public class ScheduleJob {

    public static void main(String[] args) {
    	JobDetail job = null;
    	Trigger trigger = null;

    	String desc = "Remind me to check mail after 10 seconds";
    	// More inputs:
    	// Remind me to call mom at 5:15 pm
    	// Notify me to send mail after 1 hour
    	// Remind me to check status after 10 minutes

    	desc = desc.toLowerCase();
    	try {
        	 Properties prop = new Properties();
             prop.put("org.quartz.scheduler.rmi.proxy", "true");
             prop.put("org.quartz.scheduler.rmi.registryHost", "localhost");
             prop.put("org.quartz.scheduler.rmi.registryPort", "1099");
             prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
             prop.put("org.quartz.threadPool.threadCount", "1");
        	 Scheduler scheduler = new StdSchedulerFactory(prop).getScheduler();
        	 job = JobBuilder.newJob(FirstJob.class).withIdentity("Job1", "group1").build();
        	if((desc.contains("remind") || desc.contains("notify")) && desc.contains("after") && (desc.contains("hr") || desc.contains("hour"))) {
        		int units = SchedulerUtil.afterSomeTimeUnits(desc);
        		if(units > 0) {
        			trigger = newTrigger().withIdentity("tr1", "group1").startAt(DateUtils.addHours(new Date(), units))
               			 .withSchedule(simpleSchedule()
                               .withIntervalInHours(units)
                               .withRepeatCount(0))
               			 .build();
        		} else {
        			System.out.println("Unable to parse your statement");
        		}
     		} else if((desc.contains("remind") || desc.contains("notify")) && desc.contains("after") && (desc.contains("sec") || desc.contains("second"))) {
        		int units = SchedulerUtil.afterSomeTimeUnits(desc);
        		if(units > 0) {
        			trigger = newTrigger().withIdentity("tr1", "group1").startAt(DateUtils.addSeconds(new Date(), units))
               			 .withSchedule(simpleSchedule()
                               .withIntervalInSeconds(units)
                               .withRepeatCount(0))
               			 .build();
        		} else {
        			System.out.println("Unable to parse your statement");
        		}
     		} else if((desc.contains("remind") || desc.contains("notify")) && desc.contains("after") && (desc.contains("mins") || desc.contains("minute"))) {
        		int units = SchedulerUtil.afterSomeTimeUnits(desc);
        		if(units > 0) {
        			trigger = newTrigger().withIdentity("tr1", "group1").startAt(DateUtils.addMinutes(new Date(), units))
               			 .withSchedule(simpleSchedule()
                               .withIntervalInMinutes(units)
                               .withRepeatCount(0))
               			 .build();
        		} else {
        			System.out.println("Unable to parse your statement");
        		}
     		} else if((desc.contains("remind") || desc.contains("notify")) && (desc.contains("at") || desc.contains("on"))){
     			 trigger = newTrigger().withIdentity("tr1", "group1").startAt(new Date())
            			 .withSchedule(CronScheduleBuilder
            					 .cronSchedule(SchedulerUtil.getCronFromDescription(desc)))
            			 .build();
     		}
        	 if(scheduler.checkExists(job.getKey())) {
        		 scheduler.deleteJob(job.getKey());
        	 }
        	 if(trigger != null) {
        		 scheduler.scheduleJob(job, trigger);
        	 }
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement st = null;
        StringBuilder query = new StringBuilder("UPDATE qrtz_job_details SET DESCRIPTION = '"+ desc +"' WHERE ");
        query.append("JOB_NAME = '").append(job.getKey().getName()).append("' AND ");
        query.append("JOB_GROUP = '").append(job.getKey().getGroup()).append("'");
        try {
				Class.forName("com.mysql.jdbc.Driver");
	 			connection = DriverManager.getConnection("jdbc:mysql://localhost/quartz", "root", "root");
	 			if(connection != null) {
	 				st = connection.prepareStatement(query.toString());
	 				st.executeUpdate();
	 			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}