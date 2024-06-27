/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

package fr.TheSakyo.EvhoHub.manager;

import fr.TheSakyo.EvhoHub.HubMain;
import fr.TheSakyo.EvhoHub.utils.methods.CustomMethod;
import fr.TheSakyo.EvhoHub.utils.ScoreboardPlayer;
import fr.TheSakyo.EvhoHub.utils.methods.ChairsUtils;
import fr.TheSakyo.EvhoUtility.UtilityMain;
import fr.TheSakyo.EvhoUtility.config.ConfigFile;
import fr.TheSakyo.EvhoUtility.managers.ScoreboardManager;
import fr.TheSakyo.EvhoUtility.utils.entity.player.PlayerEntity;
import net.minecraft.ChatFormatting;
import org.bukkit.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.List;
//import org.bukkit.plugin.RegisteredServiceProvider;

//import net.milkbowl.vault.chat.Chat;
//import net.milkbowl.vault.economy.Economy;
//import net.milkbowl.vault.permission.Permission;

/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

public class DefaultListeners implements Listener {

	/* Partie Variables du plugin "Vault" */

	//public static Economy economy = null;
	//public static Permission perms = null;
	//public Chat chat;

	/* Partie Variables du plugin "Vault" */

	/* Récupère la class "Main" */
	private final HubMain main;
	public DefaultListeners(HubMain pluginMain) { this.main = pluginMain; }
	/* Récupère la class "Main" */

	// Variable récupérant l'Instance de l'API 'EvhoUtility' //
	static UtilityMain EvhoUtility = UtilityMain.getInstance();



	// Petit évènement quand le joueur démonte une Entité //
	@EventHandler
	public void onDismountedEntity(EntityDismountEvent e) {

        Entity entityTarget = e.getDismounted();
		if(e.getEntity() instanceof Player p) ChairsUtils.standup(p, entityTarget);
	}
	// Petit évènement quand le joueur démonte une Entité //



	// Évènement quand le joueur quitte le serveur //
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		// Enlève le joueur de la boucle d'actualisation du Scoreboard //
		try { new ScoreboardPlayer(p, main).boards.remove(p.getUniqueId());}
		catch(NullPointerException ignored) {}
		// Enlève le joueur de la boucle d'actualisation du Scoreboard //


		// Reset le scoreboard du joueur en le recréant sans aucune instance (Utilise l'api EvhoUtility) //
		ScoreboardManager.makeScoreboard(p, true);
		// Reset le scoreboard du joueur en le recréant sans aucune instance (Utilise l'api EvhoUtility) //

	}
	// Évènement quand le joueur quitte le serveur //



	// Évènement quand le joueur rejoint le serveur //
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();

		if(!p.hasPlayedBefore()) {

			p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.visibility", false);
			p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.bypass", false);
		}

		// ⬇️ Téléporte le Joueur au Spawn du Hub ⬇️ //
		World world = Bukkit.getServer().getWorld("hub");
		if(world != null) {

			Location spawnLocation = world.getSpawnLocation();
			Location loc = new Location(world, spawnLocation.getX(), spawnLocation.getY() + 1, spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());

			p.teleport(loc);
		}
		// ⬆️ Téléporte le Joueur au Spawn du Hub ⬆️ //

		CustomMethod.ResetMenu(p); //Réinitialise le menu

		p.getInventory().setHeldItemSlot(4); //Définit le "slot" occupant la main du joueur

		CustomMethod.hidePlayers(p); //Appel la méthode "hidePlayer"

		// Aprés deux secondes, on affiche le Scoreboard au joueur //
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {

            ScoreboardPlayer scoreboard = new ScoreboardPlayer(p, main);
            scoreboard.getScoreBoard(true, true);
        }, 20 * 2);
		// Aprés deux secondes, on affiche le Scoreboard au joueur //
	}
	// Évènement quand le joueur rejoint le serveur //



	// Évènement quand le joueur intéragit avec des items (intéractions avec les items du menu) //
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		Action action = e.getAction();
		ItemStack it = e.getItem();

		// On Vérifie si l'action est un clic droit sur un bloc //
		if(action == Action.RIGHT_CLICK_BLOCK) {

			// Assied le Joueur sur l'Entité (flèche), s'il n'utilise pas la main principale (évite répétition)
			if(e.getHand() != EquipmentSlot.HAND) ChairsUtils.sitDown(p, e.getClickedBlock());
		}
		// On Vérifie si l'action est un clic droit sur un bloc //

													/* ------------------------------------------- */

		// Si l'item auquel le joueur a cliqué est null, on ne fait rien
		if(it == null) return;


													/* ------------------------------------------- */

									/* ⬇️ SINON ON VÉRIFIE L'ITEM CLIQUÉ POUR EFFECTUER ENSUITE UNE ACTION ⬇️ */

		if(CustomMethod.getClickedItem(it, Material.CLOCK, ChatFormatting.AQUA.toString() + ChatFormatting.BOLD.toString() + "Mini-Jeux")) {

			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

				Inventory inv = Bukkit.createInventory(null, 54, fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_GRAY + "Mini-Jeux"));

				p.openInventory(inv);

				/*ItemStack build = new ItemStack(Material.BRICKS, 1);
				ItemMeta CustomB = build.getItemMeta();
				CustomB.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.GOLD.toString() + ChatFormatting.BOLD.toString() + "BUILD-CRAFT"));
				CustomB.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Mode de jeu créatif, montrez vos talents de build !")));
				build.setItemMeta(CustomB);
				inv.setItem(20, build);

				ItemStack fac = new ItemStack(Material.OBSIDIAN, 1);
				ItemMeta CustomF = fac.getItemMeta();
				CustomF.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_AQUA.toString() + ChatFormatting.BOLD.toString() + "PVP-FACTION"));
				CustomF.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Devenez la faction la plus riche du jeu !")));
				fac.setItemMeta(CustomF);
				inv.setItem(24, fac);

				ItemStack parkour = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
				ItemMeta CustomP = parkour.getItemMeta();
				CustomP.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.LIGHT_PURPLE.toString() + ChatFormatting.BOLD.toString() + "Parkour"));
				CustomP.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Essayez de gagnez le premier !")));
				parkour.setItemMeta(CustomP);
				inv.setItem(28, parkour);*/

				ItemStack arena = new ItemStack(Material.DIAMOND_SWORD, 1);
				ItemMeta CustomA = arena.getItemMeta();
				CustomA.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.GREEN.toString() + ChatFormatting.BOLD.toString() + "PvP-Arena"));
				CustomA.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Survivez le dernier dans l'arène !")));
				arena.setItemMeta(CustomA);
				inv.setItem(30, arena);

				/*ItemStack paint = new ItemStack(Material.SNOWBALL, 1);
				ItemMeta CustomPB = paint.getItemMeta();
				CustomPB.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.BLUE.toString() + ChatFormatting.BOLD.toString() + "PaintBall"));
				CustomPB.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Remportez le plus de points en attaquant vos adversaires !")));
				paint.setItemMeta(CustomPB);
				inv.setItem(32, paint);

				ItemStack dropper = new ItemStack(Material.HOPPER, 1);
				ItemMeta CustomD = dropper.getItemMeta();
				CustomD.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Dropper"));
				CustomD.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Tombez sans vous faire mal !")));
				dropper.setItemMeta(CustomD);
				inv.setItem(34, dropper);*/

				ItemStack close = new ItemStack(Material.BARRIER, 1);
				ItemMeta CustomC = close.getItemMeta();
				CustomC.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_RED.toString() + ChatFormatting.BOLD.toString() + "Quitter"));
				CustomC.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.RED + "Quittez le menu !")));
				close.setItemMeta(CustomC);
				inv.setItem(53, close);

			}

		} if(CustomMethod.getClickedItem(it, Material.EMERALD, ChatFormatting.DARK_GREEN.toString() + ChatFormatting.BOLD.toString() + "Shop")) {

			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

				Inventory inv = Bukkit.createInventory(null, 36, fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_GRAY + "Shop-Menu"));

				p.openInventory(inv);

				ItemStack GradeJP = new ItemStack(Material.DIAMOND, 1);
				ItemMeta CustomJP = GradeJP.getItemMeta();
				CustomJP.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.GREEN.toString() + ChatFormatting.BOLD.toString() + "Joueur Pro"));
				CustomJP.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Devenez Joueur Pro avec 2000$")));
				GradeJP.setItemMeta(CustomJP);
				inv.setItem(10, GradeJP);

				ItemStack close = new ItemStack(Material.BARRIER, 1);
				ItemMeta CustomC = close.getItemMeta();
				CustomC.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_RED.toString() + ChatFormatting.BOLD.toString() + "Quitter"));
				CustomC.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.RED + "Quittez le menu !")));
				close.setItemMeta(CustomC);
				inv.setItem(35, close);

			}

		} if(CustomMethod.getClickedItem(it, Material.MAGMA_CREAM, ChatFormatting.BOLD + "Paramètres")) {

			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

				Inventory inv = Bukkit.createInventory(null, 27, fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_GRAY + "Options"));

				p.openInventory(inv);

				ItemStack reveal = new ItemStack(Material.ENDER_EYE, 1);
				ItemMeta CustomR = reveal.getItemMeta();
				CustomR.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.GREEN.toString() + ChatFormatting.BOLD.toString() + "Afficher les joueurs"));
				CustomR.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Afficher tous les joueurs dans le hub")));
				reveal.setItemMeta(CustomR);
				inv.setItem(11, reveal);

				ItemStack hide = new ItemStack(Material.SLIME_BALL, 1);
				ItemMeta CustomHD = hide.getItemMeta();
				CustomHD.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Cacher les joueurs"));
				CustomHD.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.WHITE + "Cacher tous les joueurs dans le hub")));
				hide.setItemMeta(CustomHD);
				inv.setItem(15, hide);

				ItemStack close = new ItemStack(Material.BARRIER, 1);
				ItemMeta CustomC = close.getItemMeta();
				CustomC.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_RED.toString() + ChatFormatting.BOLD.toString() + "Quitter"));
				CustomC.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.RED + "Quittez le menu !")));
				close.setItemMeta(CustomC);
				inv.setItem(26, close);

			}

		} if(CustomMethod.getClickedItem(it, Material.END_CRYSTAL, ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Hubs")) {

			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

				Inventory inv = Bukkit.createInventory(null, 27, fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_GRAY + "Hubs"));

				p.openInventory(inv);

				ItemStack close = new ItemStack(Material.BARRIER, 1);
				ItemMeta CustomC = close.getItemMeta();
				CustomC.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.DARK_RED.toString() + ChatFormatting.BOLD.toString() + "Quitter"));
				CustomC.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.RED + "Quittez le menu !")));
				close.setItemMeta(CustomC);
				inv.setItem(26, close);

				ItemStack hub1 = new ItemStack(Material.PURPLE_BED, 1);
				ItemMeta CustomH1 = hub1.getItemMeta();
				CustomH1.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 1"));
				hub1.setItemMeta(CustomH1);
				inv.setItem(0, hub1);

				/*ItemStack hub2 = new ItemStack(Material.PURPLE_BED, 1);
				ItemMeta CustomH2 = hub2.getItemMeta();
				CustomH2.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 2"));
				hub2.setItemMeta(CustomH2);
				inv.setItem(2, hub2);

				ItemStack hub3 = new ItemStack(Material.PURPLE_BED, 1);
				ItemMeta CustomH3 = hub3.getItemMeta();
				CustomH3.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 3"));
				hub3.setItemMeta(CustomH3);
				inv.setItem(4, hub3);

				ItemStack hub4 = new ItemStack(Material.PURPLE_BED, 1);
				ItemMeta CustomH4 = hub4.getItemMeta();
				CustomH4.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 4"));
				hub4.setItemMeta(CustomH4);
				inv.setItem(6, hub4);

				ItemStack hub5 = new ItemStack(Material.PURPLE_BED, 1);
				ItemMeta CustomH5 = hub5.getItemMeta();
				CustomH5.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 5"));
				hub5.setItemMeta(CustomH5);
				inv.setItem(8, hub5);*/

			}
		}
											/* ⬆️ SINON ON VÉRIFIE L'ITEM CLIQUÉ POUR EFFECTUER ENSUITE UNE ACTION ⬆️ */
	}
	// Évènement quand le joueur intéragit avec des items (intéractions avec les items du menu) //



	// Évènement quand le joueur clique sur un inventaire (pour les intéractions avec le menu) //
	@EventHandler
	public void onClick(InventoryClickEvent e) {

		Player p = (Player)e.getWhoClicked();
		ItemStack current = e.getCurrentItem();

		String title = fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.ComponentToString(e.getView().title());

		if(current == null) return;

											/* ⬇️ SINON ON VÉRIFIE L'ITEM CLIQUÉ POUR EFFECTUER ENSUITE UNE ACTION ⬇️ */

		if(e.getClickedInventory().getType() == InventoryType.PLAYER) {

            e.setCancelled(!fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.hasByPassPerm(p));

		} else {

			if(title.equalsIgnoreCase(ChatFormatting.DARK_GRAY + "Mini-Jeux")) {

				e.setCancelled(true);
				p.closeInventory();

				if(CustomMethod.getClickedItem(current, Material.BRICKS, ChatFormatting.GOLD.toString() + ChatFormatting.BOLD.toString() + "BUILD-CRAFT")) {

					CustomMethod.connectToServer(p, "buildcraft");

				} if(CustomMethod.getClickedItem(current, Material.OBSIDIAN, ChatFormatting.DARK_AQUA.toString() + ChatFormatting.BOLD.toString() + "PVP-FACTION")) {

					CustomMethod.connectToServer(p, "faction");

				} if(CustomMethod.getClickedItem(current, Material.CHAINMAIL_BOOTS, ChatFormatting.LIGHT_PURPLE.toString() + ChatFormatting.BOLD.toString() + "Parkour")) {

					CustomMethod.connectToServer(p, "parkour");

				} if(CustomMethod.getClickedItem(current, Material.DIAMOND_SWORD, ChatFormatting.GREEN.toString() + ChatFormatting.BOLD.toString() + "PvP-Arena")) {

					CustomMethod.connectToServer(p, "arena_game");

				} if(CustomMethod.getClickedItem(current, Material.SNOWBALL, ChatFormatting.BLUE.toString() + ChatFormatting.BOLD.toString() + "PaintBall")) {

					CustomMethod.connectToServer(p, "paintball");

				} if(CustomMethod.getClickedItem(current, Material.HOPPER, ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Dropper")) {

					CustomMethod.connectToServer(p, "dropper");
				}

			} if(title.equalsIgnoreCase(ChatFormatting.DARK_GRAY + "Options")) {

				e.setCancelled(true);
				p.closeInventory();

				if(CustomMethod.getClickedItem(current, Material.ENDER_EYE, ChatFormatting.GREEN.toString() + ChatFormatting.BOLD.toString() + "Afficher les joueurs")) {

					p.sendMessage(ChatFormatting.GRAY + ">> " + ChatFormatting.GREEN + "Les joueurs sont visibles");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.visibility", false);

									/* ------------------------------------------- */


					String playerInConfig = ConfigFile.getString(main.hidePlayerCfg, "Player." + p.getUniqueId().toString());
					if(playerInConfig != null && !playerInConfig.isBlank()) { ConfigFile.removeKey(main.hidePlayerCfg, "Player." + p.getUniqueId().toString()); }


					EvhoUtility.hidePlayers.remove(p.getUniqueId()); // Enlève le Joueur à la liste des Joueurs ayant activé l'Option pour cacher les autres Joueurs.

									/* ---------------------------- */

					// ⬇️ Recharge le Joueur en question pour tous les Joueurs Connectés ⬇️ //
					for(Player online : Bukkit.getServer().getOnlinePlayers()) {

						new PlayerEntity(online).update(null, true, true, false);
					}
					// ⬆️ Recharge le Joueur en question pour tous les Joueurs Connectés ⬆️ //
				}

				if(CustomMethod.getClickedItem(current, Material.SLIME_BALL, ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Cacher les joueurs")) {

					p.sendMessage(ChatFormatting.GRAY + ">> " + ChatFormatting.GREEN + "Les joueurs sont cachés");
					p.addAttachment(HubMain.pm.getPlugin("EvhoHub")).setPermission("evhohub.visibility", true);

									/* ------------------------------------------- */

					ConfigFile.set(main.hidePlayerCfg, "Player." + p.getUniqueId().toString(), "Option_Activated");

					EvhoUtility.hidePlayers.put(p.getUniqueId(), Boolean.TRUE); // Ajoute le Joueur à la liste des Joueurs ayant activé l'Option pour cacher les autres Joueurs.

							/* ---------------------------- */

					// ⬇️ Recharge le Joueur en question pour tous les Joueurs Connectés ⬇️ //
					for(Player online : Bukkit.getServer().getOnlinePlayers()) {

						new PlayerEntity(online).update(null, true, true, false);
					}
					// ⬆️ Recharge le Joueur en question pour tous les Joueurs Connectés ⬆️ //
				}

			} if(title.equalsIgnoreCase(ChatFormatting.DARK_GRAY + "Shop-Menu")) {

				e.setCancelled(true);
				p.closeInventory();

				if(CustomMethod.getClickedItem(current, Material.DIAMOND, ChatFormatting.GREEN.toString() + ChatFormatting.BOLD.toString() + "Joueur Pro")) {

					if(p.hasPermission("evhohub.JoueurPro")) { p.sendMessage(ChatFormatting.GRAY + ">> " + ChatFormatting.RED + "Vous ne pouvez pas acheter ce grade, ceci est surement votre grade ou alors vous êtes plus haut-gradé !"); }

					else {

						p.sendMessage(ChatFormatting.GRAY + ">> " + ChatFormatting.RED + "Impossible d'acheter ce grade pour le moment !");

					/* if(setupEconomy()){

						double balance = economy.getBalance(p);

						if(balance >= 2000) {

							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd" + " " + p.getName() + " " + "JoueurPro");
							economy.withdrawPlayer(p, 2000);
							p.addAttachment(EvhoHub.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", true);
							p.sendMessage(main.prefix + ChatFormatting.DARK_GREEN.toString() + ChatFormatting.BOLD.toString() + "Félicitation : " + ChatFormatting.GREEN + "Vous venez d'acheter un grade !");

						} else {

							p.addAttachment(EvhoHub.pm.getPlugin("EvhoHub")).setPermission("evhohub.JoueurPro", false);
							p.sendMessage(main.prefix + ChatFormatting.RED.toString() + ChatFormatting.BOLD.toString() + "Erreur : " + ChatFormatting.RED + "Vous n'avez pas assez d'argent !");
						 }
					  } */
				   }
				}

			} if(title.equalsIgnoreCase(ChatFormatting.DARK_GRAY + "Hubs")) {

				e.setCancelled(true);
				p.closeInventory();

				if(CustomMethod.getClickedItem(current, Material.PURPLE_BED, ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 1")) {

					CustomMethod.connectToServer(p, "hub");

				} if(CustomMethod.getClickedItem(current, Material.PURPLE_BED, ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 2")) {

					CustomMethod.connectToServer(p, "hub2");

				} if(CustomMethod.getClickedItem(current, Material.PURPLE_BED, ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 3")) {

					CustomMethod.connectToServer(p, "hub3");

				} if(CustomMethod.getClickedItem(current, Material.PURPLE_BED, ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 4")) {

					CustomMethod.connectToServer(p, "hub4");

				} else if(CustomMethod.getClickedItem(current, Material.PURPLE_BED, ChatFormatting.YELLOW.toString() + ChatFormatting.BOLD.toString() + "HUB 5")) {

					CustomMethod.connectToServer(p, "hub5");
				}
			}
		}
											/* ⬆️ SINON ON VÉRIFIE L'ITEM CLIQUÉ POUR EFFECTUER ENSUITE UNE ACTION ⬆️ */
	}
	// Évènement quand le joueur clique sur un inventaire (pour les intéractions avec le menu) //



	// Évènement quand le joueur jette un item //
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {

		Player p = e.getPlayer();
        e.setCancelled(!fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.hasByPassPerm(p));
	}
	// Évènement quand le joueur jette un item //


	// Évènement quand le joueur ramasse un item //
	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {

		if(e.getEntity() instanceof Player p) { e.setCancelled(!fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.hasByPassPerm(p)); }
	}
	// Évènement quand le joueur ramasse un item //



	// Évènement quand le joueur casse un bloc //
	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		Player p = e.getPlayer();
        e.setCancelled(!fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.hasByPassPerm(p));
	}
	// Évènement quand le joueur casse un bloc //


	// Évènement quand le joueur pose un bloc //
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {

		Player p = e.getPlayer();
        e.setCancelled(!fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.hasByPassPerm(p));
	}
	// Évènement quand le joueur pose un bloc //



	// Évènement quand le joueur meurt (le téléporte et réinitialise le menu) //
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
    	Player victim = e.getEntity();

		Location WorldSpawn = victim.getWorld().getSpawnLocation();
		Location locspawnworld = new Location(victim.getWorld(), WorldSpawn.getX(), WorldSpawn.getY(), WorldSpawn.getZ());

		e.setCancelled(true);

		victim.setHealth(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
		victim.teleport(locspawnworld.add(0.0, 1.0, 0.0));

		CustomMethod.ResetMenu(victim);
	}
	// Évènement quand le joueur meurt (le téléporte et réinitialise le menu) //


	// Évènement quand le joueur perd des dégâts (annule l'évènement s'il n'a pas la permission des dommages + si le joueur tombe dans le vide, il est téléporté au spawn du monde) //
	@EventHandler
	public void onDamage(EntityDamageEvent e) {

		if(!(e.getEntity() instanceof Player victim)) return;

		Location WorldSpawn = victim.getWorld().getSpawnLocation();
		Location locspawnworld = new Location(victim.getWorld(), WorldSpawn.getX(), WorldSpawn.getY(), WorldSpawn.getZ());

		if(e.getCause() == DamageCause.VOID) {

			e.setDamage(0);

			victim.sendMessage(main.prefix + ChatFormatting.RED + "Vous ne pouvez pas mourir dans le vide !");
			victim.teleport(locspawnworld.add(0.0, 1.0, 0.0));

			CustomMethod.ResetMenu(victim);

		} else { if(!victim.hasPermission("evhohub.damage")) e.setCancelled(true); }
	}
	// Évènement quand le joueur perd des dégâts (annule l'évènement s'il n'a pas la permission des dommages + si le joueur tombe dans le vide, il est téléporté au spawn du monde) //



	// Évènement quand le joueur perd des niveaux de faims (on annule donc l'évènement, sauf s'il a la permission des dommages) //
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {

		if(!(e.getEntity() instanceof Player target)) return;
		if(!target.hasPermission("evhohub.damage")) e.setCancelled(true);
	}
	// Évènement quand le joueur perd des niveaux de faims (on annule donc l'évènement, sauf s'il a la permission des dommages) //


												/* ------------------------------------------ */


	/** \ -- Partie Chargement de plugin "Vault" -- \ **/

	 /* private boolean setupEconomy() {

	   	  RegisteredServiceProvider<Economy> economyProvider = EvhoHub.pm.getPlugin("EvhoHub").getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

		  if(economyProvider != null) { economy = economyProvider.getProvider();}

		  return (economy != null);
	  	} */

	/** \ -- Partie Chargement de plugin "Vault" -- \ **/
}
