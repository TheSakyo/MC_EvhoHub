package fr.TheSakyo.EvhoHub.utils.methods;

import fr.TheSakyo.EvhoHub.HubMain;
import fr.TheSakyo.EvhoUtility.UtilityMain;
import fr.TheSakyo.EvhoUtility.config.ConfigFile;
import fr.TheSakyo.EvhoUtility.utils.entity.player.PlayerEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CustomMethod {


	static UtilityMain EvhoUtility = UtilityMain.getInstance(); // Variable récupérant l'API 'EvhoUtility'


	// ~~~~ Méthode pour réinitialiser le menu inventaire du joueur ~~~~ //
	/**
	 * Réinitialise l'inventaire du joueur (récupère le menu les items du hub), définit l'expérience du joueur sur 0
	 *
	 * @param p Le joueur qui recevra le menu
	 */
	public static void ResetMenu(Player p) {

		p.setExp(0); // Remet à zéro les points d'éxpériences du Joueur
		p.setLevel(0); // Remet à zéro les niveaux du Joueur


		//Vide l'inventaire du joueur
		p.getInventory().clear();


		/**/ // Création des différents items du menu // /**/

		ItemStack game = new ItemStack(org.bukkit.Material.CLOCK, 1);
		ItemMeta CustomG = game.getItemMeta();
		CustomG.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Mini-Jeux"));
		CustomG.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.YELLOW + "[!] Cliquez pour jouer [!]")));
		game.setItemMeta(CustomG);

		p.getInventory().setItem(4, game);


		ItemStack shop = new ItemStack(org.bukkit.Material.EMERALD, 1);
		ItemMeta CustomS = shop.getItemMeta();
		CustomS.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + "Shop"));
		CustomS.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.YELLOW + "Achetez quelques petits " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "BONUS " + ChatColor.YELLOW + "!")));
		shop.setItemMeta(CustomS);

		p.getInventory().setItem(6, shop);

		ItemStack option = new ItemStack(org.bukkit.Material.MAGMA_CREAM, 1);
		ItemMeta CustomO = option.getItemMeta();
		CustomO.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.BOLD + "Paramètres"));
		CustomO.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.YELLOW + "Quelques petites options utiles !")));
		option.setItemMeta(CustomO);

		p.getInventory().setItem(8, option);

		 ItemStack hub = new ItemStack(org.bukkit.Material.END_CRYSTAL, 1);
		ItemMeta CustomH = hub.getItemMeta();
		CustomH.displayName(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Hubs"));
		CustomH.lore(List.of(fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.StringToComponent(ChatColor.YELLOW + "Changez de hubs !")));
		hub.setItemMeta(CustomH);

		p.getInventory().setItem(0, hub);

		/**/ // Création des différents items du menu // /**/


		p.updateInventory(); //Actualise l'inventaire du joueur
	}
	// ~~~~ Méthode pour réinitialiser le menu inventaire du joueur ~~~~ //


    // ~~~~ Méthode pour vérifier l'item cliqué dans l'inventaire (PlayerInteractEvent) ~~~~ //

    /**
     * Vérifie si l'élément cliqué est le même que l'élément à vérifier en paramètre
     *
     * @param item L'élément qui a été cliqué
     * @param materialItem La matière de l'item
     * @param displaynameItem Le nom d'affichage de l'élément que vous souhaitez vérifier.*
	 *
     * @return Une Valeur Booléenne
     */
    public static boolean getClickedItem(ItemStack item, Material materialItem, String displaynameItem) {

        String getItemDisplayName = null;

        if(item.getItemMeta() != null && item.getItemMeta().displayName() != null) {

            getItemDisplayName = fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod.ComponentToString(item.getItemMeta().displayName());
        }

        if(item.getType() == materialItem && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && getItemDisplayName.equalsIgnoreCase(displaynameItem)) return true;
        return false;
    }

    // ~~~~ Méthode pour vérifier l'item cliqué dans l'inventaire (PlayerInteractEvent) ~~~~ //


	// ~~~~ Méthode pour connecter un Joueur vers un autre Serveur (Lien avec BungeeCord) ~~~~ //
	/**
	 * Connecte le joueur à un serveur demandé
	 *
	 * @param p Le joueur qui sera envoyé sur le serveur
	 *
	 * @param server Le nom du serveur auquel se connecter.
	 */
	public static void connectToServer(Player p, String server) {

		String[] arguments = new String[]{ p.getName(), server };

		//Fait appel à une méthode "EvhoUtility" pour envoyer les messages venant de "BungeeCord"
		UtilityMain.getInstance().sendMessagePlugin(UtilityMain.channel, "ConnectServer", p, arguments);
	}
	// ~~~~ Méthode pour connecter un Joueur vers un autre Serveur (Lien avec BungeeCord) ~~~~ //


	// ~~~~ Méthode pour cacher les joueurs aux joueurs ayant activé l'option pour cacher les joueurs ~~~~ //
	/**
	 * Cache tous les joueurs d'un joueur en question
	 *
	 * @param p Le joueur qui ne verra plus les autres joueurs.
	 */
	public static void hidePlayers(Player p) {

		// ⬇️ Récupère les Sections à vérifier sur le Fichier de Configuration 'hideplayer.yml' ⬇️ //
		ConfigurationSection configSection = ConfigFile.getConfigurationSection(HubMain.getInstance().hidePlayerCfg, "Player");
		String player = ConfigFile.getString(HubMain.getInstance().hidePlayerCfg, "Player." + p.getUniqueId().toString());
		// ⬆️ Récupère les Sections à vérifier sur le Fichier de Configuration 'hideplayer.yml' ⬆️ //

		// Si ces Sections sont ni 'null', ni vide, on peut cacher les Joueurs au Joueur en question //
		if((configSection != null) && (player != null && !player.isBlank())) {

			EvhoUtility.hidePlayers.put(p.getUniqueId(), Boolean.TRUE); // Ajoute le Joueur à la liste des Joueurs ayant activé l'Option pour cacher les autres Joueurs.

			// ⬇️ Recharge le Joueur en question pour tous les Joueurs Connectés ⬇️ //
			for(Player online : Bukkit.getServer().getOnlinePlayers()) {

				new PlayerEntity(online).update(null, true, true, false);
			}
			// ⬆️ Recharge le Joueur en question pour tous les Joueurs Connectés ⬆️ //
		}
		// ⬆️ Si ces Sections sont ni 'null', ni vide, on peut cacher les Joueurs au Joueur en question ⬆️ //
	}
	// ~~~~ Méthode pour cacher les joueurs aux joueurs ayant activé l'option pour cacher les joueurs ~~~~ //
}
