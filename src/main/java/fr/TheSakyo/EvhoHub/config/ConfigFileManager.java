package fr.TheSakyo.EvhoHub.config;

import fr.TheSakyo.EvhoHub.HubMain;
import fr.TheSakyo.EvhoUtility.config.ConfigFile;

public class ConfigFileManager extends fr.TheSakyo.EvhoUtility.config.ConfigFileManager {

    //Récupère la class "Main" en tant que "static"
    private static final HubMain mainInstance = HubMain.getInstance();


    // ⬇️ *** CHARGEMENT DES FICHIERS DE CONFIGURATIONS D'EVHOHUB *** ⬇️ //
	public static void LoadConfig() {

		// En-Tête du fichier de configuration "hideplayer.yml" //
        String[] headerHidePlayer = {
		   "| ===== EvhoUtility Outils ===== |",
		   " ",
		   "*** Liste des Joueur ayant l'Option : ***",
           "*** Afficher/Cacher les autres Joueurs ***",
		   " ",
		   "Il vaut mieux ne pas toucher à ce fichier.",
		   " ",
		   "par TheSakyo",
		   " "
		};
		// En-Tête du fichier de configuration "hideplayer.yml" //


		/* Chargement/Création du fichier de configuration 'hideplayer.yml' */
		if(getConfigFile(mainInstance.getDataFolder(), "hideplayer.yml").exists()) {

		    mainInstance.hidePlayerCfg = getNewConfig(mainInstance.getDataFolder(), "hideplayer.yml", headerHidePlayer);
		    ConfigFile.reloadConfig(mainInstance.hidePlayerCfg);

		} else {

		    mainInstance.hidePlayerCfg = getNewConfig(mainInstance.getDataFolder(), "hideplayer.yml", headerHidePlayer);
            ConfigFile.createSection(mainInstance.hidePlayerCfg, "Player");
            ConfigFile.saveConfig(mainInstance.hidePlayerCfg);
		}
		/* Chargement/Création du fichier de configuration 'hideplayer.yml' */
	}
    // ⬆️ *** CHARGEMENT DES FICHIERS DE CONFIGURATIONS D'EVHOHUB *** ⬆️ //
}
