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
        this.saveDefaultConfig();
        this.saveResource("values.txt");
        this.getServer().getCommandMap().register("restartme", new RestartMeCommand(this));
        this.getServer().getScheduler().scheduleRepeatingTask(new AutoBroadcastTask(this), (this.getConfig().getInt("broadcastInterval") * 20));
        if(this.getConfig().getBoolean("restartOnOverload")){
            this.getServer().getScheduler().scheduleRepeatingTask(new CheckMemoryTask(this), 6000);
            this.getServer().getLogger().notice("Memory overload restarts are enabled. If memory usage goes above "+this.getMemoryLimit()+", the server will restart.");
        }
        else{
            this.getServer().getLogger().notice("Memory overload restarts are disabled.");
        }
        this.getServer().getScheduler().scheduleRepeatingTask(new RestartServerTask(this), 20);
        this.timer = new Timer(this);
    }
    public Timer getTimer(){
        return this.timer;
    }
    public String getMemoryLimit(){
        return this.getConfig().getString("memoryLimit").toUpperCase();
    }
}