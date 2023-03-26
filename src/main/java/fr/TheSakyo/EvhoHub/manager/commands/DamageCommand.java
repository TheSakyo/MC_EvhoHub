package fr.TheSakyo.EvhoHub.manager.commands;

import fr.TheSakyo.EvhoHub.HubMain;
import net.minecraft.ChatFormatting;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DamageCommand implements CommandExecutor {

	/* Récupère la class "Main" */
	private final HubMain main;
	public DamageCommand(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */


	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		if(sender instanceof Player p) {

			if(!p.hasPermission("evhohub.damage.admin")) { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Vous n'avez pas les permissions requise !"); }

			if(args.length >= 2) {

				if(args.length == 2) {

					Player target = Bukkit.getServer().getPlayer(args[1]);

					if(target != null) {

						if(target.hasPermission("evhohub.damage")) {

							p.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts désactivé pour " + ChatFormatting.GOLD + target.getName());
							target.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts désactivé par " + ChatFormatting.YELLOW + p.getName());

							target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", false);

						} else {

							p.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts activé pour " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " !");
							target.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts activé par " + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " !");

							target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", true);
						}

						return true;

					} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

				} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /damage" + ChatFormatting.GREEN + " <player>"); }

			} else {

				if(p.hasPermission("evhohub.damage")) {

					p.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez désactivé vos dégâts");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", false);

				} else {

					p.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez activé vos dégâts");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", true);
				}

				return true;
			}

		} else {

			if(args.length == 2) {

				Player target = Bukkit.getServer().getPlayer(args[1]);

				if(target != null) {

					if(target.hasPermission("evhohub.damage")) {

						sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts désactivé pour " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " !");
						target.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts désactivé par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

						target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", false);

					} else {

						sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts activé pour " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " !");
						target.sendMessage(main.prefix + ChatFormatting.GREEN + "Dégâts activé par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

						target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.damage", true);
					}

					return true;

				} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

			} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /damage" + ChatFormatting.GREEN + " <player>"); }

		}

        return false;
    }
}
