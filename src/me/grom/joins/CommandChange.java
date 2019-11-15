package me.grom.joins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChange implements CommandExecutor{
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (!(sender instanceof Player)) {
            sender.sendMessage("This command can be run by a Player!");
            return true;
        }
        else{
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("change")) {
                if (args[0].equalsIgnoreCase("join")) {
                	p.sendMessage("��������� ���� ��������� ����� ���������� ��� ��������� ��� �����!");
                	p.sendMessage("��� ��� ��������� �������� �� �������� ������� ����, ����� ������ �������!");
                	JoinsMain.instance.isJoin = true;
                	return true;
                }
                else if (args[0].equalsIgnoreCase("leave")) {
                	p.sendMessage("��������� ���� ��������� ����� ���������� ��� ��������� ��� ������!");
                	p.sendMessage("��� ��� ��������� �������� �� �������� ������� ����, ����� ������ �������!");
                	JoinsMain.instance.isLeave = true;
                	return true;
                }
            }
        }
		return true;
	}

}
