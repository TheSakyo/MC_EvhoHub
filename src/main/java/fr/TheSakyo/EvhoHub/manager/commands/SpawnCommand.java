package fr.TheSakyo.EvhoHub.manager.commands;

import fr.TheSakyo.EvhoHub.HubMain;
import net.minecraft.ChatFormatting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

	/* Récupère la class "Main" */
	private final HubMain main;
	public SpawnCommand(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */


	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		World world = Bukkit.getServer().getWorld("hub");
		Location worldSpawnLocation = world.getSpawnLocation();
		Location spawnLocationWorld = new Location(world, worldSpawnLocation.getX(), worldSpawnLocation.getY(), worldSpawnLocation.getZ(), worldSpawnLocation.getYaw(), worldSpawnLocation.getPitch());

		if(sender instanceof Player p) {

			if(args.length >= 2) {

				if(args.length == 2) {

					Player target = Bukkit.getServer().getPlayer(args[1]);

					if(!p.hasPermission("evhohub.spawn.admin")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }
					else {

						if(target != null) {

							target.teleport(spawnLocationWorld.add(0.0, 1.0, 0.0));

							sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez téléporté " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " au spawn du hub/lobby !");
							target.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez été téléporté au spawn du hub/lobby par " + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " !");

							return true;

						} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }
					}

				} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /spawn" + ChatFormatting.GREEN + " <player>"); }

			} else {

				if(!p.hasPermission("evhohub.spawn")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }
				else {

					p.teleport(spawnLocationWorld.add(0.0, 1.0, 0.0));
					sender.sendMessage(main.prefix + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Vous avez été téléporté au spawn du hub/lobby !");

					return true;
				}
			}

		} else {

			if(args.length == 2) {

				Player target = Bukkit.getServer().getPlayer(args[1]);

				if(target != null) {

					target.teleport(spawnLocationWorld.add(0.0, 1.0, 0.0));

					sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez téléporté " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " au spawn du hub/lobby !");
					target.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez été téléporté au spawn du hub/lobby par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

					return true;

				} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

			} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /spawn" + ChatFormatting.GREEN + " <player>"); }

		}

        return false;
    }
}
