package fr.TheSakyo.EvhoHub.manager.commands;

import fr.TheSakyo.EvhoHub.HubMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DamageCommand implements CommandExecutor {

	/* Récupère la class "Main" */
	private HubMain main;
	public DamageCommand(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */


	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		if(sender instanceof Player p) {

			if(!p.hasPermission("evhohub.damage.admin")) { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Vous n'avez pas les permissions requise !"); }

			if(args.length >= 2) {

				if(args.length == 2) {

					Player target = Bukkit.getServer().getPlayer(args[1]);

					if(target != null) {

						if(target.hasPermission("evhohub.damage")) {

							p.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts désactivé pour " + ChatColor.GOLD + target.getName());
							target.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts désactivé par " + ChatColor.YELLOW + p.getName());

							target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", false);

						} else {

							p.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts activé pour " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " !");
							target.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts activé par " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " !");

							target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", true);
						}

						return true;

					} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

				} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /damage" + ChatColor.GREEN + " <player>"); }

			} else {

				if(p.hasPermission("evhohub.damage")) {

					p.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez désactivé vos dégats");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", false);

				} else {

					p.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez activé vos dégats");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", true);
				}

				return true;
			}

		} else {

			if(args.length == 2) {

				Player target = Bukkit.getServer().getPlayer(args[1]);

				if(target != null) {

					if(target.hasPermission("evhohub.damage")) {

						sender.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts désactivé pour " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " !");
						target.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts désactivé par " + ChatColor.YELLOW + "La Console" + ChatColor.GREEN + " !");

						target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", false);

					} else {

						sender.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts activé pour " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " !");
						target.sendMessage(main.prefix + ChatColor.GREEN + "Dégâts activé par " + ChatColor.YELLOW + "La Console" + ChatColor.GREEN + " !");

						target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", true);
					}

					return true;

				} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

			} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /damage" + ChatColor.GREEN + " <player>"); }

		}

        return false;
    }
}
