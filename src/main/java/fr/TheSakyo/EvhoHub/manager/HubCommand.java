/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

package fr.TheSakyo.EvhoHub.manager;

import fr.TheSakyo.EvhoHub.HubMain;
import fr.TheSakyo.EvhoHub.utils.methods.CustomMethod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

public class HubCommand implements CommandExecutor {

	/* Récupère la class "Main" */
	private HubMain main;
	public HubCommand(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */


	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
				
			if(args.length == 0) { Bukkit.dispatchCommand(sender, "evhohub help"); }

			else if(args.length != 0) {

				if(args[0].equalsIgnoreCase("help")) {

					sender.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "----" + " " + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "EvhoHub" + " " + ChatColor.WHITE.toString() + ChatColor.BOLD.toString() +  "----");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + "Alias :" + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " /eh");
					sender.sendMessage(" ");
					sender.sendMessage(ChatColor.GREEN + "/evhohub help" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Affiche toutes les commandes du plugin");
					sender.sendMessage(" ");
					sender.sendMessage(ChatColor.GREEN + "/evhohub resetmenu" + ChatColor.RED + " [<player>]" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Réinitialise le menu dans l'inventaire");
					sender.sendMessage(" ");
					sender.sendMessage(ChatColor.GREEN + "/chairs" + ChatColor.RED + " [<player>]" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Active/Désactive le fait de s'asseoir sur des escaliers");
					sender.sendMessage(" ");
					sender.sendMessage(ChatColor.GREEN + "/spawn" + ChatColor.RED + " [<player>]" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Téléportation au point de spawn du hub/lobby");

					String toggleDamageInfoCommand = ChatColor.GREEN + "/damage" + ChatColor.RED + " [<player>]" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Active ou non les dégats d'un joueur";

					String resetJoueurProInfoCommand = ChatColor.GREEN + "/evhohub resetJoueurPro" + ChatColor.RED + " <player>" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Remet à zéro les permissions du grade JoueurPro au(x) joueur(s) précisé(s)";
					String ActiveJoueurProInfoCommand = ChatColor.GREEN + "/evhohub ActiveJoueurPro" + ChatColor.RED + " <player>" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Active les permissions du grade JoueurPro au(x) joueur(s) précisé(s)";

					String reloadInfoCommand = ChatColor.GREEN + "/evhohub reload" + ChatColor.WHITE + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Recharge le plugin";

					if(sender instanceof Player p) {

						if(p.hasPermission("evhohub.admin")) {

							p.sendMessage(" ");
							p.sendMessage(toggleDamageInfoCommand);
							p.sendMessage(" ");
							p.sendMessage(resetJoueurProInfoCommand);
							p.sendMessage(" ");
							p.sendMessage(ActiveJoueurProInfoCommand);
							p.sendMessage(" ");
							p.sendMessage(reloadInfoCommand);
						}

					} else {

						sender.sendMessage(" ");
						sender.sendMessage(toggleDamageInfoCommand);
						sender.sendMessage(" ");
						sender.sendMessage(resetJoueurProInfoCommand);
						sender.sendMessage(" ");
						sender.sendMessage(ActiveJoueurProInfoCommand);
						sender.sendMessage(" ");
						sender.sendMessage(reloadInfoCommand);
					}

					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "------------------");


				} else if(args[0].equalsIgnoreCase("resetmenu")) {

					if(sender instanceof Player p) {

						if(args.length >= 2) {

							if(!p.hasPermission("evhohub.admin")) { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub reset"); }

							else {

								if(args.length == 2) {

									Player target = Bukkit.getServer().getPlayer(args[1]);

									if(target != null) {

										CustomMethod.ResetMenu(target); //Réinitialise le Menu
										p.sendMessage(main.prefix + ChatColor.GREEN + "L'inventaire de " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " a bien été réinitialiser !");
										target.sendMessage(main.prefix + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " a réinitialiser votre inventaire !");

									} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

								} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub reset" + ChatColor.GREEN + " <player>"); }
							}

						} else {

							CustomMethod.ResetMenu(p); //Réinitialise le Menu
							p.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez réinitialiser votre inventaire !");
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

								CustomMethod.ResetMenu(target); //Réinitialise le Menu
								sender.sendMessage(main.prefix + ChatColor.GREEN + "L'inventaire de " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " a bien été remis à zéro !");
								target.sendMessage(main.prefix + ChatColor.YELLOW + "La Console" + ChatColor.GREEN + " a remis à zéro votre inventaire !");

							} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub reset" + ChatColor.GREEN + " <player>"); }

					} 

				} else if(args[0].equalsIgnoreCase("resetJoueurPro")) {

					if(sender instanceof Player p) {

						if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise !"); }

						else {

							if(args.length == 2) {

								Player target = Bukkit.getServer().getPlayer(args[1]);

								if(target != null) {

									if(target.hasPermission("evhohub.JoueurPro")) {

										p.sendMessage(main.prefix + ChatColor.GREEN + "Permission du joueur remis à zéro avec succés !");
										target.sendMessage(main.prefix + ChatColor.GREEN + "Permission remis à zéro par " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " !");

										target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

									} else { p.sendMessage(main.prefix + ChatColor.RED + "Permission du joueur déja remis à zéro !"); }

								} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

							} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub resetJoueurPro" + ChatColor.GREEN + " <player>"); }
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

								if(target.hasPermission("evhohub.JoueurPro")) {

									sender.sendMessage(main.prefix + ChatColor.GREEN + "Permission du joueur remis à zéro avec succés !");
									target.sendMessage(main.prefix + ChatColor.GREEN + "Permission remis à zéro par " + ChatColor.YELLOW + "La Console" + ChatColor.GREEN + " !");

									target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

								} else { sender.sendMessage(main.prefix + ChatColor.RED + "Permission du joueur déja remis à zéro !"); }

							} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub resetJoueurPro" + ChatColor.GREEN + " <player>"); }

					} 

				} else if(args[0].equalsIgnoreCase("activeJoueurPro")) {

					if(sender instanceof Player p) {

						if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise !"); }

						else {

							if(args.length == 2) {

								Player target = Bukkit.getServer().getPlayer(args[1]);

								if(target != null) {

									if(!target.hasPermission("evhohub.JoueurPro")) {

										p.sendMessage(main.prefix + ChatColor.GREEN + "Permission 'JoueurPro' du joueur activé avec succés !");
										target.sendMessage(main.prefix + ChatColor.GREEN + "Permission 'JoueurPro' activé par " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " !");

										target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

									} else { p.sendMessage(main.prefix + ChatColor.RED + "Permission 'JoueurPro' du joueur déja activé !"); }

								} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

							} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub activeJoueurPro" + ChatColor.GREEN + " <player>"); }
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

								if(!target.hasPermission("evhohub.JoueurPro")) {

									sender.sendMessage(main.prefix + ChatColor.GREEN + "Permission 'JoueurPro' du joueur activé avec succés !");
									target.sendMessage(main.prefix + ChatColor.GREEN + "Permission 'JoueurPro' activé par " + ChatColor.YELLOW + "La Console" + ChatColor.GREEN + " !");

									target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

								} else { sender.sendMessage(main.prefix + ChatColor.RED + "Permission 'JoueurPro' du joueur déja activé !"); }

							} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub activeJoueurPro" + ChatColor.GREEN + " <player>"); }

					} 

				} else if(args[0].equalsIgnoreCase("reload")) {

					if(sender instanceof Player p) {

						if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise !"); }

						else {

							main.reloadPlugin(); //Recharge le plugin
							p.sendMessage(main.prefix + ChatColor.GREEN + "Le plugin a été rechargé !");
						}

					} else {

						main.reloadPlugin(); //Recharge le plugin
						sender.sendMessage(main.prefix + ChatColor.GREEN + "Le plugin a été rechargé !");
					}

				} else if(args[0].equalsIgnoreCase("spawn")) {

					World world = Bukkit.getServer().getWorld("hub");
					Location WorldSpawn = world.getSpawnLocation();
					Location locspawnworld = new Location(world, WorldSpawn.getX(), WorldSpawn.getY(), WorldSpawn.getZ());

					if(sender instanceof Player p) {

						if(args.length >= 2) {

							if(args.length == 2) {

								Player target = Bukkit.getServer().getPlayer(args[1]);

								if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise !"); }
								else {

									if(target != null) {

										target.teleport(locspawnworld.add(0.0, 1.0, 0.0));

										sender.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez téléporté " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " au spawn du hub/lobby !");
										target.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez été téléporté au spawn du hub/lobby par " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " !");

									} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }
								}

							} else { p.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub spawn" + ChatColor.GREEN + " <player>"); }

						} else {
							if(!p.hasPermission("evhohub.spawn")) { p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise !"); }
							else {

								p.teleport(locspawnworld.add(0.0, 1.0, 0.0));
								sender.sendMessage(main.prefix + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Vous avez été téléporté au spawn du hub/lobby !");
							}
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

										target.teleport(locspawnworld.add(0.0, 1.0, 0.0));

										sender.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez téléporté " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " au spawn du hub/lobby !");
										target.sendMessage(main.prefix + ChatColor.GREEN + "Vous avez été téléporté au spawn du hub/lobby par " + ChatColor.YELLOW + "La Console" + ChatColor.GREEN + " !");

							} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED + "Essayez /evhohub spawn" + ChatColor.GREEN + " <player>"); }

					} 

				} else { sender.sendMessage(main.prefix + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Erreur : " + ChatColor.RED +  "/evhohub help"); }
			}
		return false;
	}

	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/
}

