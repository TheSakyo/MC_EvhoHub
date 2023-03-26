/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

package fr.TheSakyo.EvhoHub.manager;

import fr.TheSakyo.EvhoHub.HubMain;
import fr.TheSakyo.EvhoHub.utils.methods.CustomMethod;
import net.minecraft.ChatFormatting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

public class HubCommand implements CommandExecutor {

	/* Récupère la class "Main" */
	private final HubMain main;
	public HubCommand(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */


	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
				
			if(args.length == 0) Bukkit.dispatchCommand(sender, "evhohub help");
			else {

				if(args[0].equalsIgnoreCase("help")) {

					sender.sendMessage(ChatFormatting.WHITE.toString() + ChatFormatting.BOLD.toString() + "----" + " " + ChatFormatting.GOLD.toString() + ChatFormatting.BOLD.toString() + "EvhoHub" + " " + ChatFormatting.WHITE.toString() + ChatFormatting.BOLD.toString() +  "----");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(ChatFormatting.AQUA.toString() + ChatFormatting.UNDERLINE.toString() + ChatFormatting.BOLD.toString() + "Alias :" + ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + " /eh");
					sender.sendMessage(" ");
					sender.sendMessage(ChatFormatting.GREEN + "/evhohub help" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Affiche toutes les commandes du plugin");
					sender.sendMessage(" ");
					sender.sendMessage(ChatFormatting.GREEN + "/evhohub resetmenu" + ChatFormatting.RED + " [<player>]" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Réinitialise le menu dans l'inventaire");
					sender.sendMessage(" ");
					sender.sendMessage(ChatFormatting.GREEN + "/chairs" + ChatFormatting.RED + " [<player>]" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Active/Désactive le fait de s'asseoir sur des escaliers");
					sender.sendMessage(" ");
					sender.sendMessage(ChatFormatting.GREEN + "/spawn" + ChatFormatting.RED + " [<player>]" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Téléportation au point de spawn du hub/lobby");

					String toggleDamageInfoCommand = ChatFormatting.GREEN + "/damage" + ChatFormatting.RED + " [<player>]" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Active ou non les dégats d'un joueur";

					String resetJoueurProInfoCommand = ChatFormatting.GREEN + "/evhohub resetJoueurPro" + ChatFormatting.RED + " <player>" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Remet à zéro les permissions du grade JoueurPro au(x) joueur(s) précisé(s)";
					String ActiveJoueurProInfoCommand = ChatFormatting.GREEN + "/evhohub ActiveJoueurPro" + ChatFormatting.RED + " <player>" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Active les permissions du grade JoueurPro au(x) joueur(s) précisé(s)";

					String reloadInfoCommand = ChatFormatting.GREEN + "/evhohub reload" + ChatFormatting.WHITE + " - " + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Recharge le plugin";

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
					sender.sendMessage(ChatFormatting.WHITE.toString() + ChatFormatting.BOLD.toString() + "------------------");


				} else if(args[0].equalsIgnoreCase("resetmenu")) {

					if(sender instanceof Player p) {

						if(args.length >= 2) {

							if(!p.hasPermission("evhohub.admin")) { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub reset"); }

							else {

								if(args.length == 2) {

									Player target = Bukkit.getServer().getPlayer(args[1]);

									if(target != null) {

										CustomMethod.ResetMenu(target); //Réinitialise le Menu
										p.sendMessage(main.prefix + ChatFormatting.GREEN + "L'inventaire de " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " a bien été réinitialiser !");
										target.sendMessage(main.prefix + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " a réinitialiser votre inventaire !");

									} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

								} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub reset" + ChatFormatting.GREEN + " <player>"); }
							}

						} else {

							CustomMethod.ResetMenu(p); //Réinitialise le Menu
							p.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez réinitialiser votre inventaire !");
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

								CustomMethod.ResetMenu(target); //Réinitialise le Menu
								sender.sendMessage(main.prefix + ChatFormatting.GREEN + "L'inventaire de " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " a bien été remis à zéro !");
								target.sendMessage(main.prefix + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " a remis à zéro votre inventaire !");

							} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub reset" + ChatFormatting.GREEN + " <player>"); }

					} 

				} else if(args[0].equalsIgnoreCase("resetJoueurPro")) {

					if(sender instanceof Player p) {

						if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }

						else {

							if(args.length == 2) {

								Player target = Bukkit.getServer().getPlayer(args[1]);

								if(target != null) {

									if(target.hasPermission("evhohub.JoueurPro")) {

										p.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission du joueur remis à zéro avec succés !");
										target.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission remis à zéro par " + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " !");

										target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

									} else { p.sendMessage(main.prefix + ChatFormatting.RED + "Permission du joueur déja remis à zéro !"); }

								} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

							} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub resetJoueurPro" + ChatFormatting.GREEN + " <player>"); }
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

								if(target.hasPermission("evhohub.JoueurPro")) {

									sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission du joueur remis à zéro avec succés !");
									target.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission remis à zéro par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

									target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

								} else { sender.sendMessage(main.prefix + ChatFormatting.RED + "Permission du joueur déja remis à zéro !"); }

							} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub resetJoueurPro" + ChatFormatting.GREEN + " <player>"); }

					} 

				} else if(args[0].equalsIgnoreCase("activeJoueurPro")) {

					if(sender instanceof Player p) {

						if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }

						else {

							if(args.length == 2) {

								Player target = Bukkit.getServer().getPlayer(args[1]);

								if(target != null) {

									if(!target.hasPermission("evhohub.JoueurPro")) {

										p.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission 'JoueurPro' du joueur activé avec succés !");
										target.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission 'JoueurPro' activé par " + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " !");

										target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

									} else { p.sendMessage(main.prefix + ChatFormatting.RED + "Permission 'JoueurPro' du joueur déja activé !"); }

								} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

							} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub activeJoueurPro" + ChatFormatting.GREEN + " <player>"); }
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

								if(!target.hasPermission("evhohub.JoueurPro")) {

									sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission 'JoueurPro' du joueur activé avec succès !");
									target.sendMessage(main.prefix + ChatFormatting.GREEN + "Permission 'JoueurPro' activé par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

									target.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);

								} else { sender.sendMessage(main.prefix + ChatFormatting.RED + "Permission 'JoueurPro' du joueur déja activé !"); }

							} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub activeJoueurPro" + ChatFormatting.GREEN + " <player>"); }

					} 

				} else if(args[0].equalsIgnoreCase("reload")) {

					if(sender instanceof Player p) {

						if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }

						else {

							main.reloadPlugin(); //Recharge le plugin
							p.sendMessage(main.prefix + ChatFormatting.GREEN + "Le plugin a été rechargé !");
						}

					} else {

						main.reloadPlugin(); //Recharge le plugin
						sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Le plugin a été rechargé !");
					}

				} else if(args[0].equalsIgnoreCase("spawn")) {

					World world = Bukkit.getServer().getWorld("hub");
					Location WorldSpawn = world.getSpawnLocation();
					Location locspawnworld = new Location(world, WorldSpawn.getX(), WorldSpawn.getY(), WorldSpawn.getZ());

					if(sender instanceof Player p) {

						if(args.length >= 2) {

							if(args.length == 2) {

								Player target = Bukkit.getServer().getPlayer(args[1]);

								if(!p.hasPermission("evhohub.admin")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }
								else {

									if(target != null) {

										target.teleport(locspawnworld.add(0.0, 1.0, 0.0));

										sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez téléporté " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " au spawn du hub/lobby !");
										target.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez été téléporté au spawn du hub/lobby par " + ChatFormatting.YELLOW + p.getName() + ChatFormatting.GREEN + " !");

									} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }
								}

							} else { p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub spawn" + ChatFormatting.GREEN + " <player>"); }

						} else {
							if(!p.hasPermission("evhohub.spawn")) { p.sendMessage(ChatFormatting.RED + "Vous n'avez pas la permission requise !"); }
							else {

								p.teleport(locspawnworld.add(0.0, 1.0, 0.0));
								sender.sendMessage(main.prefix + ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC.toString() + "Vous avez été téléporté au spawn du hub/lobby !");
							}
						}

					} else {

						if(args.length == 2) {

							Player target = Bukkit.getServer().getPlayer(args[1]);

							if(target != null) {

										target.teleport(locspawnworld.add(0.0, 1.0, 0.0));

										sender.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez téléporté " + ChatFormatting.GOLD + target.getName() + ChatFormatting.GREEN + " au spawn du hub/lobby !");
										target.sendMessage(main.prefix + ChatFormatting.GREEN + "Vous avez été téléporté au spawn du hub/lobby par " + ChatFormatting.YELLOW + "La Console" + ChatFormatting.GREEN + " !");

							} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Le joueur est introuvable !"); }

						} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Essayez /evhohub spawn" + ChatFormatting.GREEN + " <player>"); }

					} 

				} else { sender.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED +  "/evhohub help"); }
			}
		return false;
	}

	/*******************************/
	/* COMMANDE DU PLUGIN EVHOHUB */
	/******************************/
}

