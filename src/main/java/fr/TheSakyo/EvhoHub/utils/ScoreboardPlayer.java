/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

package fr.TheSakyo.EvhoHub.utils;

import fr.TheSakyo.EvhoHub.HubMain;
import fr.TheSakyo.EvhoUtility.UtilityMain;
import fr.TheSakyo.EvhoUtility.managers.ScoreboardManager;
import fr.TheSakyo.EvhoUtility.utils.DateFormatUtils;
import fr.TheSakyo.EvhoUtility.utils.custom.methods.ColorUtils;
import fr.TheSakyo.EvhoUtility.utils.custom.CustomMethod;
import fr.TheSakyo.EvhoUtility.utils.entity.player.utilities.InfoIP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.*;

/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

public class ScoreboardPlayer {

    /* Récupère la class "Main" et le joueur */
    private HubMain main;
    private Player p;

    public ScoreboardPlayer(Player player, HubMain pluginMain) { this.p = player; this.main = pluginMain; }
    /* Récupère la class "Main" et le joueur */


    // Variables différents codes couleur pour le Scoreboard //
    String AB = ChatColor.AQUA.toString() + ChatColor.BOLD.toString();
    String RB = ChatColor.RED.toString() + ChatColor.BOLD.toString();
    String LPB = ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString();

    String DGRB = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString();
    String GRAYB = ChatColor.GRAY.toString() + ChatColor.BOLD.toString();

    ChatColor W = ChatColor.WHITE;
    // Variables différents codes couleur pour le Scoreboard //


    // Petite variable pour enregistrer le scoreboard des joueurs (Utiles pour l'actualisation du Scoreboard) //
    public Map<UUID, Scoreboard> boards = new HashMap<UUID, Scoreboard>();
    // Petite variable pour enregistrer le scoreboard des joueurs (Utiles pour l'actualisation du Scoreboard) //




    /*********************************/
    /* MÉTHODE GLOBALE DU SCOREBOARD */
    /*********************************/

    public synchronized void getScoreBoard(boolean sideboard, boolean reset) {

        Scoreboard board = ScoreboardManager.getScoreboard(this.p);

        if(reset == true) ScoreboardManager.makeScoreboard(this.p, true);

        if(sideboard == true) {

            Objective obj = board.registerNewObjective("lobbyboard", "dummy", CustomMethod.StringToComponent(" "));

            updateScoreBoard(obj, board);
        }

        //Fait appel à une classe de l'api "EvhoUtility"
        //(pour enregistrer le scoreboard avec l'actualisation de l'affichage en haut de la tête du joueur)
        ScoreboardManager.makeScoreboard(this.p, false);

        if(sideboard == true) {

            if(!boards.containsKey(this.p.getUniqueId())) boards.put(this.p.getUniqueId(), board);
            else { boards.replace(this.p.getUniqueId(), board); }

            updatingScoreboard().runTaskTimerAsynchronously(main, 0L, 8L);
        }
    }

    /*********************************/
    /* MÉTHODE GLOBALE DU SCOREBOARD */
    /*********************************/




    // ⬇️ Petite méthode pour récupérer la date/heure ⬇️ //
    private synchronized String getCurrentFormattedDate(Player player, String format) {

        InfoIP info = UtilityMain.cacheInfo.get(player.getUniqueId());
        Date date = Calendar.getInstance(info.getTimeZone()).getTime();

        return DateFormatUtils.format(date, format, info.getTimeZone());
    }
    // ⬆️ Petite méthode pour récupérer la date/heure ⬆️ //



    // Petite méthode pour mettre le ping en couleur //
    private synchronized String formatPingColor(Integer ping) {

        if(ping == null) return null;

        /* MS */
        String ret = String.valueOf(ping) + "ms";
        /* MS */

        /* PING */
        int goodPing = 200;
        int mediumPing = 500;
        /* PING */

        if(ping <= goodPing) { ret = ChatColor.GREEN + String.valueOf(ping) + "ms" + ChatColor.RESET; }
        else if (ping <= mediumPing) { ret = ChatColor.YELLOW + String.valueOf(ping) + "ms" + ChatColor.RESET; }
        else { ret = ChatColor.RED + String.valueOf(ping) + "ms" + ChatColor.RESET; }

        return ret;

    }
    // Petite méthode pour mettre le ping en couleur //




    // Petite Méthode pour créer les lignes du scoreboard //
    private synchronized void updateScoreBoard(Objective sidebar, Scoreboard board) {

        // Variable du grade du joueur //
        List<String> grade = UtilityMain.getInstance().formatgrade.getPlayerGroup(this.p, true, false);
        // Variable du grade du joueur //

        // Variables différents codes couleurs tchat pour le titre du scoreboard //
        String RB = ChatColor.RED.toString() + ChatColor.BOLD.toString();

        String WB = ChatColor.WHITE.toString() + ChatColor.BOLD.toString();
        // Variables différents codes couleurs tchat pour le titre du scoreboard //

        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);


        Score line12 = sidebar.getScore(ChatColor.GOLD + " ");
        line12.setScore(12);


        Score line11 = sidebar.getScore(WB + "Mon Grade :");
        line11.setScore(11);


        Score line10 = sidebar.getScore(ChatColor.BLACK + " ");
        line10.setScore(10);


        Team line9 = board.registerNewTeam("Grade");
		line9.addEntry(ChatColor.MAGIC.toString());
		line9.suffix(CustomMethod.StringToComponent(ColorUtils.format(grade.get(0))));
		sidebar.getScore(ChatColor.MAGIC.toString()).setScore(9);


        Score line8 = sidebar.getScore(ChatColor.DARK_GRAY + " ");
        line8.setScore(8);


        String YB = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString();
        String GRB = ChatColor.GREEN.toString() + ChatColor.BOLD.toString();

        Score line7 = sidebar.getScore(YB + "Argent(s) : " + GRB + "(En Dév)");
        line7.setScore(7);


        Score line6 = sidebar.getScore(ChatColor.GRAY + " ");
        line6.setScore(6);

        Team line5 = board.registerNewTeam("Hours");
		line5.addEntry(ChatColor.DARK_AQUA.toString());
		line5.prefix(CustomMethod.StringToComponent(AB + "Heure : "));
		line5.suffix(CustomMethod.StringToComponent(ChatColor.WHITE + getCurrentFormattedDate(p, "HH:mm")));
		sidebar.getScore(ChatColor.DARK_AQUA.toString()).setScore(5);


        Score line4 = sidebar.getScore(ChatColor.WHITE + " ");
        line4.setScore(4);

        Team line3 = board.registerNewTeam("Ping");
		line3.addEntry(ChatColor.YELLOW.toString());
		line3.prefix(CustomMethod.StringToComponent(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Ping : "));
		line3.suffix(CustomMethod.StringToComponent(formatPingColor(p.getPing())));
		sidebar.getScore(ChatColor.YELLOW.toString()).setScore(3);


        Score line2 = sidebar.getScore(ChatColor.RESET + " ");
        line2.setScore(2);

        Team line1 = board.registerNewTeam("Connected");
		line1.addEntry(ChatColor.DARK_GREEN.toString());
		line1.prefix(CustomMethod.StringToComponent(DGRB + "En Ligne : "));

                    /* ----------------------------------------------------------- */

        UtilityMain.getInstance().sendToBungee("BungeeCount", "all"); //Récupère le nombre de jouer(s) en ligne (Bungee)
		line1.suffix(CustomMethod.StringToComponent(GRAYB + UtilityMain.BungeePlayerOnlines + "/" + Bukkit.getServer().getMaxPlayers()));

                    /* ----------------------------------------------------------- */

		sidebar.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);

        Score score = sidebar.getScore(" ");
        score.setScore(0);
    }
    // Petite Méthode pour créer les lignes du scoreboard //





    /*************************************************************************************/
    /* PARTIE BOUCLE ÉXÉCUTABLE POUR L'ACTUALISATION DU SCOREBOARD + DES MÉTHODES UTILES */
    /*************************************************************************************/


    // Variables pour la boucle de l'actualisation du ScoreBoard //
    String[] title = new String[] {
            W + "»   " + AB + "Evhonia" + W + "   «",
            W + "»  " + RB + "Evhonia" + W + "  «",
            W + "» " + LPB + "Evhonia" + W + " «"
    };

    int index = 0;
    // Variables pour la boucle de l'actualisation du ScoreBoard //



    // Récupère "l'Objective" du ScoreBoard du joueur //
    private Objective getSidebarObjectivePlayer() { return ScoreboardManager.getScoreboard(this.p).getObjective(DisplaySlot.SIDEBAR); }
    // Récupère "l'Objective" du ScoreBoard du joueur //


    // Petite méthode pour changer le titre du Scoreboard //
    private synchronized void ChangeTitle(Scoreboard Board, String NewTitle) {

        try { Board.getObjective(DisplaySlot.SIDEBAR).displayName(CustomMethod.StringToComponent(NewTitle)); }
        catch(NullPointerException e) { index = 0; updatingScoreboard().cancel(); }
    }
    // Petite méthode pour changer le titre du Scoreboard //



    // Petite méthode pour bien actualiser le Scoreboard //
    private synchronized void UpdatingTeams(Scoreboard board) {

        // Variable du grade du joueur //
        List<String> grade = UtilityMain.getInstance().formatgrade.getPlayerGroup(this.p, true, false);
        // Variable du grade du joueur //

        // Essaie de recharger les différents 'teams' //
        try {
            Team GradeTeam = board.getTeam("Grade");
            Team PingTeam = board.getTeam("Ping");
            Team ConnectTeam = board.getTeam("Connected");
            Team HoursTeam = board.getTeam("Hours");

            if(GradeTeam != null) { GradeTeam.suffix(CustomMethod.StringToComponent(ColorUtils.format(grade.get(0)))); }
            if(PingTeam != null) { PingTeam.suffix(CustomMethod.StringToComponent(formatPingColor(p.getPing()))); }

            if(ConnectTeam != null) {

                UtilityMain.getInstance().sendToBungee("BungeeCount", "all"); //Récupère le nombre de jouer(s) en ligne (Bungee)
                ConnectTeam.suffix(CustomMethod.StringToComponent(GRAYB + UtilityMain.BungeePlayerOnlines + "/" + Bukkit.getServer().getMaxPlayers()));
            }

            if(HoursTeam != null) { HoursTeam.suffix(CustomMethod.StringToComponent(ChatColor.WHITE + getCurrentFormattedDate(p, "HH:mm"))); }

        } catch(IllegalStateException ignored) {}
        // Essaie de recharger les différents 'teams' //

    }
    // Petite méthode pour bien actualiser le Scoreboard //



    /**/ /* MÉTHODE (BOUCLE) POUR ACTUALISER LE SCOREBOARD (SCOREBOARD ANIMÉ) */ /**/

    protected synchronized BukkitRunnable updatingScoreboard() {

        return new BukkitRunnable() {

            @Override
            public void run() {

                if(index >= title.length) index = 0;

                for(Scoreboard board : boards.values()) {

                    if(getSidebarObjectivePlayer() == null || board == null) {

                        index = 0;
                        this.cancel();

                    } else {

                        ChangeTitle(board, title[index]);
                        UpdatingTeams(board);
                    }
                }
                index++;
            }
        };
    }

    /**/ /* MÉTHODE (BOUCLE) POUR ACTUALISER LE SCOREBOARD (SCOREBOARD ANIMÉ) */ /**/


    /*************************************************************************************/
    /* PARTIE BOUCLE ÉXÉCUTABLE POUR L'ACTUALISATION DU SCOREBOARD + DES MÉTHODES UTILES */
    /*************************************************************************************/
}