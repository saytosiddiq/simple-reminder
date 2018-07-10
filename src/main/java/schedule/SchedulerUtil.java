package schedule;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SchedulerUtil {
	private static Map<String, String> weekDays = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("sunday", "SUN");
		put("monday", "MON");
		put("tuesday", "TUE");
		put("wednesday", "WED");
		put("thursday", "THU");
		put("friday", "FRI");
		put("saturday", "SAT");
	}};
	
	private static String getCronExpression(ScheduleConfig conf) {	
		return String.format("%1$s %2$s %3$s %4$s %5$s %6$s %7$s", 
				conf.getSeconds(), conf.getMinutes(), 
				conf.getHours(), conf.getDayOfMonth(), 
				conf.getMonth(), conf.getDayOfWeek(), 
				conf.getYear());
	}

	public static String getCronFromDescription(String desc) {
		ScheduleConfig config = new ScheduleConfig();
		String[] params = desc.split(" ");
		for(int i=0; i < params.length; i++) {
			String curParam = params[i];
			if(curParam.matches(".*\\d+.*")) {
				if(curParam.contains(":")) {
					String[] timeParams = curParam.split(":");
					for(int j=0; j< timeParams.length; j++) {
						String s = timeParams[j].toLowerCase();
						if(s.contains("am")) {
							timeParams[j] = s.replace("am", "");
							if(timeParams[j-1] == "12") {
								timeParams[j-1] = "0";
							}
						} else if(s.contains("pm")) {
							timeParams[j] = s.replace("pm", "");
							int hrs = Integer.valueOf(timeParams[j-1]);
							if(hrs < 12) {
								hrs = hrs + 12;
							}
							timeParams[j-1] = String.valueOf(hrs);
						}
					}

					if(timeParams.length == 3) {
						config.setHours(timeParams[0]);
						config.setMinutes(timeParams[1]);
						config.setSeconds(timeParams[2]);
					} else if(timeParams.length == 2) {
						config.setHours(timeParams[0]);
						config.setMinutes(timeParams[1]);
					} else {
						System.out.println("Invalid time value");
					}
				} else if(curParam.contains("-")) {
					String[] dateParams = curParam.split("-");
					if(dateParams.length == 3) {
						config.setMonth(dateParams[0]);
						config.setDayOfMonth(dateParams[1]);
						config.setYear(dateParams[2]);
					} else {
						System.out.println("Invalid date value");
					}
				}
			} else if(!curParam.isEmpty() && weekDays.containsKey(curParam.toLowerCase())){
				config.setDayOfWeek(weekDays.get(curParam));
			} else if("am".equals(curParam.toLowerCase())){
				if(config.getHours().matches(".*\\d+.*")) {
					if(config.getHours() == "12") {
						config.setHours("0");
					}
				} else {
					config.setHours(String.valueOf(params[i-1]));
				}
			} else if("pm".equals(curParam.toLowerCase())){
				int hrs = 0;
				if(config.getHours().matches(".*\\d+.*")) {
					hrs = Integer.valueOf(config.getHours());
					if(hrs < 12) {
						hrs = hrs + 12;
						config.setHours(String.valueOf(hrs));
					}
				} else {
					hrs = Integer.valueOf(params[i-1]);
					if(hrs < 12) {
						hrs = hrs + 12;
						config.setHours(String.valueOf(hrs));
					}
				}
			}
		}
		return getCronExpression(config);
	}
	
	public static int afterSomeTimeUnits(String desc) {
		int units = -1;
		String[] params = desc.split(" ");
		for(int i=0; i < params.length; i++) {
			String curParam = params[i];
			if(curParam.matches(".*\\d+.*")) {
				if(StringUtils.isNumeric(curParam)) {
					units = Integer.valueOf(curParam);
					break;
				}
			}
		}
		return units;
	}
}
