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
        Timer timer = this.plugin.getTimer();
        if(!timer.isPaused()){
            if(timer.getTime() >= this.plugin.getConfig().getInt("startCountdown")){
                timer.broadcastTime(this.plugin.getConfig().getString("broadcastMessage"), this.plugin.getConfig().getString("displayType"));
            }
        }
    }
}