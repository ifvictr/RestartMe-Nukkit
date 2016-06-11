package restartme.task;

import cn.nukkit.scheduler.PluginTask;
import restartme.utils.Timer;
import restartme.RestartMe;

public class AutoBroadcastTask extends PluginTask<RestartMe>{
    private RestartMe plugin;
    public AutoBroadcastTask(RestartMe plugin){
        super(plugin);
        this.plugin = plugin;
    }
    @Override
    public void onRun(int currentTick){
        Timer timer = plugin.getTimer();
        if(!timer.isPaused()){
            if(timer.getTime() >= plugin.getConfig().getInt("startCountdown")){
                timer.broadcastTime(plugin.getConfig().getString("broadcastMessage"), plugin.getConfig().getString("displayType"));
            }
        }
    }
}