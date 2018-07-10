package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

public class FirstJob implements Job{
	 public void execute(JobExecutionContext context){
	        System.out.println("You said \"" +context.getJobDetail().getDescription() + "\"");
	        try {
				context.getScheduler().deleteJob(context.getJobDetail().getKey());
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
}
