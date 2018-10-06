package com.chargnn.command;

import com.chargnn.api.UUIDFetcher;
import com.chargnn.service.BalanceService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class BalanceCommand implements CommandExecutor {

    private BalanceService service;

    public BalanceCommand(BalanceService service){
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 0 || strings.length > 3){
            sendUsage(commandSender);
            return true;
        }

        switch (strings[0].toLowerCase()){
            case "add":{
                UUID playerUUID = UUIDFetcher.getUUID(strings[1]);
                if(!service.hasBalance(playerUUID)){
                    noAccount(commandSender, strings[1]);
                    return true;
                }

                if(strings.length == 3){
                    double x;

                    try{
                        x = Double.parseDouble(strings[2]);
                    }catch(Exception e){
                        commandSender.sendMessage(ChatColor.RED + "This is not a valid ammount.");
                        return true;
                    }

                    service.add(playerUUID, x);
                } else {
                    sendUsage(commandSender);
                    return true;
                }

                break;
            }
            case "sub":{
                UUID playerUUID = UUIDFetcher.getUUID(strings[1]);
                if(!service.hasBalance(playerUUID)){
                    noAccount(commandSender, strings[1]);
                    return true;
                }

                if(strings.length == 3){
                    double x;

                    try{
                        x = Double.parseDouble(strings[2]);
                    }catch(Exception e){
                        commandSender.sendMessage(ChatColor.RED + "This is not a valid ammount.");
                        return true;
                    }

                    service.add(playerUUID, x*-1);
                } else {
                    sendUsage(commandSender);
                    return true;
                }

                break;
            }
            case "set":{
                UUID playerUUID = UUIDFetcher.getUUID(strings[1]);
                if(!service.hasBalance(playerUUID)){
                    noAccount(commandSender, strings[1]);
                    return true;
                }

                if(strings.length == 3){
                    double x;

                    try{
                        x = Double.parseDouble(strings[2]);
                    }catch(Exception e){
                        commandSender.sendMessage(ChatColor.RED + "This is not a valid ammount.");
                        return true;
                    }

                    service.set(playerUUID, x);
                } else {
                    sendUsage(commandSender);
                    return true;
                }

                break;
            }
            case "clear":{
                UUID playerUUID = UUIDFetcher.getUUID(strings[1]);
                if(!service.hasBalance(playerUUID)){
                    noAccount(commandSender, strings[1]);
                    return true;
                }

                if(strings.length == 2){
                   service.clear(playerUUID);
                } else {
                    sendUsage(commandSender);
                    return true;
                }

                break;
            }
            default:{
                sendUsage(commandSender);
                break;
            }
        }

        return true;
    }

    private void noAccount(CommandSender cs, String playerName){
        cs.sendMessage(ChatColor.RED + "Player " + playerName + " does not have an account.");
        BalanceService.balance.put(UUIDFetcher.getUUID(playerName), 200d);
        cs.sendMessage(ChatColor.GREEN + "Account was created for " + playerName + ".");
    }

    private void sendUsage(CommandSender cs){
        cs.sendMessage(ChatColor.RED + "Usage: /[set/add/clear] {player} (amount)");
    }
}