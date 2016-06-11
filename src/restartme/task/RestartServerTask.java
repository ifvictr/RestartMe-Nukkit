package restartme.task;

import cn.nukkit.scheduler.PluginTask;
import restartme.utils.Timer;
import restartme.RestartMe;

public class RestartServerTask extends PluginTask<RestartMe>{
    private RestartMe plugin;
    public RestartServerTask(RestartMe plugin){
        super(plugin);
        this.plugin = plugin;
    }
    @Override
    public void onRun(int currentTick){
        Timer timer = plugin.getTimer();
        if(!timer.isPaused()){
            timer.subtractTime(1);
            if(timer.getTime() <= plugin.getConfig().getInt("startCountdown")){
                plugin.getTimer().broadcastTime(plugin.getConfig().getString("countdownMessage"), plugin.getConfig().getString("displayType"));
            }
            if(timer.getTime() < 1){
                timer.initiateRestart(RestartMe.NORMAL);
            }
        }
    }
}