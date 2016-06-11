package restartme;

import cn.nukkit.plugin.PluginBase;
import restartme.command.RestartMeCommand;
import restartme.task.AutoBroadcastTask;
import restartme.task.CheckMemoryTask;
import restartme.task.RestartServerTask;
import restartme.utils.Timer;

public class RestartMe extends PluginBase{
    public static final int NORMAL = 0;
    public static final int OVERLOADED = 1;
    private Timer timer;
    @Override
    public void onEnable(){
        saveDefaultConfig();
        saveResource("values.txt");
        getServer().getCommandMap().register("restartme", new RestartMeCommand(this));
        getServer().getScheduler().scheduleRepeatingTask(new AutoBroadcastTask(this), (getConfig().getInt("broadcastInterval") * 20));
        if(getConfig().getBoolean("restartOnOverload")){
            getServer().getScheduler().scheduleRepeatingTask(new CheckMemoryTask(this), 6000);
            getServer().getLogger().notice("Memory overload restarts are enabled. If memory usage goes above "+getMemoryLimit()+", the server will restart.");
        }
        else{
            getServer().getLogger().notice("Memory overload restarts are disabled.");
        }
        getServer().getScheduler().scheduleRepeatingTask(new RestartServerTask(this), 20);
        this.timer = new Timer(this);
    }
    public Timer getTimer(){
        return timer;
    }
    public String getMemoryLimit(){
        return getConfig().getString("memoryLimit").toUpperCase();
    }
}