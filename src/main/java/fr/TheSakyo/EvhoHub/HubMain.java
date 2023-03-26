/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

package fr.TheSakyo.EvhoHub;

import fr.TheSakyo.EvhoHub.config.ConfigFileManager;
import fr.TheSakyo.EvhoHub.manager.HubCommand;
import fr.TheSakyo.EvhoHub.manager.DefaultListeners;
import fr.TheSakyo.EvhoHub.manager.commands.ChairsCommand;
import fr.TheSakyo.EvhoHub.manager.commands.DamageCommand;
import fr.TheSakyo.EvhoHub.manager.commands.SpawnCommand;
import fr.TheSakyo.EvhoHub.utils.methods.CustomMethod;
import fr.TheSakyo.EvhoHub.utils.ScoreboardPlayer;
import fr.TheSakyo.EvhoUtility.UtilityMain;
import fr.TheSakyo.EvhoUtility.config.ConfigFile;
import net.minecraft.ChatFormatting;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.TimerTask;

/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

public class HubMain extends JavaPlugin implements Listener {

	//Variable pour récupérer la méthode "PluginManager" (sert à gérer le plugin)
   	public static PluginManager pm = Bukkit.getPluginManager();

	//Variable pour récupérer le plugin
   	public static Plugin plugin;

	// Variable avec l'instance globale //
   	private static HubMain instance;
	// Variable avec l'instance globale //

	// Variable pour Détecter la Console //
   	public ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	// Variable pour Détecter la Console //

	// Variable pour le Nom du Plugin //
    public String prefix = ChatFormatting.WHITE + "[" + ChatFormatting.GOLD + "EvhoHub" + ChatFormatting.WHITE + "]" + " " + ChatFormatting.RESET;
	// Variable pour le Nom du Plugin //


	// Fichier Configuration stockant les Joueurs ayant activé l'Option pour cacher les Joueurs //
	public ConfigFile hidePlayerCfg;
	// Fichier Configuration stockant les Joueurs ayant activé l'Option pour cacher les Joueurs //


	/* Vérifie si le plugin "EvhoUtility" éxiste dans le serveur */
	private Plugin getUtilityPlugin() { return Bukkit.getServer().getPluginManager().getPlugin("EvhoUtility"); }
	/* Vérifie si le plugin "EvhoUtility" éxiste dans le serveur */

	/* Retourne l'instance du plugin  */
	public static HubMain getInstance() { return instance; }
	/* Retourne l'instance du plugin  */

	/*************************************************************/
	/* PARTIE AVEC ACTIVATION/DÉSACTIVATION/CHARGEMENT DU PLUGIN */
	/************************************************************/


	/* Initialisation (Pour bien démarrer le Plugin) */
	private void init() {

		// ⬇️ Retourne l'Instance du Plugin et le Plugin vers cette 'Class' Principal "EvhoHubMain" ⬇️ //
		plugin = this;
		instance = this;
		// ⬆️ Retourne l'Instance du Plugin et le Plugin vers cette 'Class' Principal "EvhoHubMain" ⬆️ //

		ConfigFileManager.LoadConfig(); //Recharge les fichiers de configurations du Plugin utiles pour le serveur

		// ⬇️ Vérifie si le serveur fonctionne sous bungee, si c'est le cas, on enregistre les canaux 'bungee' ⬇️ //
		if(UtilityMain.getInstance().hasBungee()) {

			this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		}
		//⬆️ Vérifie si le serveur fonctionne sous bungee, si c'est le cas, on enregistre les canaux 'bungee' ⬆️ //


		// ⬇️ Pour tous les Joueurs en ligne, on Cache les Joueurs à ceux qui ont activé l'Option ⬇️ //
		for(Player players : Bukkit.getServer().getOnlinePlayers()) {

			CustomMethod.hidePlayers(players); //Appel la méthode "hidePlayer"
		}
		// ⬆️ Pour tous les Joueurs en ligne, on Cache les Joueurs à ceux qui ont activé l'Option ⬆️ //
	}
	/* Initialisation (Pour bien démarrer le Plugin) */


	/* Activation du plugin */
    public void onEnable() {

		//Vérifie si trouve bien le plugin "EvhoUtility" dans le serveur
		if(getUtilityPlugin().isEnabled()) {

			this.init(); //Fait Appel à une méthode pour bien initialiser le Plugin

			// Message disant que le plugin est activé //
			console.sendMessage(prefix + ChatFormatting.GREEN + "Hub/Lobby Enabled !");
			// Message disant que le plugin est activé //

			//Récupère la class des évènements //
			this.getServer().getPluginManager().registerEvents(new DefaultListeners(this), this);
			//Récupère la class des évènements //

			// Récupère la commande du plugin //
			this.getCommand("evhohub").setExecutor(new HubCommand(this));
			this.getCommand("eh").setExecutor(new HubCommand(this));
			// Récupère la commande du plugin //


			// Récupère quelque commande utile du plugin //
			this.getCommand("chairs").setExecutor(new ChairsCommand(this));
			this.getCommand("spawn").setExecutor(new SpawnCommand(this));
			this.getCommand("damage").setExecutor(new DamageCommand(this));
			// Récupère quelque commande utile du plugin //

			this.updateScoreboardAndMenuGUI(); //Actualise le Scoreboard et le Menu 'GUI' pour les joueurs

		} else {

			//Demande le plugin "EvhoUtility" pour le fonctionnement du plugin
			console.sendMessage("");
			console.sendMessage(prefix + ChatFormatting.RED + "Veuillez nous excuser, ce plugin requiert un plugin spécifique !");
			console.sendMessage(prefix + ChatFormatting.RED + "Le plugin est le suivant : " + ChatFormatting.YELLOW + "EvhoUtility");
			console.sendMessage("");

			//Désactive le plugin
			pm.disablePlugin(plugin);
		}
    }
	/* Activation du plugin */



	/* Désactivation du plugin */
	public void onDisable() {

  		//Retourne l'instance du plugin en "NULL"
  		instance = null;

		// Message disant que le plugin est désactivé //
  		console.sendMessage(prefix + ChatFormatting.RED + "Hub/Lobby Disabled !");
	}
	/* Désactivation du plugin */



	/*******************************************************/
	/****** ⬇️ PARTIE AVEC RECHARGEMENT DU PLUGIN ⬇️ ******/
	/******************************************************/

	public void reloadPlugin() {

		this.init(); //Fait Appel à une méthode pour bien initialiser le Plugin
		this.updateScoreboardAndMenuGUI(); //Actualise le Scoreboard et le Menu 'GUI' pour les joueurs
	}

	/*******************************************************/
	/****** ⬆️ PARTIE AVEC RECHARGEMENT DU PLUGIN ⬆️ ******/
	/******************************************************/


	/*************************************************************/
	/* PARTIE AVEC ACTIVATION/DÉSACTIVATION/CHARGEMENT DU PLUGIN */
	/************************************************************/




	/*** ⬇️ Actualise le Scoreboard et le Menu 'GUI' pour les joueurs ⬇️ ***/
	private void updateScoreboardAndMenuGUI() {

		// ⬇️ Aprés deux secondes, on actualise le Menu et le scoreboard pour tous les joueurs ⬇️ //
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {

				// ⬇️ Actualise le menu et le scoreboard pour tous les joueurs ⬇️ //
				for(Player online : Bukkit.getServer().getOnlinePlayers()) {

					CustomMethod.ResetMenu(online); //Actualise le menu
					UtilityMain.getInstance().sendToBungee("BungeeCount", "all"); //Récupère le nombre de jouer(s) en ligne (Bungee)

								/* ----------------------------------- */

					// Aprés trois secondes, on affiche le Scoreboard au joueur //
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {

                        ScoreboardPlayer scoreboard = new ScoreboardPlayer(online, getInstance());
                        scoreboard.getScoreBoard(true, true);
                    }, 20 * 3);
					// Aprés trois secondes, on affiche le Scoreboard au joueur //
				}
				// ⬆️ Actualise le menu et le scoreboard pour tous les joueurs ⬆️ //
			}

		}, 2 * 1000);
		// ⬆️ Aprés deux secondes, on actualise le menu et le scoreboard pour tous les joueurs ⬆️ //

	}
	/*** ⬆️ Actualise le Scoreboard et le Menu 'GUI' pour les joueurs ⬆️ ***/

}