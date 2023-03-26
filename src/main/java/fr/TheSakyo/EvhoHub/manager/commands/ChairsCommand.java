package fr.TheSakyo.EvhoHub.manager.commands;

import fr.TheSakyo.EvhoHub.HubMain;
import net.minecraft.ChatFormatting;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChairsCommand implements CommandExecutor {

	/* Récupère la class "Main" */
	private final HubMain main;
	public ChairsCommand(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */


	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		if(sender instanceof Player p) {

			if(args.length >= 2) {

				if(!p.hasPermission("evhohub.chairs.admin")) { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /chairs"); }

				else {

					if(args.length == 2) {

						Player target = Bukkit.getServer().getPlayer(args[1]);

						if(target != null) {

							if(target.hasPermission("evhohub.chairs")) {

								p.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise désactivé pour " + ChatFormatting.GOLD + target.getName());
								target.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise désactivé par " + ChatFormatting.YELLOW + p.getName());

								target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.chairs", false);

							} else {

								p.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise activé pour " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " !");
								target.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise activé par " + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " !");

								target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.chairs", true);
							}

							return true;

						} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

					} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /chairs" + ChatFormatting.GREEN + " <player>"); }
				}

			} else {

				if(p.hasPermission("evhohub.chairs")) {

					p.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez désactivé le Mode Chaise");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.chairs", false);

				} else {

					p.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez activé le Mode Chaise");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.chairs", true);
				}

				return true;
			}

		} else {

			if(args.length == 2) {

				Player target = Bukkit.getServer().getPlayer(args[1]);

				if(target != null) {

					if(target.hasPermission("evhohub.chairs")) {

						sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise désactivé pour " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " !");
						target.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise désactivé par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

						target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.chairs", false);

					} else {

						sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise activé pour " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " !");
						target.sendMessage(main.prefix + ChatFormatting.GREEN + "Mode Chaise activé par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

						target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.chairs", true);
					}

					return true;

				} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

			} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /chairs" + ChatFormatting.GREEN + " <player>"); }

		}

        return false;
    }
}
