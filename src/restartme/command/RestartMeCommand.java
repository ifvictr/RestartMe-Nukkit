package restartme.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import java.util.HashMap;
import restartme.utils.Timer;
import restartme.utils.Utils;
import restartme.RestartMe;

public class RestartMeCommand extends Command{
    private RestartMe plugin;
    public RestartMeCommand(RestartMe plugin){
        super("restartme", "Shows all RestartMe commands", null, new String[]{"rm"});
        this.setPermission("restartme.command.restartme");
        this.plugin = plugin;
    }
    private void sendCommandHelp(CommandSender sender){
        HashMap<String, String> commands = new HashMap<String, String>(){{
            put("add", "Adds n seconds to the timer");
            put("help", "Shows all RestartMe commands");
            put("memory", "Shows memory usage information");
            put("set", "Sets the timer to n seconds");
            put("start", "Starts the timer");
            put("stop", "Stops the timer");
            put("subtract", "Subtracts n seconds from the timer");
            put("time", "Gets the remaining time until the server restarts");
        }};
        sender.sendMessage("RestartMe commands:");
        for(String name : commands.keySet()){
            sender.sendMessage("/restartme "+name+": "+commands.get(name));
        }
    }
    @Override
    public boolean execute(CommandSender sender, String label, String[] args){
        if(!this.testPermission(sender)){
            return false;
        }
        if(args.length > 0){
            Timer timer = this.plugin.getTimer();
            switch(args[0].toLowerCase()){
                case "a":
                case "add":
                    if(args.length > 1){
                        timer.addTime(Integer.parseInt(args[1]));
                        sender.sendMessage(TextFormat.GREEN+"Added "+args[1]+" to restart timer.");
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Please specify a time value.");
                    }
                    break;
                case "help":
                    this.sendCommandHelp(sender);
                    break;
                case "m":
                case "memory":
                    String memLimit = this.plugin.getMemoryLimit();
                    sender.sendMessage("Bytes: "+Runtime.getRuntime().totalMemory()+"/"+Utils.calculateBytes(memLimit));
                    sender.sendMessage("Memory-limit: "+memLimit);
                    sender.sendMessage("Overloaded: "+(Utils.isOverloaded(memLimit) ? TextFormat.GREEN+"yes" : TextFormat.RED+"no"));
                    break;
                case "set":
                    if(args.length > 1){
                        timer.setTime(Integer.parseInt(args[1]));
                        sender.sendMessage(TextFormat.GREEN+"Set restart timer to "+args[1]+".");
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Please specify a time value.");
                    }
                    break;
                case "start":
                    if(timer.isPaused()){
                        timer.setPaused(false);
                        sender.sendMessage(TextFormat.YELLOW+"Timer is no longer paused.");
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Timer is not paused.");
                    }
                    break;
                case "stop":
                    if(timer.isPaused()){
                        sender.sendMessage(TextFormat.RED+"Timer is already paused.");
                    }
                    else{
                        timer.setPaused(true);
                        sender.sendMessage(TextFormat.YELLOW+"Timer has been paused.");
                    }
                    break;
                case "s":
                case "subtract":
                    if(args.length > 1){
                        timer.addTime(Integer.parseInt(args[1]));
                        sender.sendMessage(TextFormat.GREEN+"Subtracted "+args[1]+" from restart timer.");
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Please specify a time value.");
                    }
                    break;
                case "t":
                case "time":
                    sender.sendMessage(TextFormat.YELLOW+"Time remaining: "+timer.getFormattedTime());
                    break;
                default:
                    sender.sendMessage("Usage: /restartme <sub-command> [parameters]");
                    break;
            }
        }
        else{
            this.sendCommandHelp(sender);
            return false;
        }
        return true;
    }
}
