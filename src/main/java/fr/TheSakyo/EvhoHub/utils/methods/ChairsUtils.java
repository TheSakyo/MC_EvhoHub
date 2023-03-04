/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

package fr.TheSakyo.EvhoHub.utils.methods;

import fr.TheSakyo.EvhoHub.HubMain;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;


import java.util.*;
import java.util.List;

/* PARTIE IMPORTATIONS + PACKAGE DE LA CLASS */

public class ChairsUtils {

    static HubMain mainInstance = HubMain.getInstance(); // Récupère l'Instance de la class principale.

    /**
     * Récupère la face exacte du bloc en fonction de sa rotation {@link BlockFace}
     *
     * @param face La face du bloc à faire pivoter.
     *
     * @return La face de bloc du bloc en question.
     */
    protected static BlockFace rot(BlockFace face) {

        // Récupère la face exacte du bloc en fonction de sa rotation //
        switch(face) {

            case NORTH -> { return BlockFace.WEST; }
            case WEST -> { return BlockFace.SOUTH; }
            case SOUTH -> { return BlockFace.EAST; }
            case EAST -> { return BlockFace.NORTH; }
            default -> throw new IllegalArgumentException();
        }
        // Récupère la face exacte du bloc en fonction de sa rotation //
    }


    /**
     * Renvoie la longueur d'un bloc d'escalier dans la direction de la face spécifiée
     *
     * @param expectedFace La direction dans laquelle l'escalier fait face.
     * @param block Le bloc à partir duquel commencer la recherche.
     * @param searchFace La direction dans laquelle les escaliers sont recherchés.
     * @param limit Le nombre maximum de blocs à rechercher.
     *
     * @return La longueur des escaliers
     */
    protected static int calculateStairsWidth(BlockFace expectedFace, Block block, BlockFace searchFace, int limit) {

        // Boucle pour détecter la longueur en fonction de la limite précisée //
        for(int i = 0; i < limit; i++) {

            block = block.getRelative(searchFace);
            BlockData blockdata = block.getBlockData();

            if(!(blockdata instanceof Stairs stairs)) { return i; }

            if(stairs.getFacing() != expectedFace) { return i; }
        }
        // Boucle pour détecter la longueur en fonction de la limit précisé //

        //Retourne la limite
        return limit;
    }



    /**
     * Calcule l'emplacement du joueur exact pour quand il s'assiéra.
     *
     * @param player Le joueur assis
     * @param block Le bloc sur lequel le joueur est assis.
     *
     * @return L'emplacement du bloc.
     */
    protected static List<Object> calculateSitLocation(Player player, Block block) {

        // Permettra de récupérer le nouveau cacul de la localisation pour que le Joueur s'asseye avec la raison du retour.
        List<Object> calculLocation = new ArrayList<Object>();

        BlockData blockdata = block.getBlockData(); // Récupère les données du block
        Location blockLocation = block.getLocation(); //Récupère la localisation du bloc
        String REASON = ""; // Permet de récupérer la raison du retour du calcul

        float yaw = player.getLocation().getYaw(); // Récupère la rotation "Yaw" du joueur
        float pitch = player.getLocation().getPitch(); // Récupère la rotation "Pitch" du joueur

        double x = 0.0; // Permet de récupérer la coordonée 'X' éxacte à envoyé
        double z = 0.0; // Permet de récupérer la coordonée 'Y' éxacte à envoyé

        double distance = blockLocation.add(0.5, 0, 0.5).distance(player.getLocation()); // Récupère la distance entre le bloc et le Joueur

        // Si la distance entre le bloc et le Joueur est inférieur ou égal à 3.5, on continue
        if(distance <= 3.5) {

            // Vérifie si le bloc est un escalier, alors on effectue le calcul pour l'escalier
            if(blockdata instanceof Stairs stairs) {

                // Récupère la face de l'escalier //
                BlockFace ascendingFacing = stairs.getFacing();

                //Détecte la rotation exacte de l'escalier
                BlockFace facing = rot(ascendingFacing);

                // Calcule la longueur de l'escalier
                int width = ChairsUtils.calculateStairsWidth(ascendingFacing, block, facing, 16);

                // Obtient une relation en fonction de la rotation est la longueur
                block.getRelative(facing, width + 1);

                // ⬇️ Essait de définir le "Yaw" et les coordonées X ou Z en fonction de la rotation exacte de la face de l'escalier ⬇️ //
                switch(ascendingFacing.getOppositeFace()) {

                    case NORTH -> { yaw = 180; z = -0.2; break; } // Rotation vers le nord : Enlève "0.2" à la coordonée 'Z' et définit "180" à la rotation 'Yaw'
                    case EAST -> { yaw = -90; x = 0.2; break; } // Rotation vers le l'est : Ajoute "0.2" à la coordonée 'X' et définit "-90" à la rotation 'Yaw'
                    case SOUTH -> { yaw = 0; z = 0.2; break; } // Rotation vers le sud : Ajoute "0.2" à la coordonée 'Z' et définit "0" à la rotation 'Yaw'
                    case WEST -> { yaw = 90; x = -0.2; break; } // Rotation vers l'ouest : Enlève "0.2" à la coordonée 'X' et définit "90" à la rotation 'Yaw'
                }
                // ⬆️ Essait de définir le "Yaw" et les coordonées X ou Z en fonction de la rotation exacte de la face de l'escalier ⬆️ //

                // Définit la nouvelle localisation pour que le Joueur s'asseye en réajustant l'emplacement quand le Joueur va s'asseoir sur la chaise
                calculLocation.add(blockLocation.add(x, 0.3, z));

                // Définit également le retour qui sera envoyé.
                calculLocation.add("Vous êtes maintenant assis.");

            // Sinon, on vérifie si le bloc est une dakke, alors on effectue le calcul pour la dalle
            } else if(blockdata instanceof Slab slab) {

                /* ⬇️ Définit la nouvelle localisation pour que le Joueur s'asseye en réajustant l'emplacement quand le Joueur va s'asseoir sur la dalle
                   en fonction de son type ⬇️ */
                if(slab.getType() == Slab.Type.BOTTOM) calculLocation.add(blockLocation.add(0.0, 0.3, 0.0));
                else if(slab.getType() == Slab.Type.TOP) calculLocation.add(blockLocation.add(0.0, 0.8, 0.0));
                /* ⬆️ Définit la nouvelle localisation pour que le Joueur s'asseye en réajustant l'emplacement quand le Joueur va s'asseoir sur la dalle
                   en fonction de son type ⬆️ */

                // Définit également le retour qui sera envoyé.
                calculLocation.add("Vous êtes maintenant assis.");

            // Sinon, on retourne 'null'
            } else return null;

        // Sinon, on définit la raison qui sera envoyée pour laquelle on ne définit pas la nouvelle localisation pour que le Joueur s'asseye
        } else calculLocation.add("Vous êtes trop loin pour vous asseoir.");

                                    /* ------------------------------------------------------ */

         // ⬇️ Si la liste 'calculLocation' contient en première position une Localisation, on change alors sa rotation ⬇️ //
         if(calculLocation.get(0) instanceof Location loc) {

            //Définit la rotation "Yaw" de la Localisation récupérée par celui du Joueur ou par celui qui a été calculer en fonction de la rotation de l'escalier
            loc.setYaw(yaw);
            loc.setPitch(pitch); //Définit la rotation "Pitch" de la Localisation récupérée par celui du Joueur

            calculLocation.set(0, loc); // Réattribut la Localisation récupérée avec les modifications effectuée
         }
         // ⬆️ Si la liste 'calculLocation' contient en première position une Localisation, on change alors sa rotation ⬆️ //

                                    /* ------------------------------------------------------ */

        return calculLocation; //Retourne la nouvelle localisation pour que le Joueur s'asseye ainsi que la raison.
    }

                        /* ------------------------------------------------------------------------------------------- */

    /**
     * Le joueur se relève s'il est assis
     *
     * @param player Le joueur assis
     * @param entityTarget l'Entité sur laquelle il est assis
     *
     * @return Relève le joueur s'il est assis
     */
    public static void standup(Player player, Entity entityTarget) {

        double x = 0.0; // Permet de récupérer la coordonée 'X' éxacte à envoyé
        double z = 0.0; // Permet de récupérer la coordonée 'Y' éxacte à envoyé

        Location playerLocation = player.getLocation(); // Récupère la localisation du Joueur
        BlockFace face = player.getFacing(); // Récupère la direction de face du Joueur (peut changer, en récupérant la direction de face de l'escalier)
        Block block = player.getLocation().getBlock(); // Récupère le Block se situant à la localisation du Joueur

        // On vérifie si l'entité cible a le Joueur en tant que passagé, si c'est le cas, on continue //
        if(entityTarget.getPassengers().contains(player)) {

            entityTarget.removePassenger(player); // Enlève le Joueur en passagé de l'entité
            entityTarget.setInvulnerable(false); // Rend l'entité cible plus invulnérable
            entityTarget.remove(); // Supprme l'entité cible

            // Si le bloc ciblé est un escalier, on récupère sa direction de face à la place de la direction de face du Joueur
            if(block.getBlockData() instanceof Stairs stair) face = stair.getFacing();

            // ⬇️ Essait de définir les coordonées X ou Z en fonction de la rotation exacte de la face demandée ⬇️ //
            switch(rot(face).getOppositeFace()) {

                case NORTH -> { x = -0.75; break; } // Rotation vers le nord : Enlève "0.75" à la coordonée 'X'
                case EAST -> {  z = -0.75; break; } // Rotation vers le sud : Enlève "0.75" à la coordonée 'Z'
                case SOUTH -> { x = 0.75; break; } // Rotation vers le l'est : Ajoute "0.75" à la coordonée 'X'
                case WEST -> { z = 0.75; break; } // Rotation vers l'ouest : Ajoute "0.5" à la coordonée 'Z'
            }
            // ⬆️ Essait de définir les coordonées X ou Z en fonction de la face demandée ⬆️ //

            // Récupère la nouvelle localisation auquel le joueur sera téléporté (évite les colisions)
            Location newLocation = block.getLocation().toCenterLocation().add(x, 0.5, z);
            newLocation.setYaw(playerLocation.getYaw()); // Définit la rotation 'Yaw' du Joueur à la nouvelle localisation
            newLocation.setPitch(playerLocation.getPitch()); // Définit la rotation 'Pitch' du Joueur à la nouvelle localisation

            player.teleport(newLocation); // Téléporte le Joueur à la nouvelle localisation demandé
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Vous n'êtes plus assis."); // Envoie un message au Joueur
        }
        // On vérifie si l'entité cible, a le Joueur assis sur lui, alors on continue //
    }

    /**
     * Le joueur s'asseois sur une Entité {@link ArmorStand}, s'il a la permission requise et si c'est un escalier ou une dalle
     *
     * @param player Le joueur qui doit s'asseoir
     * @param blockTarget le bloc sur lequel il doit s'assoir
     *
     * @return Assis le joueur s'il a la permission requise et si c'est un escalier ou une dalle
     */
    public static void sitdown(Player player, Block blockTarget) {

        ServerLevel level = ((CraftWorld)player.getWorld()).getHandle(); // Récupère le Monde du Joueur depuis le 'NMS'

        BlockData blockdata = blockTarget.getBlockData(); // Récupère les données du bloc en question

        // Vérifie si le type du block est un escalier ou une dalle //
        if(blockdata instanceof Stairs || blockdata instanceof Slab) {

            // Récupère le bloc étant au-dessus du bloc cible auquel le Joueur souhaite s'asseoir
            Material inBlockMaterial = blockTarget.getLocation().getBlock().getRelative(BlockFace.UP).getType();

            //Vérifie s'il n'y a aucun bloc au-dessus du bloc cible ou si il y'a que le bloc de chaîne, sinon on annule le code
            if(inBlockMaterial != Material.AIR && inBlockMaterial != Material.CHAIN) return;

            // Vérifie si le type du block est un escalier et on vérifie s'il n'est pas à l'envers, sinon on annule le code
            if(blockdata instanceof Stairs stair) { if(stair.getHalf() != Bisected.Half.BOTTOM) return; }

            // Sinon, vérifie si le type du block est une dalle et on vérifie si ce n'est pas deux dalle double, ou une dalle du bas, sinon on annule le code
            else if(blockdata instanceof Slab slab) { if(slab.getType() == Slab.Type.DOUBLE /*|| slab.getType() != Slab.Type.BOTTOM*/) return; }

                            /* --------------------------------------------------------------- */

            //Vérfie si le joueur à la permission requise, sinon rien ne se passe
            if(player.hasPermission("evhohub.chairs")) {

                boolean hasOnlyPlayer = false;

                //Récupère une bonne "Location" avec la méthode "calculateSitLocation()" ainsi que le message envoyé
                List<Object> SitLocation = calculateSitLocation(player, blockTarget);

                // Si la Localisation 'SitLocation' est "null", rien ne se passe.
                if(SitLocation == null) return;

                // Si la liste 'SitLocation' contient uniquement un message, rien ne se passe, mais on envoie le message au joueur. //
                if(SitLocation.get(0) instanceof String message) {

                    player.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC.toString() + message);
                    return;
                }
                // Si la liste 'SitLocation' contient uniquement un message, rien ne se passe, mais on envoie le message au joueur. //

                // Récupère toutes les entités étant dans la 'hitbox' au niveau du bloc cible
                Collection<Entity> entities = player.getWorld().getNearbyEntities(blockTarget.getBoundingBox());

                // Vérifie s'il n'y a aucune Entité dans la 'hitbox' du bloc cible ou s'il y a que le joueur que le Joueur en question //
                for(Entity entity : entities) {

                    if(entity instanceof Player p) {

                        if(p == player) hasOnlyPlayer = true;
                        else hasOnlyPlayer = false;
                    }
                }
                // Vérifie s'il n'y a aucune Entité dans la 'hitbox' du bloc cible ou s'il y a que le joueur que le Joueur en question //

                // On annule s'il y a des entitées dans la 'hitbox' du bloc cible ou si la 'hitbox' du bloc ne contient pas que le Joueur en question
                if(!(entities.isEmpty() || hasOnlyPlayer)) {

                    player.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC.toString() + "Quelque chose vous empêche de vous asseoir :(.");
                    return;
                }

                                     /* ------------------------------------------------------- */

                Location location = ((Location)SitLocation.get(0)); // Récupère la localisation calculée pour que le Joueur puisse s'asseoir.

                // Créer un Porte Armure qui permettra au Joueur de s'asseoir
                ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);

                armorStand.setPos(location.getX(), location.getY(), location.getZ()); // Définit la position où le Porte Armure va apparaître
                armorStand.setInvisible(true); // Rend le Porte Armure invisible.
                armorStand.setMarker(true); // Définit un marqueur au Porte Armure.
                armorStand.setSmall(true); // Rend le Porte Armure petit.
                armorStand.setNoBasePlate(true); // Enlève la Plaque de Base du Porte Armure.
                armorStand.isCollidable(false); // Désactive les collisions du Porte Armure.
                armorStand.setInvulnerable(true); // Rend l'Entité invulnérable.
                armorStand.setNoGravity(true); //Annule la gravité de l'Entité.
                armorStand.setSilent(true); //Annule le son de l'Entité sur silencieux.
                armorStand.setCustomNameVisible(false); //Annule tout nom personnalisé sur l'Entité.
                armorStand.setRot(player.getLocation().getYaw(), player.getLocation().getPitch()); // Définit la rotation de l'Entité par la même rotation du Joueur.

                                    /* ------------------------------------------------------- */

                level.addEntity(armorStand, CreatureSpawnEvent.SpawnReason.CUSTOM); // Fait apparaître le Porte Armure pour pemettre au Joueur de le monter
                ((CraftPlayer)player).getHandle().startRiding(armorStand, true); // Le Joueur va monter le Porte Armure

                                    /* ------------------------------------------------------- */

                player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + SitLocation.get(1));  // Envoit le message d'information au Joueur
            }
        }
		// Vérifie si le type du block est un escalier ou une dalle //
    }
}
